package org.anderes.edu.jpa.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceUnitUtil;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <strong>Achtung:</strong> mittels VM Argument<br> 
 * <code>-javaagent: .../org.eclipse.persistence.jpa-2.5.2.jar</code><br>
 * starten
 * 
 * @author René Anderes
 *
 */
public class RecipeRepositoryTest {
	
	private RecipeRepository repository;
	private static final String WEAVING = "Bitte Weaving aktivieren, VM-Argument: -javaagent:c:\\Users\\na247\\.m2\\repository\\org\\eclipse\\persistence\\org.eclipse.persistence.jpa\\2.5.2\\org.eclipse.persistence.jpa-2.5.2.jar";
	
	@BeforeClass
	public static void onetime() {
	    final List<String> arguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
	    assertTrue(WEAVING, arguments.stream().anyMatch(argument -> argument.startsWith("-javaagent")));
	}
	
	@Before
	public void setup() {
		repository = RecipeRepository.build();
	}
	
	/**
	 * Fetch-Strategie "old style":<br>
	 * <code>
	 * setHint(QueryHints.BATCH, "r.ingredients")<br>
	 * setHint(QueryHints.LOAD_GROUP_ATTRIBUTE, "ingredients")
	 * </code> 
	 */
	@Test
	public void getDetachedRecipe() {
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
		
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		final Recipe recipe = recipes.iterator().next();

		// Detached durch die Serialisierung 
		final Recipe clone = SerializationUtils.clone(recipe);
		
		assertThat(clone.getTitle(), is("Dies und Das"));
		assertThat(clone.getIngredients().size(), is(4));
	}
	
	/**
	 * Fetch-Strategie JPA 2.1: Entity-Graph - fetchgraph
	 */
	@Test
	public void ingredientsShouldBeloadedByFetchgraph() {
		final Collection<Recipe> recipes = repository.getRecipesByTitleFetchgraph("Dies");
		
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		final Recipe recipe = recipes.iterator().next();

		final PersistenceUnitUtil util = repository.getPersistenceUnitUtil();
		assertTrue("Ups, der Rezepttitel ist nicht geladen.", util.isLoaded(recipe, Recipe_.title.getName()));
		assertTrue("Ups, die Zutaten wurden nicht geladen.", util.isLoaded(recipe, Recipe_.ingredients.getName()));
		assertFalse("Der Preample darf nicht geladen sein.", util.isLoaded(recipe, Recipe_.preample.getName()));
		   
		// Detached durch die Serialisierung 
		final Recipe clone = SerializationUtils.clone(recipe);
		
		assertThat(clone.getTitle(), is("Dies und Das"));
		assertThat(clone.getIngredients().size(), is(4));
	}
	
	@Test
	@Ignore("Load Graph funktioniert nicht wie erwartet")
	public void findOneIngredientsShouldBeLoaded() {
		final Recipe recipe = repository.findOne(10001l);
		
		assertNotNull(recipe);
		final PersistenceUnitUtil util = repository.getPersistenceUnitUtil();
		assertTrue("Ups, der Rezepttitel ist nicht geladen.", util.isLoaded(recipe, Recipe_.title.getName()));
        assertTrue("Ups, die Zutaten wurden nicht geladen.", util.isLoaded(recipe, Recipe_.ingredients.getName()));
		
		// Detached durch die Serialisierung 
		final Recipe clone = SerializationUtils.clone(recipe);
		
		assertThat(clone.getTitle(), is("Dies und Das"));
		assertThat(clone.getIngredients().size(), is(4));
	}

	/**
	 * Fetch-Strategie JPA 2.1: Entity-Graph - loadgraph
	 */
	@Test
	@Ignore("Load Graph funktioniert nicht wie erwartet")
	public void ingredientsShouldBeloadedByLoadgraph() {
		final Collection<Recipe> recipes = repository.getRecipesByTitleLoadgraph("Dies");
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		final Recipe recipe = recipes.iterator().next();

        final PersistenceUnitUtil util = repository.getPersistenceUnitUtil();
        assertTrue("Ups, der Rezepttitel ist nicht geladen.", util.isLoaded(recipe, Recipe_.title.getName()));
        assertTrue("Ups, die Zutaten wurden nicht geladen.", util.isLoaded(recipe, Recipe_.ingredients.getName()));
        
		// Detached durch die Serialisierung 
		final Recipe clone = SerializationUtils.clone(recipe);
		
		assertThat(clone.getTitle(), is("Dies und Das"));
		assertThat(clone.getIngredients().size(), is(4));
	}
	
	@Test
	public void getRecipesByTitle() {
		final Collection<Recipe> recipes = repository.getRecipesByTitle("Dies");
		
		assertNotNull(recipes);
		assertThat(recipes.size(), is(1));
		final Recipe recipe = recipes.iterator().next();
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
}
