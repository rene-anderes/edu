package org.anderes.edu.jpa.cookbook.solution1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecipeRepositoryEntityManagerIT {
	
	private RecipeRepositoryEntityManager repository;
	
	@Before
	public void setup() {
		repository = RecipeRepositoryEntityManager.build();
	}

	@Test
	public void shouldBeOneRecipe() {
		final Recipe recipe = repository.findOne(10001l);

		assertNotNull(recipe);
		assertThat(recipe.getTitle(), is("Dies und Das"));
		assertThat(repository.getPersistenceUnitUtil().isLoaded(recipe, Recipe_.ingredients.getName()), is(false));
		assertThat(recipe.getIngredients().size(), is(4)); //LAZY Loading obwohl die Entität detached ist (nur EclipseLink)
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
	    final Collection<Recipe> recipes = repository.getRecipesByIngredient("Olivenöl");
	    
	    assertNotNull(recipes);
        assertThat(recipes.size(), is(3));
	}
	
	@Test
    public void shouldBeFindRecipesShortByIngredient() {
        final Collection<RecipeShort> recipes = repository.getRecipesShortByIngredient("Olivenöl");
        
        assertNotNull(recipes);
        assertThat(recipes.size(), is(3));
        recipes.stream().forEach(r -> {
            assertThat(r.getTitle(), is(notNullValue()));
            assertThat(r.getId(), is(not(nullValue())));
        });
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
		updateRecipe.setPreamble("Neuer Preamble, alles vom Feinsten");
		
		final Recipe savedRecipe = repository.save(updateRecipe);
		
		assertThat(savedRecipe, is(not(nullValue())));
		assertThat(savedRecipe.getPreamble(), is("Neuer Preamble, alles vom Feinsten"));
		assertThat(savedRecipe.getIngredients().size(), is(4));
	}
	
	@Test
	public void shouldBeRemoveOneIngredient() {
	    final Recipe updateRecipe = repository.findOne(Long.valueOf(10004L));
	    assertThat(updateRecipe.getIngredients().size(), is(3));
	    final Ingredient ingredientForRemove = updateRecipe.getIngredients().iterator().next();
	    
	    updateRecipe.removeIngredient(ingredientForRemove);
	    
	    final Recipe savedRecipe = repository.save(updateRecipe);
	    assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getIngredients().size(), is(2));
	}
	
	@Test
	public void shouldBeDelete() {
		final Recipe toDelete = repository.findOne(Long.valueOf(10005L));
		repository.remove(toDelete);
		
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Fast fertig");
		assertNotNull(recipes);
		assertThat(recipes.size(), is(0));
	}
	
	@Test
	public void shouldBeView() {
	    final List<TagCounterView> tags = repository.getTags();
	    
	    assertThat(tags, is(not(nullValue())));
	    assertThat(tags.size() > 1, is(true));
	    
	}
}
