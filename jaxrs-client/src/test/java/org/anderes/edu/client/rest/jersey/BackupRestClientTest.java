package org.anderes.edu.client.rest.jersey;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.jaxrs.client.dto.IngredientResource;
import org.anderes.edu.jaxrs.client.dto.Link;
import org.anderes.edu.jaxrs.client.dto.RecipeCollection;
import org.anderes.edu.jaxrs.client.dto.RecipeResource;
import org.anderes.edu.jaxrs.client.dto.RecipeShortResource;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BackupRestClientTest {
    
    private UriBuilder restUrl;
    private Client client;

    @Before
    public void setUp() throws Exception {
      
        restUrl = UriBuilder.fromPath("resources-api").path("recipes-repository").host("www.anderes.org").scheme("http");
        
        /** 
         * Hier wird das Jackson Feature registriert.
         * Eine manuelle Registrierung eines Feature verhindert, dass Jersey
         * die Feature automatisch registriert, daher wäre das "disable" 
         * des auto discovery gar nicht notwendig. Wird hier jedoch exemplarisch explizit gemacht.
         * siehe https://jersey.java.net/documentation/latest/user-guide.html#json.jackson 
         */
        client = ClientBuilder.newBuilder()
                        .register(JacksonFeature.class)
                        .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
                        .property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_CLIENT, TRUE)
                        .build();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void shouldBeFindAllRecipe() throws JsonMappingException, IOException {
        final Response response = client.target(restUrl).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        
        final RecipeCollection content = response.readEntity(RecipeCollection.class);
        final List<RecipeShortResource> array = content.getContent();
        assertThat(array, is(notNullValue()));
        
        final List<RecipeResource> recipes = array.parallelStream().map(element -> {
            final List<Link> links = element.getLinks();
            assertThat(links.stream().filter(link -> link.getRel().equalsIgnoreCase("self")).count(), is(1L));
            final Optional<Link> selfLink = links.stream().filter(link -> link.getRel().equalsIgnoreCase("self")).findFirst();
            assertThat(selfLink.isPresent(), is(true));
            final String url = selfLink.get().getHref();
            return getRecipe(url);
        }).collect(Collectors.toList());
        
        assertThat(recipes.size(), is(content.getNumberOfElements()));
        dumpRecipeCollection(recipes);
    }
    
    private void dumpRecipeCollection(final List<RecipeResource> recipes) throws JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final File dumpFile = File.createTempFile("Recipes", ".json");
        System.out.printf("Dump-File erstellt: %s %n", dumpFile.getAbsolutePath());
        mapper.writeValue(dumpFile, recipes);
    }
    
    private RecipeResource getRecipe(final String url) {
        final Response response = client.target(url).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final RecipeResource recipe = response.readEntity(RecipeResource.class);
        assertThat(recipe.getLinks(), is(notNullValue()));
        
        final List<Link> links = recipe.getLinks();
        assertThat(links.stream().filter(link -> link.getRel().equalsIgnoreCase("ingredients")).count(), is(1L));
        final Optional<Link> ingredientsLink = links.stream().filter(link -> link.getRel().equalsIgnoreCase("ingredients")).findFirst();
        assertThat(ingredientsLink.isPresent(), is(true));
        final String ingredientUrl = ingredientsLink.get().getHref();
        recipe.setIngredients(getIngredients(ingredientUrl));
        return recipe;
    }

    private List<IngredientResource> getIngredients(final String url) {
        final Response response = client.target(url).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final List<IngredientResource> list = response.readEntity(new GenericType<List<IngredientResource>>(){});
        return list;
    }
}
