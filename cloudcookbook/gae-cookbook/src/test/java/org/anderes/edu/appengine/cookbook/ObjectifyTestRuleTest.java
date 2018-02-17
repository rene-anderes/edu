package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue.ValueType;

import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.CleanupStrategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.Strategy;
import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.UsingDataSet;
import org.anderes.edu.appengine.cookbook.objectify.Image;
import org.anderes.edu.appengine.cookbook.objectify.Ingredient;
import org.anderes.edu.appengine.cookbook.objectify.Recipe;
import org.junit.Rule;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyService;

public class ObjectifyTestRuleTest {

    @Rule
    public ObjectifyTestRule rule = new ObjectifyTestRule();

    static {
        ObjectifyService.register(Recipe.class);
        ObjectifyService.register(Ingredient.class);
        ObjectifyService.register(Image.class);
    }
    
    @Test
    public void shouldBeMap() throws Exception {
        final Path jsonFile = Paths.get(getClass().getResource("/recipe2.json").toURI());
        final JsonStructure recipeObject = rule.readJsonFile(jsonFile);
        assertThat(recipeObject.getValueType(), is(ValueType.OBJECT));
        
        final Recipe recipe = rule.mapRecipe((JsonObject)recipeObject);
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.getTitle(), is("Arabische Spaghetti"));
        assertThat(recipe.getTags().size(), is(2));
        assertThat(recipe.getAddingDate().getTime(), is(1250959818424L));
        assertThat(recipe.getEditingDate().getTime(), is(1421863647087L));
        final Optional<Image> image = rule.mapImage((JsonObject)recipeObject);
        assertThat(image, is(notNullValue()));
        assertThat(image.isPresent(), is(true));
        final Set<Ingredient> ingredients = rule.mapIngredients((JsonObject)recipeObject);
        assertThat(ingredients, is(notNullValue()));
        assertThat(ingredients.size(), is(8));
    }
    
    @Test
    @CleanupStrategy(value = Strategy.BEFORE)
    @UsingDataSet(value = { "/recipe2.json" })
    public void shouldBeUsingdataSet() {
        Recipe findOne = rule.findOne("c0e5582e-252f-4e94-8a49-e12b4b047afb");
		assertThat(findOne, is(notNullValue()));
    }
    
    @Test
    @UsingDataSet(value = { "/Cookbook_Complete.json" })
    @CleanupStrategy(value = Strategy.BEFORE)
    public void shouldBeUsingdataSetAndCleanup() {
        assertThat(rule.findAll().size(), is(44));
    }
 
}
