package org.anderes.edu.appengine.cookbook.rest;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.anderes.edu.appengine.cookbook.rest.MyApplication;
import org.anderes.edu.appengine.cookbook.rest.RecipeResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class RestApiIT {
    
    final UriBuilder uri = UriBuilder.fromResource(MyApplication.class)
            .scheme("http").host("localhost").port(8089).path(RecipeResource.class);
    
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
       
        final JSONArray json = response.readEntity(JSONArray.class);
        assertThat(json, is(notNullValue()));
    }
    
    @Test
    public void shouldBeSaveNewRecipe() throws Exception {
        
        // given
        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(uri);
        final Path jsonFile = Paths.get(".", "target", "test-classes", "recipe1.json");
        final JSONObject json = readJsonFile(jsonFile);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", StandardCharsets.UTF_8.displayName());
        final Entity<JSONObject> entity = Entity.entity(json, variant);

        // when
        final Response responseFromSave = target.request(APPLICATION_JSON).buildPost(entity).invoke();
        
        // then
        assertThat(responseFromSave.getStatus(), is(CREATED.getStatusCode()));
        final URI uriForNewRecipe = responseFromSave.getLocation();
        assertThat(uriForNewRecipe, is(notNullValue()));
        final WebTarget targetForGetOne = client.target(uriForNewRecipe);
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
