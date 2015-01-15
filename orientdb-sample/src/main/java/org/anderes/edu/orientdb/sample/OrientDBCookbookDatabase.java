package org.anderes.edu.orientdb.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.Validate;

import com.orientechnologies.orient.core.command.OCommandOutputListener;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

public class OrientDBCookbookDatabase {

    private final String databaseUrl;
    private String user = "admin";
    private String password = "admin";

    @SuppressWarnings("resource")
    private OrientDBCookbookDatabase(final String databaseUrl) {
        this.databaseUrl = databaseUrl;
        final OObjectDatabaseTx db = new OObjectDatabaseTx(databaseUrl).open(user, password);
        db.getEntityManager().registerEntityClasses(getClassForRegistration());
        db.close();
    };
    
    
    public static OrientDBCookbookDatabase setup(final String databaseUrl) {
        return new OrientDBCookbookDatabase(databaseUrl);
    }

    public void backup(final File file) {
        final OObjectDatabaseTx db = getDatabaseTx();
        try {
            final OCommandOutputListener listener = new OCommandOutputListener() {
                @Override
                public void onMessage(String iText) {
                    System.out.print(iText);
                }
            };

            final OutputStream out = new FileOutputStream(file);
            db.backup(out, null, null, listener, 9, 2048);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private Collection<String> getClassForRegistration() {
        final Collection<String> register = new HashSet<String>();
        register.add(Recipe.class.getName());
        register.add(Ingredient.class.getName());
        register.add(Image.class.getName());
        return register;
    }

    public Recipe save(final Recipe recipe) {
        Validate.notNull(recipe, "Das Rezept darf nicht null sein.");
        if (recipe.getId() == null) {
            return saveNewRecipe(recipe);
        }
        return saveExistsRecipe(recipe);
    }

    private Recipe saveExistsRecipe(final Recipe recipe) {
        // TODO Auto-generated method stub
        return null;
    }

    private Recipe saveNewRecipe(final Recipe recipe) {
        final OObjectDatabaseTx db = getDatabaseTx();
        recipe.setId(UUID.randomUUID().toString());
        try {
            final Recipe storedRecipe = db.attachAndSave(recipe);
            return db.detachAll(storedRecipe, true);
        } finally {
            db.close();
        }
    }

    public List<Recipe> getRecipes() {
        final OObjectDatabaseTx db = getDatabaseTx();
        try {
            final List<Recipe> resultset = db.query(new OSQLSynchQuery<Recipe>("select * from Recipe").setFetchPlan("*:-1"));
            final List<Recipe> recipes = new ArrayList<Recipe>(resultset.size());
            for (Recipe r : resultset) {
                recipes.add((Recipe) db.detachAll(r, true));
            }
            return recipes;
        } finally {
            db.close();
        }
    }

    public <T> void deleteAll(final Class<T> clazz) {
        final OObjectDatabaseTx db = getDatabaseTx();
        try {
            for (T c : db.browseClass(clazz)) {
                db.delete(c);
            }
        } finally {
            db.close();
        }
    }

    private OObjectDatabaseTx getDatabaseTx() {
        return OObjectDatabasePool.global().acquire(databaseUrl, user, password);
    }
}
