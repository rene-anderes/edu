package org.anderes.edu.orientdb.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

public class OrientDBCookbookDatabaseTest {

    private OrientDBCookbookDatabase database;
    
    private final String dbFile = "d:/temp/recipeDatabase";
    
    @Before
    public void setup() {
        final File dbPath = new File(dbFile);
        if (!dbPath.exists()) {
            new OObjectDatabaseTx("plocal:" + dbFile).create();
        }
        database = OrientDBCookbookDatabase.setup("plocal:" + dbFile);
    }

    @After
    public void tearDown() throws Exception {
        database.deleteAll(Recipe.class);
    }

    @Test
    public void shouldBeEmtpyList() {
        final List<Recipe> recipes = database.getRecipes();
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.size(), is(0));
    }
    
    @Test
    public void shouldBeSave() {
        // given
        Recipe recipe = new Recipe();
        recipe = RecipeBuilder.buildRecipe(recipe);
        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient = RecipeBuilder.buildIngredients(ingredient);
            recipe.getIngredients().add(ingredient);
        }
        
        // when
        final Recipe storedRecipe = database.save(recipe);
        
        // then
        assertThat(storedRecipe, is(notNullValue()));
        assertThat(storedRecipe.getIngredients().size(), is(5));
        
        
        final List<Recipe> recipes = database.getRecipes();
        assertThat(recipes, is(notNullValue()));
        assertThat(recipes.size(), is(1));
        
    }

}
