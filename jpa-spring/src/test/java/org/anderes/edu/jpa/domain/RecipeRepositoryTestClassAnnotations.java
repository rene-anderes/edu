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

import org.anderes.edu.dbunitburner.DbUnitRule;
import org.anderes.edu.dbunitburner.DbUnitRule.CleanupUsingScript;
import org.anderes.edu.dbunitburner.DbUnitRule.ShouldMatchDataSet;
import org.anderes.edu.dbunitburner.DbUnitRule.UsingDataSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
                "classpath:application-context.xml",
                "classpath:unittest-application-context.xml"
})
@CleanupUsingScript(value = { "/sql/DeleteTableContentScript.sql" })
@UsingDataSet(value = { "/prepare.xls" })
public class RecipeRepositoryTestClassAnnotations {

    @Inject
    private RecipeRepository repository;
  
    @Inject @Rule 
    public DbUnitRule dbUnitRule;
   
    @Before
    public void setup() {
    }
    
    @Test
    @ShouldMatchDataSet(
            value = { "/prepare.xls" },
            orderBy = { "RECIPE.UUID", "INGREDIENT.ID" })
    public void shouldBeFindAll() {
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
        final Recipe recipe = repository.findOne("c0e5582e-252f-4e94-8a49-e12b4b047afb");
        assertNotNull(recipe);
        assertThat(recipe.getTitle(), is("Arabische Spaghetti"));
    }
    
    @Test
    @ShouldMatchDataSet(
            value = { "/prepare.xls" },
            orderBy = { "RECIPE.UUID", "INGREDIENT.ID" })
    public void getRecipesByTitle() {
        final Collection<Recipe> recipes = repository.findByTitleLike("%Spaghetti%");

        assertNotNull(recipes);
        assertThat(recipes.size(), is(1));
        final Recipe recipe = recipes.iterator().next();
        assertThat(recipe.getTitle(), is("Arabische Spaghetti"));
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
    @ShouldMatchDataSet(value = { "/expected-afterUpdate.xls" },
            excludeColumns = { "INGREDIENT.ID" },
            orderBy = { "RECIPE.UUID", "INGREDIENT.ANNOTATION" }
    )
    public void shouldBeUpdateRecipe() {
        final Recipe updateRecipe = repository.findOne("c0e5582e-252f-4e94-8a49-e12b4b047afb");
        updateRecipe.setPreamble("Neuer Preamble vom Test");
        updateRecipe.addIngredient(new Ingredient("1", "Tomate", "vollreif"));
        final Recipe savedRecipe = repository.save(updateRecipe);
        
        assertThat(savedRecipe, is(not(nullValue())));
        assertThat(savedRecipe.getPreamble(), is("Neuer Preamble vom Test"));
        assertThat(savedRecipe.getIngredients().size(), is(4));
        
        final Recipe findRecipe = repository.findOne(savedRecipe.getUuid());
        assertThat(findRecipe, is(not(nullValue())));
        assertThat(findRecipe.getPreamble(), is("Neuer Preamble vom Test"));
        assertNotSame(updateRecipe, findRecipe);
        assertThat(updateRecipe, is(findRecipe));
    }
    
    @Test
    @ShouldMatchDataSet(value = { "/expected-afterDeleteOne.xls" },
            excludeColumns = { "RECIPE.ADDINGDATE" },
            orderBy = { "RECIPE.UUID", "INGREDIENT.ID" })
    public void shouldBeDelete() {
        final Recipe toDelete = repository.findOne("c0e5582e-252f-4e94-8a49-e12b4b047afb");
        assertThat("Das Rezept mit der ID 'c0e5582e-252f-4e94-8a49-e12b4b047afb' existiert nicht in der Datenbank", toDelete, is(not(nullValue())));
        repository.delete(toDelete);
        
        final Collection<Recipe> recipes = repository.findByTitleLike("%Spaghetti%");
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
