package org.anderes.edu.appengine.cookbook;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;


public class RecipeRepository {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
    /**
     * Findet ein einzelnes Rezept
     * 
     * @param id Datenbankidentität
     * @return Rezept
     * @throws com.googlecode.objectify.NotFoundException falls die Entität mir der entsprechenden ID nicht existiert
     */
    public Recipe findOne(final String id) {
        Validate.notNull(id, "Parameter id darf nicht null sein");
        return ofy().load().type(Recipe.class).id(id).safe();
    }

    public Recipe save(final Recipe recipe) {
        Validate.notNull(recipe, "Parameter id darf nicht null sein");
        if (recipe.getId() == null) {
            recipe.setId(UUID.randomUUID().toString());
        }
        recipe.setEditingDate(new Date());
        if (recipe.getAddingDate() == null) {
            recipe.setAddingDate(new Date());
        }
        final Key<Recipe> key = ofy().save().entity(recipe).now();
        logger.fine("Rezept mit Key '" + key.toWebSafeString() + "' gespeichert.");
        return recipe;
    }

    /**
     * Löscht ein einzelnes Rezept
     * 
     * @param id Datenbankidentität
     * @throws com.googlecode.objectify.NotFoundException falls die Entität mir der entsprechenden ID nicht existiert
     */
    public void delete(final String recipeId) {
        Validate.notNull(recipeId, "Parameter recipeId darf nicht null sein");
        delete(findOne(recipeId));
    }
    
    public void delete(final Recipe recipe) {
        Validate.notNull(recipe, "Parameter recipe darf nicht null sein");
        ofy().delete().entity(recipe).now();
    }

    public Collection<Recipe> findByTitle(final String title) {
        return ofy().load().type(Recipe.class).filter("title >=", title).filter("title <", title + "\ufffd").list();
    }

    public List<Recipe> findAll() {
        return ofy().load().type(Recipe.class).list();
    }
    
    public List<RecipeShort> getRecipeCollection() {
        final List<Recipe> collection = findAll();
        final ArrayList<RecipeShort> recipes = new ArrayList<>(collection.size());
        for (Recipe recipe : collection) {
            recipes.add(new RecipeShort(recipe.getTitle(), recipe.getId(), recipe.getEditingDate()));
        }
        return recipes;
    }

    public boolean exists(final Recipe recipe) {
        Validate.notNull(recipe, "Parameter recipe darf nicht null sein");
        Validate.notNull(recipe.getId(), "Die Rezept-Id darf nicht null sein");
        try {
            findOne(recipe.getId());
        } catch (NotFoundException e) {
            return false;
        }
        return true;
    }
    
    public Map<String, Integer> findAllTags() {
        final List<Recipe> recipes = findAll();
        final List<String> tagCollection = new ArrayList<>(recipes.size());
        for (Recipe recipe : recipes) {
            tagCollection.addAll(recipe.getTags());
        }
        return toMap(tagCollection);
    }

    private Map<String, Integer> toMap(final List<String> tagCollection) {
        final HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String tag : tagCollection ) {
            if (StringUtils.isEmpty(tag)) {
                continue;
            }
            if (map.containsKey(tag)) {
                map.put(tag, map.get(tag) + 1);
            } else {
                map.put(tag, 1);
            }
        }
        return map;
    }
}
