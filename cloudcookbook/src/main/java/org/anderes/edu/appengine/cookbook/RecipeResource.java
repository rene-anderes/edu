package org.anderes.edu.appengine.cookbook;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.URI;
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

@Path("/recipes")
@Produces(APPLICATION_JSON)
public class RecipeResource {
    
    private RecipeRepository repository = new RecipeRepository();
    
    @GET
    @Path("/{id}")
    public Recipe findOne(@PathParam("id") Long id) {
        return repository.findOne(id);
    }
    
    @GET
    public List<Recipe> findAll() {
        return repository.findAll();
    }
    
    @POST
    @Consumes(APPLICATION_JSON)
    public Response save(Recipe recipe) {
        final Recipe savedRecipe = repository.save(recipe);
        final URI location = UriBuilder.fromPath("/services").path(RecipeResource.class).path(Long.toString(savedRecipe.getId())).build();
        return Response.created(location).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") Long id) {
        final Recipe recipe = repository.findOne(id);
        repository.delete(recipe);
        return Response.ok().build();
    }
}
