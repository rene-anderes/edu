package org.anderes.edu.appengine.cookbook;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/recipes")
@Produces(APPLICATION_JSON)
public class RecipeResource {
    
    private RecipeRepository repository = new RecipeRepository();
    
    @GET
    @Path("/{id}")
    public Recipe findOne(@PathParam("id") Long id) {
        return repository.findOne(id);
    }
}
