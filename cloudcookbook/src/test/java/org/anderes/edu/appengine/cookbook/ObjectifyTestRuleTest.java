package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue.ValueType;

import org.anderes.edu.appengine.cookbook.ObjectifyTestRule.UsingDataSet;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.junit.Rule;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyService;

public class ObjectifyTestRuleTest {

    @Rule
    public ObjectifyTestRule rule = new ObjectifyTestRule();

    static {
        ObjectifyService.register(Recipe.class);
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
        assertThat(recipe.getImage(), is(notNullValue()));
        assertThat(recipe.getIngredients(), is(notNullValue()));
        assertThat(recipe.getIngredients().size(), is(8));
    }
    
    @Test
    @UsingDataSet(value = { "/recipe2.json" })
    public void shouldBeUsingdataSet() {
        assertThat(rule.findOne("c0e5582e-252f-4e94-8a49-e12b4b047afb"), is(notNullValue()));
    }
}
