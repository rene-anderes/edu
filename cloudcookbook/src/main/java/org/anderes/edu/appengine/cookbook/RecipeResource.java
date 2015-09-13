package org.anderes.edu.appengine.cookbook;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.googlecode.objectify.ObjectifyService;

@Path("/recipes")
@Produces(APPLICATION_JSON)
public class RecipeResource {
    static {
        ObjectifyService.register(Recipe.class);
    }
    

    @GET
    @Path("/{id}")
    public Recipe findOne(@QueryParam("id") Long id) {
        
        return new Recipe();
    }
}
