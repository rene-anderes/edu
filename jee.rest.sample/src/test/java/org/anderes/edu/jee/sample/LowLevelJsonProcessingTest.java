package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.anderes.edu.jee.rest.sample.LowLevelJsonProcessing;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class LowLevelJsonProcessingTest extends JerseyTest {
     
    @Override
    protected Application configure() {
        return new ResourceConfig(LowLevelJsonProcessing.class);
    }
 
    @Test
    public void shoudBeAllRecipes() {
                
        final Response response = target("recipes").request(APPLICATION_JSON_TYPE).get();
        
        assertThat(response.getStatusInfo(), is(OK));
        assertThat(response.hasEntity(), is(true));
        final JsonArray recipes = response.readEntity(JsonArray.class);
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.size(), is(2));
    }
    
    @Test
    public void shouldBeOneRecipe() {
                
        final Response response = target("recipes/2006").request(APPLICATION_JSON_TYPE).get();
        
        assertThat(response.getStatusInfo(), is(OK));
        assertThat(response.hasEntity(), is(true));
        final JsonObject recipe = response.readEntity(JsonObject.class);
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.getString("title"), is("Tiramisu"));
    }
}
