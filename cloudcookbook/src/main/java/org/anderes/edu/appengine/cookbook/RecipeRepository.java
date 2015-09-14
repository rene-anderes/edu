package org.anderes.edu.appengine.cookbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;

import com.googlecode.objectify.NotFoundException;


public class RecipeRepository {

    /**
     * Findet ein einzelnes Rezept
     * 
     * @param id Datenbankidentität
     * @return Rezept
     * @throws NotFoundException falls die Entität mir der entsprechenden ID nicht existiert
     */
    public Recipe findOne(final Long id) {
        return ofy().load().type(Recipe.class).id(id).safe();
    }

    public Recipe save(final Recipe newRecipe) {
        ofy().save().entity(newRecipe).now();
        return newRecipe;
    }
    
    public void delete(final Recipe recipe) {
        ofy().delete().entity(recipe).now();
    }

    public Collection<Recipe> findByTitle(final String title) {
        return ofy().load().type(Recipe.class).filter("title >=", title).filter("title <", title + "\ufffd").list();
    }
    
}
