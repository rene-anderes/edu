package org.anderes.edu.jpa.cookbook.solution1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class RecipeRepositoryTest {
	
	private RecipeRepository repository;
	
	@Before
	public void setup() {
		repository = RecipeRepository.build();
	}

	@Test
	public void shouldBeOneRecipe() {
		final Recipe recipe = repository.findOne(10001l);
		
		assertNotNull(recipe);
		assertThat(recipe.getTitle(), is("Dies und Das"));
		assertThat(repository.getPersistenceUnitUtil().isLoaded(recipe, Recipe_.ingredients.getName()), is(false));
		assertThat(recipe.getIngredients().size(), is(4)); // LAZY-Loading
		System.out.println(recipe);
	}
	
	@Test
	public void shouldBeFindRecipesByTitle() {
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
		
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		final Recipe recipe = recipes.iterator().next();
		assertThat(recipe.getTitle(), is("Dies und Das"));
		assertThat(recipe.getIngredients().size(), is(4));
	}
	
	@Test
	public void shouldBeFindRecipesByIngredient() {
	    final Collection<Recipe> recipes = repository.getRecipesByIngredient("Mehl");
	    
	    assertNotNull(recipes);
        assertThat(recipes.size(), is(2));
	}
	
	@Test
	public void shouldBeSaveNewRecipe() {
		final Recipe savedRecipe = repository.save(RecipeBuilder.buildRecipe());
		assertThat(savedRecipe, is(not(nullValue())));
		assertThat(savedRecipe.getId(), is(not(nullValue())));
		assertThat(savedRecipe.getIngredients().size(), is(3));
	}
	
	@Test
	public void shouldBeUpdateRecipe() {
		final Recipe updateRecipe = repository.findOne(Long.valueOf(10001L));
		updateRecipe.setPreamble("Neuer Preamble vom Test");
		
		final Recipe savedRecipe = repository.save(updateRecipe);
		
		assertThat(savedRecipe, is(not(nullValue())));
		assertThat(savedRecipe.getPreamble(), is("Neuer Preamble vom Test"));
		assertThat(savedRecipe.getIngredients().size(), is(4));
	}
	
	@Test
	public void shouldBeRemoveOneIngredient() {
	    final Recipe updateRecipe = repository.findOne(Long.valueOf(10003L));
	    assertThat(updateRecipe.getIngredients().size(), is(4));
	    final Ingredient ingredientForRemove = updateRecipe.getIngredients().iterator().next();
	    
	    updateRecipe.removeIngredient(ingredientForRemove);
	    
	    final Recipe savedRecipe = repository.save(updateRecipe);
	    assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getIngredients().size(), is(3));
	}
	
	@Test
	public void shouldBeDelete() {
		final Recipe toDelete = repository.findOne(Long.valueOf(10002L));
		repository.remove(toDelete);
		
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Pasta");
		assertNotNull(recipes);
		assertThat(recipes.size(), is(0));
	}
}
