package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.googlecode.objectify.ObjectifyService;

public class RecipeRepositoryTest {

    @Rule
    public ObjectifyTestRule rule = new ObjectifyTestRule();

    static {
        ObjectifyService.register(Recipe.class);
    }

    @Test
    public void shouldBeSaveNewRecipe() {
        Recipe savedRecipe = null;
        RecipeRepository repository = new RecipeRepository();
        final Recipe newRecipe = createNeRecipe();
        savedRecipe = repository.save(newRecipe);
        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(notNullValue()));
    }

    private Recipe createNeRecipe() {
        final Recipe recipe = new Recipe();
        recipe.setTitle("Test-Rezept").setPreamble("Test Preamble").setPreparation("Testzubereitung").setNoOfPerson(2);
        return recipe;
    }
}
