package org.anderes.edu.appengine.cookbook;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;

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
        
        final Recipe findRecipe = repository.findOne(savedRecipe.getId());
        assertThat(findRecipe, is(notNullValue()));
        assertThat(findRecipe.getNoOfPerson(), is(2));
        assertThat(findRecipe.getTitle(), is("Basilikum-Pesto"));
        assertThat(findRecipe.getIngredients().size(), is(2));
    }
    
    @Test
    public void shouldBefindByTitle() {
        final Recipe newRecipe_1 = createRecipeForPesto();
        final Recipe newRecipe_2 = createRecipeForAsiatischeSpaghetti();
        repository.save(newRecipe_1);
        repository.save(newRecipe_2);
        
        Collection<Recipe> list = repository.findByTitle("Basilikum-Pesto");
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(1));
        
        list = repository.findByTitle("Basilikum");
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(1));
        
        list = repository.findByTitle("Pesto");
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(0));
    }

    @Test
    public void shouldBeDeleteRecipe() {
        final Recipe newRecipe = createRecipeForAsiatischeSpaghetti();
        final Recipe savedRecipe = repository.save(newRecipe);
        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(notNullValue()));
                
        exception.expect(NotFoundException.class);
        exception.expectMessage(startsWith("No entity was found"));
        
        repository.delete(savedRecipe);
        repository.findOne(savedRecipe.getId());
    }
    
    private Recipe createRecipeForPesto() {
        final Recipe recipe = new Recipe();
        recipe.setTitle("Basilikum-Pesto").setPreamble("hmm... fein!").setPreparation("Pasta machen; Spagetti natürlich ...").setNoOfPerson(2);
        recipe.addTag("pasta").addTag("fleischlos");
        recipe.setImage(new Image("/pesto.jpg", "Pesto mit Spagetti"));
        recipe.addIngredient(new Ingredient("1", "Knoblizehe", "Bio Knobli")).addIngredient(new Ingredient("nach belieben", "Basilikum", "frisch vom Garten"));
        return recipe;
    }
    
    private Recipe createRecipeForAsiatischeSpaghetti() {
        final Recipe recipe = new Recipe();
        recipe.setTitle("Asiatische Spaghetti").setPreparation("Mehl und etwas Curry in einer Schüssel mischen ...").setNoOfPerson(2);
        recipe.addTag("pasta").addTag("fleisch");
        recipe.setImage(new Image("/pesto.jpg", "Pesto mit Spagetti"));
        recipe.addIngredient(new Ingredient("1", "Knoblizehe", "Bio Knobli")).addIngredient(new Ingredient("3 EL", "dunkle Sojasouce", null));
        return recipe;
    }
    
}
