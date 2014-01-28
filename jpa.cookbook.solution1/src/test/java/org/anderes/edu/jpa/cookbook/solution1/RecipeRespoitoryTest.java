package org.anderes.edu.jpa.cookbook.solution1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.PersistenceUnitUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RecipeRespoitoryTest {
	
	private RecipeResository repository;
	
	@Before
	public void setup() {
		repository = RecipeResository.build();
	}

	@Test
	public void getRecipesByTitle() {
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
		
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		Recipe recipe = recipes.iterator().next();
		assertThat(recipe.getTitle(), is("Dies und Das"));
		assertThat(recipe.getIngredients().size(), is(4));
	}
	
	@Test
	public void shouldBeSaveNewRecipe() {
		final Recipe savedRecipe = repository.save(RecipeBuilder.buildRecipe());
		assertThat(savedRecipe, is(not(nullValue())));
		assertThat(savedRecipe.getId(), is(not(nullValue())));
	}
	
	@Test
	public void shouldBeUpdateRecipe() {
		final Recipe updateRecipe = repository.findOne(Long.valueOf(10001L));
		updateRecipe.setPreample("Neuer Preample vom Test");
		final Recipe savedRecipe = repository.save(updateRecipe);
		
		assertThat(savedRecipe, is(not(nullValue())));
		assertThat(savedRecipe.getPreample(), is("Neuer Preample vom Test"));
	}
	
	@Test
	public void shouldBeDelete() {
		final Recipe toDelete = repository.findOne(Long.valueOf(10002L));
		repository.remove(toDelete);
		
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Pasta");
		assertNotNull(recipes);
		assertThat(recipes.size(), is(0));
	}
	
	@Test
	@Ignore("Offen Punkte betreffend EntityGraph klären")
	public void ingredientsShouldBeloaded() {
		RecipeResository repository = RecipeResository.build();
		Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
		
		PersistenceUnitUtil util = repository.getPersistenceUnitUtil();
		assertTrue(util.isLoaded(recipes, "ingredients"));
	}

}
