package org.anderes.edu.firebase;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import static org.apache.commons.lang3.StringUtils.*;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

public class CookbookSyncTest {

    private final String FIREBASE_IO = "codelab-82e5d.firebaseio.com";
    private static String accessToken;
    private UriBuilder anderesUrl;
    private final Function<JsonValue, Optional<String>> extractRecipeUrl = 
                    element -> ((JsonObject)element).getJsonArray("links").stream()
                    .filter(link -> ((JsonObject)link).getString("rel").equals("self"))
                    .map(link -> ((JsonObject)link).getString("href"))
                    .findFirst();

    @BeforeClass
    public static void setupOnce() {
        GoogleCredential googleCred;
        try(FileInputStream fis = new FileInputStream("codelab.service.account.json")) {
            googleCred = GoogleCredential.fromStream(fis);
            GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                    "https://www.googleapis.com/auth/firebase.database",  // or use firebase.database.readonly for read-only access
                    "https://www.googleapis.com/auth/userinfo.email"
                    )
                );
            scoped.refreshToken();
            accessToken = scoped.getAccessToken();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    
    @Before
    public void setUp() throws Exception {
        anderesUrl = UriBuilder.fromPath("resources-api").path("recipes-repository").host("www.anderes.org").scheme("http");
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void shouldBeFindAllRecipe() {
       final Client client = buildRestClient();
       final Response response = client.target(anderesUrl).request(APPLICATION_JSON_TYPE).get();
        
       assertThat(response.getStatus(), is(OK.getStatusCode()));
       assertThat(response.hasEntity(), is(true));
       final JsonObject content = response.readEntity(JsonObject.class);
       client.close();
       final JsonArray array = content.getJsonArray("content");
       assertThat(array, is(notNullValue()));
       
       array.stream()
           .peek(System.out::println)
           .forEach(element -> assertThat(((JsonObject)element).containsKey("uuid"), is(true)));
       
       final List<Optional<String>> recipeLinkList = array.stream().map(extractRecipeUrl).collect(Collectors.toList());
       assertThat(recipeLinkList.size(), is(array.size()));
       
       recipeLinkList.parallelStream().filter(o -> o.isPresent())
                       .map(o -> o.get())
                       .forEach(o -> insertRecipeLink(o));
       
       final List<JsonObject> recipes = recipeLinkList.parallelStream().filter(o -> o.isPresent())
                       .map(o -> o.get())
                       .map(o -> getRecipe(o))
                       .collect(Collectors.toList());
       
       recipes.parallelStream().forEach(recipe -> updateRecipeAtFirebase(recipe));
       
       recipeLinkList.parallelStream().filter(o -> o.isPresent()).map(o -> o.get())
           .forEach(recipeUrl -> getIngredients(recipeUrl).stream()
                       .map(o -> (JsonObject)o)
                       .forEach(ingredient -> updateIngredientAtFirebase(ingredient, substringAfterLast(recipeUrl, "/"))));
    }
    
    private void insertRecipeLink(final String link) {
        
        final JsonObject message = Json.createObjectBuilder().add("Link", link).build();
        final Entity<JsonObject> entity = Entity.entity(message, APPLICATION_JSON_TYPE);
        final Client client = buildRestClient();
        final UriBuilder restUrl = UriBuilder.fromPath("/").host(FIREBASE_IO).scheme("https").path("recipes")
                        .path(substringAfterLast(link, "/") + ".json")
                        .queryParam("access_token", accessToken);
        final Response response = client.target(restUrl).request().build("PATCH", entity)
                        .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        
        final JsonObject value = response.readEntity(JsonObject.class);
        assertThat(value.containsKey("Link"), is(true));
        
        client.close();
    }
    
    private void updateRecipeAtFirebase(final JsonObject recipe) {
        final Entity<JsonObject> entity = Entity.entity(recipe, APPLICATION_JSON_TYPE);
        final Client client = buildRestClient();
        final UriBuilder restUrl = UriBuilder.fromPath("/").host(FIREBASE_IO).scheme("https").path("recipes")
                        .path(recipe.getString("uuid") + ".json")
                        .queryParam("access_token", accessToken);
        final Response response = client.target(restUrl).request().build("PATCH", entity)
                        .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        client.close();
    }
    
    private void updateIngredientAtFirebase(final JsonObject ingredient, final String recipeId) {
        final Entity<JsonObject> entity = Entity.entity(ingredient, APPLICATION_JSON_TYPE);
        final Client client = buildRestClient();
        final UriBuilder restUrl = UriBuilder.fromPath("/").host(FIREBASE_IO).scheme("https").path("recipes")
                        .path(recipeId).path("ingredients").path(ingredient.getString("resourceId") + ".json")
                        .queryParam("access_token", accessToken);
        final Response response = client.target(restUrl).request().build("PATCH", entity)
                        .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();
        
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        client.close();
    }
    
    private JsonObject getRecipe(final String recipeUrl) {
        final Client client = buildRestClient();
        final Response response = client.target(recipeUrl).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final JsonObject content = response.readEntity(JsonObject.class);
        client.close();
        return Json.createObjectBuilder()
                        .add("uuid", content.getString("uuid"))
                        .add("title", content.getString("title"))
                        .add("preamble", content.getString("preamble"))
                        .add("preparation", content.getString("preparation"))
                        .add("noOfPerson", content.getString("noOfPerson"))
                        .add("rating", content.getInt("rating"))
                        .add("addingDate", content.get("addingDate"))
                        .add("editingDate", content.get("editingDate"))
                        .add("tags", content.getJsonArray("tags"))
                        .build();
    }
    
    private JsonArray getIngredients(final String recipeUrl) {
        final Client client = buildRestClient();
        final Response response = client.target(recipeUrl).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final JsonObject content = response.readEntity(JsonObject.class);
        Optional<String> ingredientsLink = content.getJsonArray("links").stream()
                    .filter(link -> ((JsonObject)link).getString("rel").equals("ingredients"))
                    .map(link -> ((JsonObject)link).getString("href"))
                    .findFirst();
        client.close();
        return requestForIngredients(ingredientsLink.orElseThrow(() -> new IllegalStateException("Ungültige URL!")));
    }
    
    private JsonArray requestForIngredients(final String ingredientsLink) {
        final Client client = buildRestClient();
        final Response response = client.target(ingredientsLink).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final JsonArray content = response.readEntity(JsonArray.class);
        client.close();
        final List<JsonObject> ingredients = content.stream().map(v -> (JsonObject)v).map(element -> createIngrendient(element)).collect(Collectors.toList());
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ingredients.stream().forEach(element -> arrayBuilder.add(element));
        return arrayBuilder.build();
    }

    private JsonObject createIngrendient(final JsonObject element) {
        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("resourceId", element.getString("resourceId"));
        if (element.isNull("portion")) {
            objectBuilder.addNull("portion");
        } else {
            objectBuilder.add("portion", element.getString("portion"));
        }
        if (element.isNull("description")) {
            objectBuilder.addNull("description");
        } else {
            objectBuilder.add("description", element.getString("description"));
        }
        if (element.isNull("comment")) {
            objectBuilder.addNull("comment");
        } else {
            objectBuilder.add("comment", element.getString("comment"));
        }
        return objectBuilder.build();
    }
    
    private Client buildRestClient( ) {
        return ClientBuilder.newBuilder()
                        .register(JsonProcessingFeature.class)
                        .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
                        .build();
    }
}
