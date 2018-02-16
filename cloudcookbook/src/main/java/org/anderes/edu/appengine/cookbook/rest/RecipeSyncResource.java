package org.anderes.edu.appengine.cookbook.rest;

import static java.lang.Boolean.TRUE;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.appengine.cookbook.dto.RecipeDto;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.anderes.edu.appengine.cookbook.objectify.RecipeRepository;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

@Path("recipes")
public class RecipeSyncResource {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final RecipeRepository repository = new RecipeRepository();

    
    @POST
    @Path("replication/async")
    public void recipeReplicationAsync(@Suspended final AsyncResponse ar) {
        
    	final UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
    	final Response response = buildClient().target(restUrl).request(APPLICATION_JSON_TYPE).get();
    	if (response.getStatus() != OK.getStatusCode() || !response.hasEntity()) {
    		final String message = "Synchronisationsfehler";
    		logger.log(WARNING, message);
    		ar.resume(new WebApplicationException(message));
    	}

    	Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
            	deleteAllRecipes();
        		final List<RecipeShort> recipes = response.readEntity(new GenericType<List<RecipeShort>>() {});
        		for (RecipeShort recipeShort : recipes) {
        			Optional<RecipeDto> recipe = getRecipeFromSource(recipeShort.getId());
        			recipe.ifPresent(r -> {
        				repository.save(r);
        				logger.log(INFO, String.format("Rezept mit ID %s gespeichert.", r.getId()));
        			});
        		}
            }
    	});
    	ar.resume("OK");
    }
    
    private Client buildClient() {
    	ClientConfig configuration = new ClientConfig();
		configuration.register(JacksonFeature.class)
			.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE_CLIENT, TRUE)
			.property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_CLIENT, TRUE);
		return ClientBuilder.newClient(configuration);
	}

	@POST
    @Path("replication")
    public void recipeReplication() {
    	final UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
    	final Response response = buildClient().target(restUrl).request(APPLICATION_JSON_TYPE).get();
    	if (response.getStatus() != OK.getStatusCode() || !response.hasEntity()) {
    		final String message = "Synchronisationsfehler";
    		logger.log(WARNING, message);
    		throw new WebApplicationException(message);
    	}

    	deleteAllRecipes();
    	final List<RecipeShort> recipes = response.readEntity(new GenericType<List<RecipeShort>>() {});
    	for (RecipeShort recipeShort : recipes) {
    		Optional<RecipeDto> recipe = getRecipeFromSource(recipeShort.getId());
    		recipe.ifPresent(r -> {
    			repository.save(r);
    			logger.log(INFO, String.format("Rezept mit ID %s gespeichert.", r.getId()));
    		});
    	}
    }
    
    private void deleteAllRecipes() {
        final List<RecipeDto> recipes = repository.findAll();
        for (RecipeDto recipe : recipes) {
            repository.delete(recipe);
            logger.log(INFO, String.format("Rezept mit ID %s gelöscht.", recipe.getId()));
        }
    }

    private Optional<RecipeDto> getRecipeFromSource(final String id) {
        final UriBuilder restUrl = UriBuilder.fromPath("resources-api").path("recipes").host("www.anderes.org").scheme("http");
        final Response response = buildClient().target(restUrl.path(id)).request(APPLICATION_JSON_TYPE).get();
        final int status = response.getStatus();
        if (status == OK.getStatusCode() && response.hasEntity()) {
            logger.log(INFO, String.format("Rezept mit ID %s via REST empfangen.", id));
            return Optional.of(response.readEntity(RecipeDto.class));
        }
        logger.log(WARNING, String.format("Synchronisationsfehler: Responsstatus = '%s'", status));
        return Optional.empty();
    }
}
