package org.anderes.edu.appengine.cookbook;

import static com.googlecode.objectify.ObjectifyService.ofy;


public class RecipeRepository {

    public Recipe findOne(final Long id) {
    
        return null;
    }

    public Recipe save(final Recipe newRecipe) {
        ofy().save().entity(newRecipe).now();
        return newRecipe;
    }
    
    public void delete(final Recipe recipe) {
        
    }
}
