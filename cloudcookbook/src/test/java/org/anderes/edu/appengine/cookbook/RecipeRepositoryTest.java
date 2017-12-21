package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.CleanupStrategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.Strategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.UsingDataSet;
import org.anderes.edu.appengine.cookbook.dto.Image;
import org.anderes.edu.appengine.cookbook.dto.Ingredient;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

@UsingDataSet(value = { "/Cookbook_Complete.json" })
@CleanupStrategy(value = Strategy.BEFORE)
public class RecipeRepositoryTest {

    @Rule
    public ObjectifyTestRule rule = new ObjectifyTestRule();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    private RecipeRepository repository;

    static {
        ObjectifyService.register(Recipe.class);
    }

    @Before
    public void setUp() {
        repository = new RecipeRepository();
    }
    
    @Test
    public void shouldBeSaveNewRecipe() {
        Recipe savedRecipe = null;
        final Recipe newRecipe = createRecipeForPesto();
        savedRecipe = repository.save(newRecipe);
        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(notNullValue()));
        assertThat(repository.exists(savedRecipe), is(true));
        
        final Recipe findRecipe = repository.findOne(savedRecipe.getId());
        assertThat(findRecipe, is(notNullValue()));
        assertThat(findRecipe.getNoOfPeople(), is("2"));
        assertThat(findRecipe.getTitle(), is("Basilikum-Pesto"));
        assertThat(findRecipe.getIngredients().size(), is(2));
    }

    @Test
    public void shouldBeDeleteRecipe() {
        exception.expect(NotFoundException.class);
        exception.expectMessage(startsWith("No entity was found"));
        
        final Recipe savedRecipe = repository.findOne("d60588da-7971-42f1-b514-85e1bfa02b1e");
        repository.delete("d60588da-7971-42f1-b514-85e1bfa02b1e");
        assertThat(repository.exists(savedRecipe), is(false));
        assertThat(repository.exist("d60588da-7971-42f1-b514-85e1bfa02b1e"), is(false));
        repository.findOne("d60588da-7971-42f1-b514-85e1bfa02b1e");
    }
    
    @Test
    public void shouldBeFindAllTags() {
        final Map<String, Integer> tagMap = repository.findAllTags();
        assertThat(tagMap, is(notNullValue()));
        assertThat(tagMap.size(), is(10));
    }
    
    @Test
    public void shouldBeFindAllRecipeShort() {
        final List<RecipeShort> recipeShorts = repository.getRecipeCollection();
        assertThat(recipeShorts, is(notNullValue()));
        assertThat(recipeShorts.size(), is(44));
    }
    
    @Test
    @UsingDataSet(value = { "/Cookbook_Complete.json" })
    @CleanupStrategy(value = Strategy.BEFORE)
    public void shouldBeFindAll() {
        final List<Recipe> recipes = repository.findAll();
        
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.size(), is(44));
    }
    
    private Recipe createRecipeForPesto() {
        final Recipe recipe = new Recipe();
        recipe.setTitle("Basilikum-Pesto").setPreamble("hmm... fein!").setPreparation("Pasta machen; Spagetti natürlich ...").setNoOfPeople("2");
        recipe.addTag("pasta").addTag("fleischlos");
        recipe.setImage(new Image("/pesto.jpg", "Pesto mit Spagetti"));
        recipe.addIngredient(new Ingredient("1", "Knoblizehe", "Bio Knobli")).addIngredient(new Ingredient("nach belieben", "Basilikum", "frisch vom Garten"));
        return recipe;
    }
    
}
