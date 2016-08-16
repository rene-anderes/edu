package org.anderes.edu.jpa.cookbook;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.Validate;

/**
 * In diesem Beispiel eines Repository wird der Entity-Manager so lange
 * gehalten wie das Repository-Objekt selber existiert (Klassenmember).
 * 
 * @author René Anderes
 *
 */
public class RecipeRepository {

	private EntityManager entityManager; 
	
	private RecipeRepository() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eclipseLinkPU");
        entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static RecipeRepository build() {
		return new RecipeRepository();
	}

	public Recipe findOne(final Long databaseidentity) {
		Validate.notNull(databaseidentity, "Der Parameter 'databaseidentity' darf nicht null sein.");
		
		return entityManager.find(Recipe.class, databaseidentity);
	}
		
	/**
	 * Gibt alle Rezepte zurück die im Titel den übergeben Wert enthalten.
	 * 
	 * @param title
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitle(final String title) {
	    Validate.notNull(title, "Der Parameter 'title' darf nicht null sein.");
	    
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%");
		
        return query.getResultList();
	}

	/**
	 * Gibt alle Rezepte zurück die eine Zutat besitzen mit der Beschreibung
	 * die als Parameter dieser Methode mitgegeben werden kann.
	 * 
	 * @param ingredientDescription
	 * @return Liste von rezepten
	 */
    public Collection<Recipe> getRecipesByIngredient(String ingredientDescription) {
        Validate.notNull(ingredientDescription, "Der Parameter 'ingredientDescription' darf nicht null sein.");
        
        final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYINGREDIENT, Recipe.class);
        query.setParameter("description", "%" + ingredientDescription + "%");
        return query.getResultList();
    }
	
	/**
	 * Nur für Testzwecke
	 * @return Persistence-Util
	 */
	/*package*/ PersistenceUnitUtil getPersistenceUnitUtil() {
		return entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
	}

	public Recipe save(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter 'entity' darf nicht null sein.");
		
		Recipe savedRecipe = null;
		entityManager.getTransaction().begin();
		if (entity.getId() == null || entity.getId() == 0) {
		    entityManager.persist(entity);
		    savedRecipe = entity;
		} else {
		    savedRecipe = entityManager.merge(entity);
		}
		entityManager.getTransaction().commit();
		return savedRecipe;
	}
		
	/**
	 * Bei dieser Methode wird erwartet, dass die Entity bereits den Status 'managed'
	 * hat. Dies kann natürlich auch anderes gelöst werden. Siehe alternatives Repository
	 */
	public void remove(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter 'entity' darf nicht null sein.");
		
		if (!entityManager.contains(entity)) {
			throw new IllegalArgumentException("Die Entität besitzt nicht den Status managed");
		}
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
