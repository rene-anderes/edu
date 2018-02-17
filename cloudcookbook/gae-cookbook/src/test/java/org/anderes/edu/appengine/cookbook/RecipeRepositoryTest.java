package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.CleanupStrategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.Strategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.UsingDataSet;
import org.anderes.edu.appengine.cookbook.dto.ImageDto;
import org.anderes.edu.appengine.cookbook.dto.IngredientDto;
import org.anderes.edu.appengine.cookbook.dto.RecipeDto;
import org.anderes.edu.appengine.cookbook.dto.RecipeShort;
import org.anderes.edu.appengine.cookbook.objectify.Image;
import org.anderes.edu.appengine.cookbook.objectify.Ingredient;
import org.anderes.edu.appengine.cookbook.objectify.Recipe;
import org.anderes.edu.appengine.cookbook.objectify.RecipeRepository;
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
        ObjectifyService.register(Ingredient.class);
        ObjectifyService.register(Image.class);
    }

    @Before
    public void setUp() {
        repository = new RecipeRepository();
    }
    
    @Test
    public void shoudlBeFindOne() {
    	
    	final RecipeDto findRecipe = repository.findOne("77acc29a-6809-4183-8945-e8b759e9b3f0");
    	
    	assertThat(findRecipe, is(notNullValue()));
    	assertThat(findRecipe.getTitle(), is("Asiatische Spaghetti"));
    	assertThat(findRecipe.getIngredients().size(), is(11));
    	assertThat(findRecipe.getImage(), is(nullValue()));
    }
    
    @Test
    public void shouldBeSaveNewRecipe() {
        RecipeDto savedRecipe = null;
        final RecipeDto newRecipe = createRecipeForPesto();
        savedRecipe = repository.save(newRecipe);
        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(notNullValue()));
        assertThat(repository.exists(savedRecipe), is(true));
        
        final RecipeDto findRecipe = repository.findOne(savedRecipe.getId());
        assertThat(findRecipe, is(notNullValue()));
        assertThat(findRecipe.getNoOfPeople(), is("2"));
        assertThat(findRecipe.getTitle(), is("Basilikum-Pesto"));
        assertThat(findRecipe.getIngredients().size(), is(2));
        assertThat(findRecipe.getImage(), is(notNullValue()));
    }

    @Test
    public void shouldBeDeleteRecipe() {
        exception.expect(NotFoundException.class);
        exception.expectMessage(startsWith("No entity was found"));
        
        final RecipeDto savedRecipe = repository.findOne("d60588da-7971-42f1-b514-85e1bfa02b1e");
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
        final List<RecipeDto> recipes = repository.findAll();
        
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.size(), is(44));
    }
    
    private RecipeDto createRecipeForPesto() {
        final RecipeDto recipe = new RecipeDto();
        recipe.setTitle("Basilikum-Pesto").setPreamble("hmm... fein!").setPreparation("Pasta machen; Spagetti natürlich ...").setNoOfPeople("2");
        recipe.addTag("pasta").addTag("fleischlos");
        recipe.setImage(new ImageDto("/pesto.jpg", "Pesto mit Spagetti"));
        recipe.addIngredient(new IngredientDto("1", "Knoblizehe", "Bio Knobli")).addIngredient(new IngredientDto("nach belieben", "Basilikum", "frisch vom Garten"));
        return recipe;
    }
    
}
