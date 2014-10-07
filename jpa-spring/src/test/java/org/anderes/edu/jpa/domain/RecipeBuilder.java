package org.anderes.edu.jpa.domain;

import java.util.UUID;


public class RecipeBuilder {

	public static Recipe buildRecipe() {
		final Recipe recipe = new Recipe(UUID.randomUUID().toString());
		recipe.setTitle("Meine neues Rezept");
		recipe.setNoOfPerson("2");
		recipe.setPreparation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque convallis lacus at sagittis scelerisque. Donec at mauris ac mi pharetra.");
		addIngredients(recipe);
		return recipe;
	}
	
	public static void addIngredients(final Recipe recipe) {
		recipe.addIngredient(new Ingredient("1", "Tomate", "Vollreif"));
		recipe.addIngredient(new Ingredient("2", "Peperoni", null));
		recipe.addIngredient(new Ingredient("etwas", "Pfeffer", "schwarz"));
	}
}
