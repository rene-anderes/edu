package org.anderes.edu.orientdb.sample;

import static org.apache.commons.lang3.RandomStringUtils.*;


public class RecipeBuilder {

	public static Recipe buildRecipe(final Recipe recipe) {
		recipe.setTitle(randomAlphabetic(10));
		recipe.setNoOfPerson(randomNumeric(1));
		recipe.setPreparation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.");
		return recipe;
	}
	
	public static Ingredient buildIngredients(final Ingredient ingredient) {
	    ingredient.setQuantity(randomNumeric(1));
	    ingredient.setDescription(randomAlphanumeric(20));
	    ingredient.setAnnotation(randomAlphabetic(10));
	    return ingredient;
	}
}
