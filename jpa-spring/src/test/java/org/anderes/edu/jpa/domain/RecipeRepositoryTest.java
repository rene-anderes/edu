package org.anderes.edu.jpa.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.List;

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
        final Recipe recipe = repository.findOne("FF00-AA");
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
        // given
        final Recipe newRecipe = RecipeBuilder.buildRecipe();
        
        // when
        final Recipe savedRecipe = repository.save(newRecipe);
        
        // then
        assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getUuid(), is(not(nullValue())));
        
        final Recipe findRecipe = repository.findOne(savedRecipe.getUuid());
        assertNotSame(newRecipe, findRecipe);
        assertThat(newRecipe, is(findRecipe));
    }
    
    @Test
    public void shouldBeUpdateRecipe() {
        final Recipe updateRecipe = repository.findOne("FF00-AA");
        updateRecipe.setPreample("Neuer Preample vom Test");
        final Recipe savedRecipe = repository.save(updateRecipe);
        
        assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getPreample(), is("Neuer Preample vom Test"));
        assertThat(savedRecipe.getIngredients().size(), is(4));
        
        final Recipe findRecipe = repository.findOne(savedRecipe.getUuid());
        assertThat(findRecipe, is(not(nullValue())));
        assertThat(findRecipe.getPreample(), is("Neuer Preample vom Test"));
        assertNotSame(updateRecipe, findRecipe);
        assertThat(updateRecipe, is(findRecipe));
    }
    
    @Test
    public void shouldBeDelete() {
        final Recipe toDelete = repository.findOne("FF00-BB");
        repository.delete(toDelete);
        
        final Collection<Recipe> recipes = repository.findByTitleLike("%Pasta%");
        assertNotNull(recipes);
        assertThat(recipes.size(), is(0));
    }
    
    @Test
    public void shouldBeFindAllTag() {
        final List<String> tags = repository.findAllTag();
        assertThat(tags, is(notNullValue()));
        assertThat(tags.size(), is(4));
    }
}
