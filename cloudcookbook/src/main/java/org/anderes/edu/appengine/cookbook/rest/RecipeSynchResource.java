package org.anderes.edu.appengine.cookbook.rest;

import static java.lang.Boolean.TRUE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;
import static java.util.logging.Level.*;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.appengine.cookbook.RecipeRepository;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

@Path("recipes")
public class RecipeSynchResource {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final RecipeRepository repository = new RecipeRepository();
    private final Client client = ClientBuilder.newBuilder()
            .register(JacksonFeature.class)
            .property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
            .property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_CLIENT, TRUE)
            .build();
    
    @POST
    @Path("sync")
    public Response recipeSync() {
        deleteAllRecipes();
        final UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
        final Response response = client.target(restUrl).request(APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == OK.getStatusCode() && response.hasEntity()) {
            final List<RecipeShort> recipes = response.readEntity(new GenericType<List<RecipeShort>>() {});
            for (RecipeShort recipeShort : recipes) {
                Recipe recipe = getRecipeFromSource(recipeShort.getId());
                repository.save(recipe);
                logger.log(INFO, String.format("Rezept mit ID %s gespeichert.", recipe.getId()));
            }
            return Response.ok().build();
        }
        final String message = "Synchronisationsfehler";
        logger.log(WARNING, message);
        throw new WebApplicationException(message);
    }
    
    private void deleteAllRecipes() {
        final List<Recipe> recipes = repository.findAll();
        for (Recipe recipe : recipes) {
            repository.delete(recipe);
            logger.log(INFO, String.format("Rezept mit ID %s gelöscht.", recipe.getId()));
        }
    }

    private Recipe getRecipeFromSource(final String id) {
        final UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
        final Response response = client.target(restUrl.path(id)).request(APPLICATION_JSON_TYPE).get();
        final int status = response.getStatus();
        if (status == OK.getStatusCode() && response.hasEntity()) {
            logger.log(INFO, String.format("Rezept mit ID %s via REST empfangen.", id));
            return response.readEntity(Recipe.class);
        }
        logger.log(WARNING, String.format("Synchronisationsfehler: Responsstatus = '%s'", status));
        throw new NotFoundException(String.format("Synchronisationsfehler: Rezept mit ID %s nicht gefunden!", id));
    }
}
