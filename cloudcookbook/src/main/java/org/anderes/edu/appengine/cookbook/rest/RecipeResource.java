package org.anderes.edu.appengine.cookbook.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.appengine.cookbook.RecipeRepository;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;

@Path("recipes")
@Produces(APPLICATION_JSON)
public class RecipeResource {
    
    private RecipeRepository repository = new RecipeRepository();
    
    @GET
    @Path("/{id}")
    public Recipe findOne(@PathParam("id") String id) {
        return repository.findOne(id);
    }
    
    @GET
    public Response findAll() {
        final List<RecipeShort> recipes = repository.getRecipeCollection();
        return Response.ok().encoding(StandardCharsets.UTF_8.displayName()).entity(recipes).build();
    }
    
    @POST
    @Consumes(APPLICATION_JSON)
    public Response save(Recipe recipe) {
        final Recipe savedRecipe = repository.save(recipe);
        final URI location = UriBuilder.fromResource(RecipeResource.class).path(savedRecipe.getId()).build();
        return Response.created(location).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteOne(@PathParam("id") String id) {
        final Recipe recipe = repository.findOne(id);
        repository.delete(recipe);
        return Response.ok().build();
    }
}
