package org.anderes.edu.client.rest.jersey;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Beispiel für einen REST-Client der JSON mittels JOSN-P low level verarbeitet.
 * <p>
 * siehe https://jsonp.java.net/
 * 
 * @author René Anderes
 *
 */
public class JsonProcessingRestClientTest {
    
    private UriBuilder restUrl;
    private Client client;

    @Before
    public void setUp() throws Exception {
      
        restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
        
        /** 
         * Hier wird explizit das JSON-P Feature registriert. Dies ist nicht notwendig, da Jersey
         * das entsprechende Feature im Klassenpfad hat und dadurch automatisch registriert.
         * Das ausschalten des auto discovery wird hier nur exemplarisch gemacht und ist für
         * JSON-P nicht notwendig.
         * siehe https://jersey.java.net/documentation/latest/user-guide.html#deployment.autodiscoverable 
         */
        client = ClientBuilder.newBuilder()
                        .register(JsonProcessingFeature.class)
                        .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
                        .build();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void shouldBeFindAllRecipe() {
        final Response response = client.target(restUrl).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final JsonArray array = response.readEntity(JsonArray.class);
        assertThat(array, is(notNullValue()));
        array.forEach(element -> assertThat(((JsonObject)element).containsKey("id"), is(true)));
    }
    
    @Test
    public void shouldBeOneRecipe() {
        final Response response = client.target(restUrl.path("c0e5582e-252f-4e94-8a49-e12b4b047afb")).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final JsonObject recipe = response.readEntity(JsonObject.class);
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.containsKey("id"), is(true));
        assertThat(recipe.getString("id"), is("c0e5582e-252f-4e94-8a49-e12b4b047afb"));
        assertThat(recipe.containsKey("title"), is(true));
        assertThat(recipe.containsKey("preamble"), is(true));
        assertThat(recipe.containsKey("noOfPeople"), is(true));
        assertThat(recipe.containsKey("ingredients"), is(true));
        final JsonArray ingredients = recipe.getJsonArray("ingredients");
        assertThat(ingredients, is(notNullValue()));
        ingredients.forEach(i -> { 
                assertThat(((JsonObject)i).containsKey("description"), is(true)); 
                assertThat(((JsonObject)i).containsKey("comment"), is(true));
                assertThat(((JsonObject)i).containsKey("portion"), is(true));
        });
        assertThat(recipe.containsKey("preparation"), is(true));
        assertThat(recipe.containsKey("tags"), is(true));
        final JsonArray tags = recipe.getJsonArray("tags");
        ingredients.forEach(t -> assertThat(t, is(notNullValue())));
        assertThat(tags, is(notNullValue()));
        assertThat(recipe.containsKey("rating"), is(true));
        assertThat(recipe.containsKey("addingDate"), is(true));
        assertThat(recipe.containsKey("editingDate"), is(true));
        assertThat(recipe.containsKey("image"), is(true));
    }
}
