package org.anderes.edu.appengine.cookbook.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Test;

public class RestApiIT {
    
    private final static UriBuilder uri = UriBuilder.fromResource(MyApplication.class)
                    .scheme("http").host("localhost").port(8089).path(RecipeResource.class);
    
    @AfterClass
    public static void shutdown() {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(uri);
        final Response response = target.request(APPLICATION_JSON).buildGet().invoke();
        final JSONArray jsonArray = response.readEntity(JSONArray.class);
        deleteAll(jsonArray);
    }
    
    private static void deleteAll(JSONArray jsonArray) {
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(uri);
        for (Object object : jsonArray) {
            @SuppressWarnings("rawtypes")
            final Response response = target.path(((LinkedHashMap)object).get("id").toString()).request().delete();
            assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
        }
    }
    
    @Test
    public void shouldBeGetAllRecipes() {
        
        // given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(uri);
    
        // when
        final Response response = target.request(APPLICATION_JSON).buildGet().invoke();
        
        // then
        assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.getMediaType(), is(APPLICATION_JSON_TYPE));
        assertThat(response.hasEntity(), is(true));
     
        final JSONArray jsonArray = response.readEntity(JSONArray.class);
        assertThat(jsonArray, is(notNullValue()));
    }
    
    @Test
    public void shouldBeSaveNewRecipe() throws Exception {
        
        // given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(uri);
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe1.json");
        final JSONObject json = readJsonFile(jsonFile);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", UTF_8.displayName());
        final Entity<JSONObject> entity = Entity.entity(json, variant);

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
    public void shouldBePutRecipe() throws Exception {
        // given
        final Client client = ClientBuilder.newClient();
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe2.json");
        final JSONObject json = readJsonFile(jsonFile);
        final WebTarget target = client.target(uri);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", UTF_8.displayName());
        final Entity<JSONObject> entity = Entity.entity(json, variant);

        // when
        final Response responseFromSave = target.path(json.get("id").toString()).request(APPLICATION_JSON).put(entity);
        
        // then
        assertThat(responseFromSave.getStatus(), is(CREATED.getStatusCode()));
        final URI uriForSavedRecipe = target.getUri();
        final WebTarget targetForGetOne = client.target(uriForSavedRecipe);
        final Response responseFromGet = targetForGetOne.request(APPLICATION_JSON).get();
        assertThat(responseFromGet.getStatus(), is(OK.getStatusCode()));
    }
    
    
    @Test
    public void shouldBeReadJsonFile() throws Exception {
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe1.json");
        final JSONObject jsonObject = readJsonFile(jsonFile);
        assertThat(jsonObject, is(notNullValue()));
        final String name = (String) jsonObject.get("title");
        assertThat("Torroneparfait", is(name));
    }

    private JSONObject readJsonFile(Path jsonFile) throws IOException, ParseException, FileNotFoundException {
        final JSONParser parser = new JSONParser();
        final Object obj = parser.parse(new FileReader(jsonFile.toFile()));
        return (JSONObject) obj;
    }
}
