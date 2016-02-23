package org.anderes.edu.appengine.cookbook;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.anderes.edu.appengine.cookbook.dto.Image;
import org.anderes.edu.appengine.cookbook.dto.Ingredient;
import org.anderes.edu.appengine.cookbook.dto.Recipe;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyTestRule implements TestRule {

    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface UsingDataSet {
        String[] value();
    }
    
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface CleanupStrategy {
        Strategy value();
    }
    
    public enum Strategy { BEFORE, AFTER, NONE }
    
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                helper.setUp();
                try (Closeable closeable = ObjectifyService.begin()) {
                    before(description);
                    base.evaluate();
                    after(description);
                } finally {
                    helper.tearDown();
                }
            }
        };
    }

    private void before(Description description) throws URISyntaxException, FileNotFoundException {
        final UsingDataSet usingDataSet = extractUsingDataSet(description);
        final CleanupStrategy cleanupStrategy = extractCleanupStrategy(description);
        if (cleanupStrategy != null && cleanupStrategy.value() == Strategy.BEFORE) {
            cleanupDatabase();
        }
        if (usingDataSet != null) {
            handleDataSet(usingDataSet);
        }
    }

    void handleDataSet(final UsingDataSet usingDataSet) throws URISyntaxException, FileNotFoundException {
        final String[] dataSetFiles = usingDataSet.value();
        for (String jsonFileString : dataSetFiles) {
            final Path jsonFile = Paths.get(getClass().getResource(jsonFileString).toURI());
            persistJsonFileData(jsonFile);
        }
    }

    private void cleanupDatabase() {
        final List<Recipe> recipes = findAll();
        for (Recipe recipe : recipes) {
            ofy().delete().entity(recipe).now();
        }
    }

    private void persistJsonFileData(final Path jsonFile) throws FileNotFoundException {
        JsonStructure jsonValue = readJsonFile(jsonFile);
        if (jsonValue.getValueType() == ValueType.OBJECT) {
            persistRecipe((JsonObject) jsonValue);
        } else if (jsonValue.getValueType() == ValueType.ARRAY) {
            persistRecipes((JsonArray) jsonValue);
        }
    }

    /* package*/ JsonStructure readJsonFile(final Path jsonFile) throws FileNotFoundException {
        final JsonReader reader = Json.createReader(new FileReader(jsonFile.toFile()));
        JsonStructure jsonValue = reader.read();
        reader.close();
        return jsonValue;
    }

    private void persistRecipes(final JsonArray jsonArray) {
        for (JsonValue value : jsonArray) {
            persistRecipe((JsonObject)value);
        }
        
    }

    private void persistRecipe(final JsonObject recipeObject) {
        final Recipe recipe = mapRecipe(recipeObject);
        ofy().save().entity(recipe).now();
    }

    /* package */ Recipe mapRecipe(final JsonObject recipeObject) {
        final Recipe recipe = new Recipe();
        if (recipeObject.isNull("id")) {
            throw new IllegalArgumentException("Die id des rezepts darf nicht null sein");
        }
        recipe.setId(recipeObject.getString("id"));
        recipe.setTitle(recipeObject.getString("title"));
        recipe.setPreamble(recipeObject.getString("preamble"));
        recipe.setNoOfPeople(recipeObject.getString("noOfPeople"));
        recipe.setPreparation(recipeObject.getString("preparation"));
        recipe.setRating(recipeObject.getInt("rating", 0));
        if (!recipeObject.isNull("addingDate")) {
            final long addingDate = recipeObject.getJsonNumber("addingDate").longValue();
            recipe.setAddingDate(new Date(addingDate));
        }
        if (!recipeObject.isNull("editingDate")) {
            final long editingDate = recipeObject.getJsonNumber("editingDate").longValue(); 
            recipe.setEditingDate(new Date(editingDate));
        }
        if (!recipeObject.isNull("tags")) {
            final JsonArray tags = recipeObject.getJsonArray("tags");
            for (JsonValue tag : tags) {
                recipe.addTag(((JsonString)tag).getString());
            }
        }
        if (!recipeObject.isNull("image")) {
            final JsonObject imageObject = ((JsonObject)recipeObject.get("image"));
            recipe.setImage(new Image());
            recipe.getImage().setDescription(imageObject.getString("description"));
            recipe.getImage().setUrl(imageObject.getString("url"));
        }
        final JsonArray ingredientsArray = recipeObject.getJsonArray("ingredients");
        for (JsonValue ingredient : ingredientsArray) {
            final String description = ((JsonObject) ingredient).getString("description");
            final String portion = ((JsonObject) ingredient).getString("portion");
            final String comment = ((JsonObject) ingredient).getString("comment");
            recipe.addIngredient(new Ingredient(portion, description, comment));
        }
        return recipe;
    }
    
    private void after(final Description description) {
        final CleanupStrategy cleanupStrategy = extractCleanupStrategy(description);
        if (cleanupStrategy != null && cleanupStrategy.value() == Strategy.AFTER) {
            cleanupDatabase();
        }
    }
    
    /* package */ List<Recipe> findAll() {
        return ofy().load().type(Recipe.class).list();
    }
    
    /* package */ Recipe findOne(final String id) {
        return ofy().load().type(Recipe.class).id(id).safe();
    }
    
    private UsingDataSet extractUsingDataSet(final Description description) {
        UsingDataSet usingDataSet = description.getAnnotation(UsingDataSet.class);
        if (usingDataSet == null) {
            usingDataSet = description.getTestClass().getAnnotation(UsingDataSet.class);
        }
        return usingDataSet;
    }
    
    private CleanupStrategy extractCleanupStrategy(final Description description) {
        CleanupStrategy cleanupStrategy = description.getAnnotation(CleanupStrategy.class);
        if (cleanupStrategy == null) {
            cleanupStrategy = description.getTestClass().getAnnotation(CleanupStrategy.class);
        }
        return cleanupStrategy;
    }
}
