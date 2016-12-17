package org.anderes.edu.appengine.cookbook.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.json.stream.JsonGenerator.PRETTY_PRINTING;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RestApiIT {
    
    private final static Logger logger = Logger.getLogger(RestApiIT.class.getName());
    private final static UriBuilder uri = UriBuilder.fromResource(MyApplication.class)
                    .scheme("http").host("localhost").port(8089).path(RecipeResource.class);
    private Client client;
    
    @Before
    public void setup() {
        client = ClientBuilder.newClient();
        client.register(JsonProcessingFeature.class)
            .property(PRETTY_PRINTING, true)
            .register(new LoggingFilter(logger, true)); 
    }
    
    @BeforeClass
    public static void shutdown() {
        final Client client = ClientBuilder.newClient();
        client.register(JsonProcessingFeature.class)
            .property(PRETTY_PRINTING, true)
            .register(new LoggingFilter(logger, true)); 
        final WebTarget target = client.target(uri);
        final Response response = target.request(APPLICATION_JSON).buildGet().invoke();
        final JsonArray jsonArray = response.readEntity(JsonArray.class);
        deleteAll(jsonArray, client);
    }
    
    private static void deleteAll(final JsonArray jsonArray, final Client client) {
        final WebTarget target = client.target(uri);
        for (JsonValue object : jsonArray) {
            final WebTarget newTarget = target.path(((JsonObject)object).getString("id"));
            final Response response = newTarget.request().delete();
            assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
        }
    }
    
    @Test
    public void shouldBeGetAllRecipes() {
        
        // given
        final WebTarget target = client.target(uri);
    
        // when
        final Response response = target.request(APPLICATION_JSON).buildGet().invoke();
        
        // then
        assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.getMediaType(), is(APPLICATION_JSON_TYPE));
        assertThat(response.hasEntity(), is(true));
     
        final JsonArray  jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray, is(notNullValue()));
    }
    
    @Test
    public void shouldBeSaveNewRecipePOST() throws Exception {
        
        // given
        final WebTarget target = client.target(uri);
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe1.json");
        final JsonObject json = readJsonFile(jsonFile);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", UTF_8.displayName());
        final Entity<JsonObject> entity = Entity.entity(json, variant);

        // when
        final Response responseFromSave = target.request(APPLICATION_JSON).post(entity);
        
        // then
        assertThat(responseFromSave.getStatus(), is(CREATED.getStatusCode()));
        final URI uriForNewRecipe = responseFromSave.getLocation();
        assertThat(uriForNewRecipe, is(notNullValue()));
        final WebTarget targetForGetOne = client.target(uriForNewRecipe);
        final Response responseFromGet = targetForGetOne.request(APPLICATION_JSON).get();
        assertThat(responseFromGet.getStatus(), is(OK.getStatusCode()));
    }
    
    @Test
    public void shouldBeInsertNewRecipePUT() throws Exception {
        // given
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe2.json");
        final JsonObject json = readJsonFile(jsonFile);
        final WebTarget target = client.target(uri);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", UTF_8.displayName());
        final Entity<JsonObject> entity = Entity.entity(json, variant);

        // when
        final WebTarget newTarget = target.path(json.getString("id"));
        final Response responseFromSave = newTarget.request(APPLICATION_JSON).put(entity);
        
        // then
        assertThat(responseFromSave.getStatus(), is(CREATED.getStatusCode()));
        final URI uriForSavedRecipe = target.getUri();
        final WebTarget targetForGetOne = client.target(uriForSavedRecipe);
        final Response responseFromGet = targetForGetOne.request(APPLICATION_JSON).get();
        assertThat(responseFromGet.getStatus(), is(OK.getStatusCode()));
    }
    
    @Test
    public void shouldBeDeleteNotExistRecipe() {
    	// given
    	final WebTarget target = client.target(uri).path("abc-000");
        // when
    	final Response response = target.request().delete();
    	// then
    	assertThat(response.getStatus(), is(NOT_FOUND.getStatusCode()));
    }
    @Test
    public void shouldBeReadJsonFileFromPath() throws Exception {
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe1.json");
        final JsonObject recipeObject = readJsonFile(jsonFile);
        
        assertThat(recipeObject, is(notNullValue()));
        assertThat(recipeObject.getString("title"), is("Torroneparfait"));
    }
    
    @Test
    public void shouldBeReadJsonFileFromResources() throws Exception {
        final Path jsonFile = Paths.get(getClass().getResource("/recipe1.json").toURI());
        final JsonObject recipeObject = readJsonFile(jsonFile);
        
        assertThat(recipeObject, is(notNullValue()));
        assertThat(recipeObject.getString("title"), is("Torroneparfait"));
    }
    
    private JsonObject readJsonFile(Path jsonFile) throws FileNotFoundException {
        final JsonReader reader = Json.createReader(new FileReader(jsonFile.toFile()));
        final JsonObject recipeObject = reader.readObject();
        reader.close();
        return recipeObject;
    }
}
