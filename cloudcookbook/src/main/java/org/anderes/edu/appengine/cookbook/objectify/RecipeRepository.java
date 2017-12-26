package org.anderes.edu.appengine.cookbook.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.anderes.edu.appengine.cookbook.dto.ImageDto;
import org.anderes.edu.appengine.cookbook.dto.IngredientDto;
import org.anderes.edu.appengine.cookbook.dto.RecipeDto;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.apache.commons.lang3.Validate;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;

public class RecipeRepository {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Findet ein einzelnes Rezept
	 * 
	 * @param id
	 *            Datenbankidentität
	 * @return Rezept
	 * @throws com.googlecode.objectify.NotFoundException
	 *             falls die Entität mir der entsprechenden ID nicht existiert
	 */
	public RecipeDto findOne(final String id) {
		Validate.notNull(id, "Parameter id darf nicht null sein");
		
		final Recipe recipe = ofy().load().type(Recipe.class).id(id).safe();
		return mapToNewRecipeDto(recipe);
	}

	private RecipeDto mapToNewRecipeDto(Recipe recipe) {
		final RecipeDto recipeDto = new RecipeDto();
		recipe.getIngredients().stream()
				.map(i -> new IngredientDto(i.getPortion(), i.getDescription(), i.getComment()))
				.forEach(i -> recipeDto.addIngredient(i));
		recipe.getImage().ifPresent(image -> recipeDto.setImage(new ImageDto(image.getUrl(), image.getDescription())));

		recipeDto.setId(recipe.getId());
		recipeDto.setTitle(recipe.getTitle());
		recipeDto.setPreamble(recipe.getPreamble());
		recipeDto.setPreparation(recipe.getPreparation());
		recipeDto.setNoOfPeople(recipe.getNoOfPeople());
		recipeDto.setAddingDate(recipe.getAddingDate());
		recipeDto.setEditingDate(recipe.getEditingDate());
		recipeDto.setRating(recipe.getRating());
		recipeDto.setTags(recipe.getTags());
		return recipeDto;
	}

	public RecipeDto save(final RecipeDto recipeDto) {
		Validate.notNull(recipeDto, "Parameter recipeDto darf nicht null sein");
		final Recipe recipe = mapToRecipe(recipeDto, new Recipe());
		if (isBlank(recipeDto.getId())) {
		} else {
			Recipe r = ofy().load().type(Recipe.class).id(recipeDto.getId()).now();
			if (r != null) {
				r.getImage().ifPresent(i -> deleteImage(i));
				r.getIngredients().stream().forEach(i -> deleteIngredient(i));
			} else {
				recipe.setId(recipeDto.getId());
			}
		}
		recipe.setEditingDate(new Date());
		if (recipe.getAddingDate() == null) {
			recipe.setAddingDate(new Date());
		}
		if (recipeDto.getImage() != null) {
			final Image image = new Image(recipeDto.getImage().getUrl(), recipeDto.getImage().getDescription());
			ofy().save().entity(image).now();
			recipe.setImage(image);
		}
		final List<Ingredient> ingredients = recipeDto.getIngredients().stream()
				.map(i -> new Ingredient(i.getPortion(), i.getDescription(), i.getComment()))
				.collect(Collectors.toList());
		ingredients.stream().forEach(i -> ofy().save().entity(i).now());
		ingredients.stream().forEach(i -> recipe.addIngredient(i));
		final Key<Recipe> key = ofy().save().entity(recipe).now();
		logger.fine("Rezept mit Key '" + key.toWebSafeString() + "' gespeichert.");
		final Recipe newRecipe = ofy().load().type(Recipe.class).id(recipe.getId()).safe();
		return mapToNewRecipeDto(newRecipe);
	}
	
	private void deleteIngredient(final Ingredient ingredient) {
		ofy().delete().entity(ingredient).now();
	}

	private void deleteImage(final Image image) {
		ofy().delete().entity(image).now();
	}

	private Recipe mapToRecipe(final RecipeDto recipeDto, final Recipe recipe) {
		recipe.setTitle(recipeDto.getTitle());
		recipe.setPreamble(recipeDto.getPreamble());
		recipe.setPreparation(recipeDto.getPreparation());
		recipe.setNoOfPeople(recipeDto.getNoOfPeople());
		recipe.setAddingDate(recipeDto.getAddingDate());
		recipe.setEditingDate(recipeDto.getEditingDate());
		recipe.setRating(recipeDto.getRating());
		recipe.setTags(recipeDto.getTags());
		return recipe;
	}

	/**
	 * Löscht ein einzelnes Rezept
	 * 
	 * @param id
	 *            Datenbankidentität
	 * @throws com.googlecode.objectify.NotFoundException
	 *             falls die Entität mir der entsprechenden ID nicht existiert
	 */
	public void delete(final String recipeId) {
		Validate.notNull(recipeId, "Parameter recipeId darf nicht null sein");
		final Recipe recipe = ofy().load().type(Recipe.class).id(recipeId).safe();
		recipe.getImage().ifPresent(image -> deleteEntity(image));
		recipe.getIngredients().forEach(i -> deleteEntity(i));
		deleteEntity(recipe);
	}

	private void deleteEntity(Object entity) {
		ofy().delete().entity(entity).now();
	}

	public void delete(final RecipeDto recipe) {
		Validate.notNull(recipe, "Parameter recipe darf nicht null sein");
		Validate.notNull(recipe.getId(), "Die Rezept-Id darf nicht null sein");
		delete(recipe.getId());
	}

	public List<RecipeDto> findAll() {
		final List<Recipe> recipes = ofy().load().type(Recipe.class).list();
		return recipes.stream().map(r -> mapToNewRecipeDto(r)).collect(Collectors.toList());
	}

	public List<RecipeShort> getRecipeCollection() {
	    
	    final List<Recipe> iterator = ofy().load().type(Recipe.class).list();
	    final List<RecipeShort> recipes = iterator.stream()
	    		.map(recipe -> new RecipeShort(recipe.getTitle(), recipe.getId(), recipe.getEditingDate()))
	    		.sorted()
	    		.collect(Collectors.toList());
		return recipes;
	}

	public boolean exists(final RecipeDto recipe) {
		Validate.notNull(recipe, "Parameter recipe darf nicht null sein");
		Validate.notNull(recipe.getId(), "Die Rezept-Id darf nicht null sein");
		try {
			findOne(recipe.getId());
		} catch (NotFoundException e) {
			return false;
		}
		return true;
	}

	public boolean exist(final String recipeId) {
		try {
			findOne(recipeId);
		} catch (NotFoundException e) {
			return false;
		}
		return true;
	}

	public Map<String, Integer> findAllTags() {
		final List<RecipeDto> recipes = findAll();
		final List<String> tagCollection = new ArrayList<>(recipes.size());
		recipes.stream().forEach(recipe -> tagCollection.addAll(recipe.getTags()));
		return toMap(tagCollection);
	}

	private Map<String, Integer> toMap(final List<String> tagCollection) {
		final HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (String tag : tagCollection) {
			if (isEmpty(tag)) {
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
