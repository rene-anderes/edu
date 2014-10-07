package org.anderes.edu.jpa.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collection;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class RecipeRepositoryTest {

    @Inject
    private RecipeRepository repository;
    
    @Test
    public void testApp() {
        Iterable<Recipe> recipes = repository.findAll();
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.iterator().hasNext(), is(true));
        int counter = 0;
        for (Recipe recipe : recipes) {
            assertThat(recipe.getTitle(), is(notNullValue()));
            counter++;
        }
        assertThat(counter, is(2));
    }
    

    @Test
    public void shouldBeOneRecipe() {
        final Recipe recipe = repository.findOne(10001l);
        
        assertNotNull(recipe);
        assertThat(recipe.getTitle(), is("Dies und Das"));
        assertThat(recipe.getIngredients().size(), is(4));
        System.out.println(recipe);
    }
    
    @Test
    public void getRecipesByTitle() {
        final Collection<Recipe> recipes = repository.findByTitleLike("%Dies%");
        
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
        assertThat(savedRecipe.getIngredients().size(), is(3));
    }
    
    @Test
    public void shouldBeUpdateRecipe() {
        final Recipe updateRecipe = repository.findOne(Long.valueOf(10001L));
        updateRecipe.setPreample("Neuer Preample vom Test");
        final Recipe savedRecipe = repository.save(updateRecipe);
        
        assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getPreample(), is("Neuer Preample vom Test"));
        assertThat(savedRecipe.getIngredients().size(), is(4));
    }
    
    @Test
    public void shouldBeDelete() {
        final Recipe toDelete = repository.findOne(Long.valueOf(10002L));
        repository.delete(toDelete);
        
        final Collection<Recipe> recipes = repository.findByTitleLike("%Pasta%");
        assertNotNull(recipes);
        assertThat(recipes.size(), is(0));
    }
}
