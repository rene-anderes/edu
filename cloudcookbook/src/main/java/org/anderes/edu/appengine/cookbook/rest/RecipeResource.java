package org.anderes.edu.appengine.cookbook.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.appengine.cookbook.RecipeRepository;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.anderes.edu.appengine.cookbook.dto.TagDto;

@Path("recipes")
@Produces(APPLICATION_JSON)
public class RecipeResource {
    
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private RecipeRepository repository = new RecipeRepository();
    
    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Recipe findOne(@PathParam("id") String id) {
        return repository.findOne(id);
    }
    
    @GET
    @Produces(APPLICATION_JSON)
    public Response findAll() {
        final List<RecipeShort> recipes = repository.getRecipeCollection();
        final CacheControl cache = new CacheControl();
        cache.setMaxAge(10);
        cache.setSMaxAge(10);
        return Response.ok().cacheControl(cache).encoding(UTF_8.displayName()).entity(recipes).build();
    }
    
    @POST
    @Consumes(APPLICATION_JSON)
    public Response insert(Recipe recipe) {
        logger.fine("POST - Recipe: " + recipe);
        final Recipe savedRecipe = repository.save(recipe);
        final URI location = UriBuilder.fromResource(RecipeResource.class).path(savedRecipe.getId()).build();
        return Response.created(location).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(APPLICATION_JSON)
    public Response save(@PathParam("id") String id, Recipe recipe) {
        logger.fine("PUT - Recipe: " + recipe);
        if (!id.equals(recipe.getId())) {
            logger.warning("Path-id '" + id + "' ist ungleich Recipe-id '" + recipe.getId() + "'");
            throw new WebApplicationException("Ungültige Parameter", BAD_REQUEST);
        }
        if (repository.exists(recipe)) {
            repository.save(recipe);
            return Response.ok().build();
        }
        final Recipe savedRecipe = repository.save(recipe);
        final URI location = UriBuilder.fromResource(RecipeResource.class).path(savedRecipe.getId()).build();
        return Response.created(location).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteOne(@PathParam("id") String id) {
        logger.fine("DELETE - Recipe with id: " + id);
        repository.delete(id);
        return Response.ok().build();
    }
    
    @GET
    @Path("tags")
    @Produces(APPLICATION_JSON)
    public Response tags() {
        final ArrayList<TagDto> tags = mapToTagDto(repository.findAllTags());
        return Response.ok(tags).build();
    }
    
    public ArrayList<TagDto> mapToTagDto(final Map<String, Integer> tags) {
        final ArrayList<TagDto> dto = new ArrayList<TagDto>(tags.size());
        for (String key : tags.keySet()) {
            dto.add(new TagDto(key, tags.get(key)));
        }
        return dto;
    }
}
