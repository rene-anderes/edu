package org.anderes.edu.client.rest.jersey;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecipeRestApiTest {
    
    private UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
    private File tempFile;

    @Before
    public void setUp() throws Exception {
        System.getProperties().put("http.proxySet", "true");
        System.getProperties().put("http.proxyHost", "localhost");
        System.getProperties().put("http.proxyPort", "8080");
        
        tempFile = File.createTempFile("jsonTest", "json");
    }

    @After
    public void tearDown() throws Exception {
        assertThat(tempFile.delete(), is(true));
    }

    @Test
    public void shouldBeBackupAllRecipe() {
        final JsonArray array = getJsonFromServer(restUrl, JsonArray.class);

        assertThat(array, is(notNullValue()));
        array.forEach(element -> assertThat(((JsonObject)element).containsKey("id"), is(true)));
    }
    
    @Test
    public void shouldBeOneRecipe() {
        final JsonObject recipe = getJsonFromServer(restUrl.path("c0e5582e-252f-4e94-8a49-e12b4b047afb"), JsonObject.class);
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.containsKey("id"), is(true));
        assertThat(recipe.getString("id"), is("c0e5582e-252f-4e94-8a49-e12b4b047afb"));
        assertThat(recipe.containsKey("title"), is(true));
        assertThat(recipe.containsKey("preample"), is(true));
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
        System.out.println(recipe);
    }
    
    @Test
    public void shouldBeWriteJsonFile() throws IOException {
        
        // given
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        final JsonObject recipe = builder.add("id", "c0e5582e-252f-4e94-8a49-e12b4b047afc")
                        .add("noOfPeople", "2")
                        .add("ingredients", createIngredients())
                        .add("tags", createTags())
                        .add("title", "Arabische Spaghetti")
                        .add("preparation", "Pouletfleisch in schmale Streifen schneiden und kurz anbraten.")
                        .add("preample", "Dieses Rezept ...")
                        .add("rating", "5")
                        .add("addingDate", "1250959818424")
                        .add("editingDate", "1412436923512")
                        .add("image", "")
                        .build();
        final JsonArray recipes = Json.createArrayBuilder().add(recipe).build();
        final Path path = Paths.get(tempFile.toURI());
        
        // when
        writeJsonToFile(recipes, path);
        
        // then
        assertThat(tempFile.exists(), is(true));
        assertThat(tempFile.getTotalSpace() > 500, is(true));
        
    }
    
    private JsonArray createIngredients() {
        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        final JsonObject ingredient1 = objectBuilder.add("description", "Spaghetti").add("portion", "250g").add("comment", "").build();
        final JsonObject ingredient2 = objectBuilder.add("description", "Sultaninen").add("portion", "2 EL").add("comment", "").build();
        final JsonObject ingredient3 = objectBuilder.add("description", "Salz, Pfeffer und Olivenöl").add("portion", "").add("comment", "").build();
        return Json.createArrayBuilder().add(ingredient1).add(ingredient2).add(ingredient3).build();
    }
    
    private JsonArray createTags() {
        return Json.createArrayBuilder().add("pasta").add("fleisch").build();
    }

    private <T> T getJsonFromServer(final UriBuilder uri, final Class<T> clazz) {
        final Client client = ClientBuilder.newClient(new ClientConfig().register(JsonProcessingFeature.class));
        return client.target(uri).request(MediaType.APPLICATION_JSON_TYPE).get(clazz);
    }
    
    private void writeJsonToFile(final JsonArray json, final Path path) throws IOException {
        final Writer writer = new FileWriter(path.toFile());
        writer.write(json.toString());
        writer.close();
    }
}
