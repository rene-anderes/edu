package org.anderes.edu.jpa.cookbook.solution1;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.Validate;

public class RecipeResository {

	private EntityManager entityManager; 
	
	private RecipeResository() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testDB");
        entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static RecipeResository build() {
		return new RecipeResository();
	}

	public Recipe findOne(final Long databaseidentity) {
		Validate.notNull(databaseidentity, "Der Parameter darf nicht null sein.");
		return entityManager.find(Recipe.class, databaseidentity);
	}
	
	public Collection<Recipe> getRecipesByTitle(final String title) {
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%");

		/* Das Laden des Graphs funktioniert noch nicht wie erwartet --> Check */ 
//		EntityGraph<?> ingredientsGraph = entityManager.createEntityGraph(Recipe.class);
//		ingredientsGraph.addAttributeNodes("ingredients");
//		query.setHint("javax.persistence.loadgraph", ingredientsGraph);
		
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
		Validate.notNull(entity, "Der Parameter darf nicht null sein.");
		
		entityManager.getTransaction().begin();
		final Recipe recipe = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return recipe;
	}
	
	public void remove(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter darf nicht null sein.");
		if (!entityManager.contains(entity)) {
			throw new IllegalArgumentException("Die Entität besitzt nicht den Status managed");
		}
		
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}
}
