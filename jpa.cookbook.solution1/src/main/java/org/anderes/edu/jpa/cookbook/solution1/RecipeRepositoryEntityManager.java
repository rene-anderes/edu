package org.anderes.edu.jpa.cookbook.solution1;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.Validate;

public class RecipeRepositoryEntityManager {

	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eclipseLinkPU");
	
	private RecipeRepositoryEntityManager() {
        super();
	}
	
	public static RecipeRepositoryEntityManager build() {
		return new RecipeRepositoryEntityManager();
	}

	public Recipe findOne(final Long databaseidentity) {
		Validate.notNull(databaseidentity, "Der Parameter darf nicht null sein.");
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final Recipe recipe =  entityManager.find(Recipe.class, databaseidentity);
		entityManager.close();
		return recipe;
	}
		
	/**
	 * Gibt alle Rezepte zurück die im Titel den übergeben Wert enthalten.
	 * 
	 * @param title
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitle(final String title) {
		
	    final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%");
		
        final List<Recipe> recipes =  query.getResultList();
        entityManager.close();
        return recipes;
	}

	/**
	 * Gibt alle Rezepte zurück die eine Zutat besitzen mit der Beschreibung
	 * die als Parameter dieser Methode mitgegeben werden kann.
	 * 
	 * @param ingredientDescription
	 * @return Liste von rezepten
	 */
    public Collection<Recipe> getRecipesByIngredient(String ingredientDescription) {
        
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYINGREDIENT, Recipe.class);
        query.setParameter("description", "%" + ingredientDescription + "%");
        
        final List<Recipe> recipes =  query.getResultList();
        entityManager.close();
        return recipes;
    }
	
	/**
	 * Nur für Testzwecke
	 * @return Persistence-Util
	 */
	/*package*/ PersistenceUnitUtil getPersistenceUnitUtil() {
	    final EntityManager entityManager = entityManagerFactory.createEntityManager();
		return entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
	}

	public Recipe save(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter darf nicht null sein.");
		
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		final Recipe savedRecipe = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
		return savedRecipe;
	}
		
	public void remove(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter darf nicht null sein.");
		
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		if (!entityManager.contains(entity)) {
		    entityManager.close();
			throw new IllegalArgumentException("Die Entität besitzt nicht den Status managed");
		}
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
