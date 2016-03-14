package org.anderes.edu.client.rest.jersey;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.client.rest.jersey.dto.Recipe;
import org.anderes.edu.client.rest.jersey.dto.RecipeShort;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoxyPojoRestClientTest {

    private UriBuilder restUrl;
    private Client client;

    @Before
    public void setUp() throws Exception {
        System.getProperties().put("http.proxySet", "true");
        System.getProperties().put("http.proxyHost", "localhost");
        System.getProperties().put("http.proxyPort", "8080");
        
        restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
        
        /** 
         * Hier wird das MOXy Feature automatisch registriert.
         * siehe https://jersey.java.net/documentation/latest/user-guide.html#json.moxy 
         */
        client = ClientBuilder.newClient();

    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void shouldBeFindAll() {
        final Response response = client.target(restUrl).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final List<RecipeShort> recipes = response.readEntity(new GenericType<List<RecipeShort>>() {});
        assertThat(recipes, is(notNullValue()));
        recipes.forEach(element -> assertThat(((RecipeShort)element).getId(), is(notNullValue())));
    }
    
    @Test
    public void shouldBeOneRecipe() {
        final Response response = client.target(restUrl.path("c0e5582e-252f-4e94-8a49-e12b4b047afb")).request(APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final Recipe recipe = response.readEntity(Recipe.class);
        assertThat(recipe.getId(), is(notNullValue()));
        assertThat(recipe.getId(), is("c0e5582e-252f-4e94-8a49-e12b4b047afb"));
        assertThat(recipe.getTitle(), is("Arabische Spaghetti"));
    }
}
