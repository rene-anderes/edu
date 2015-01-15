package org.anderes.edu.orientdb.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

public class OrientDBTest {
    
    private final String dbFile = "d:/temp/recipeDatabase";
 
    @Before
    public void setup() {
        OObjectDatabaseTx db;
        final File dbPath = new File(dbFile);
        if (!dbPath.exists()) {
            db = new OObjectDatabaseTx("plocal:" + dbFile).create();
        }
        db = new OObjectDatabaseTx("plocal:" + dbFile).open("admin", "admin");
        final Collection<String> register = new HashSet<String>();
        register.add(Recipe.class.getName());
        register.add(Ingredient.class.getName());
        register.add(Image.class.getName());
        db.getEntityManager().registerEntityClasses(register);
        db.close();
    }
    
    @After
    public void tearDown() {
        OObjectDatabaseTx db = OObjectDatabasePool.global().acquire("plocal:" + dbFile, "admin", "admin");
        for (Recipe recipe : db.browseClass(Recipe.class)) {
            System.out.format("Delete Recipe: %s%n", recipe.getTitle());
            db.delete(recipe);
        }
        for (Ingredient i : db.browseClass(Ingredient.class)) {
            System.out.format("Sollte diese Ausgabe erfolgen ist eine Zutat nicht gelöscht worden: %s %s %s%n", i.getQuantity(), i.getDescription(), i.getAnnotation());
        }
        db.close(); 
    }
    
    @Test
    public void shouldBePersistOneObject() {
        OObjectDatabaseTx db= OObjectDatabasePool.global().acquire("plocal:" + dbFile, "admin", "admin");
        
        Recipe recipe = db.newInstance(Recipe.class);
        recipe = RecipeBuilder.buildRecipe(recipe);
        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = db.newInstance(Ingredient.class);
            ingredient = RecipeBuilder.buildIngredients(ingredient);
            recipe.getIngredients().add(ingredient);
        }
        try {
          Recipe storedRecipe = db.save(recipe);
          assertThat(storedRecipe, is(notNullValue()));
        } finally {
          db.close();
        }
        checkDatabase();
    }
    
    private void checkDatabase() {
        OObjectDatabaseTx db= OObjectDatabasePool.global().acquire("plocal:" + dbFile, "admin", "admin");
        
        List<Recipe> resultset = db.query(new OSQLSynchQuery<Recipe>("select * from Recipe").setFetchPlan("*:-1"));
        for(Recipe recipe : resultset) {
            System.out.format("toString(): %s%n", recipe.toString());
            System.out.format("hashCode(): %s%n", recipe.hashCode());
            System.out.format("Rezepttitel: %s%n", recipe.getTitle());
            final Set<Ingredient> ingredients = recipe.getIngredients();
            assertThat(ingredients.size(), (is(5)));
            for(Ingredient i : ingredients) {
                System.out.format("Zutat: %s %s %s%n", i.getQuantity(), i.getDescription(), i.getAnnotation());
            }
            final Recipe clone = db.detachAll(recipe, true);
            System.out.format("Rezepttitel: %s%n", clone.getTitle());
            for(Ingredient i : clone.getIngredients()) {
                System.out.format("Zutat: %s %s %s%n", i.getQuantity(), i.getDescription(), i.getAnnotation());
            }
        }
        db.close();
    }
}
