package org.anderes.edu.jpa.cookbook;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.anderes.edu.jpa.cookbook.Recipe_;
import org.apache.commons.lang3.Validate;
import org.eclipse.persistence.config.QueryHints;

public class RecipeRepository {

	private EntityManager entityManager; 
	
	private RecipeRepository() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testDB");
        entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static RecipeRepository build() {
		return new RecipeRepository();
	}

	/**
	 * Dynamisch aufgebauter Entity Graph
	 * 
	 * @param databaseidentity ID
	 * @return Gefundene Datensatz oder {@code null}
	 */
	public Recipe findOne(final Long databaseidentity) {
		Validate.notNull(databaseidentity, "Der Parameter darf nicht null sein.");

		final EntityGraph<Recipe> graph = this.entityManager.createEntityGraph(Recipe.class);
		graph.addAttributeNodes(Recipe_.ingredients.getName());
		
		final Map<String, Object> props = new HashMap<String, Object>();
		props.put(QueryHints.JPA_LOAD_GRAPH, graph);
		
		return entityManager.find(Recipe.class, databaseidentity, props);
	}
	
	/**
	 * Gibt alle Rezepte zurück die im Titel den übergeben Wert enthalten.
	 * </p>
	 * Fetch-Strategie: EntityGraph - <code>javax.persistence.fetchgraph</code> (JPA 2.1)<br>
	 * Entity graphs can be created dynamically from scratch
	 * 
	 * @param title
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitleFetchgraph(final String title) {

	    final EntityGraph<?> ingredientsGraph = entityManager.createEntityGraph(Recipe.class);
	    final Subgraph<Ingredient> itemGraph = ingredientsGraph.addSubgraph("ingredients");
	    itemGraph.addAttributeNodes("description");
	    ingredientsGraph.addAttributeNodes(Recipe_.title.getName());
		
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%").setHint(QueryHints.JPA_FETCH_GRAPH, ingredientsGraph);
		
        return query.getResultList();
	}
	
	/**
	 * Gibt alle Rezepte zurück die im Titel den übergeben Wert enthalten.
	 * </p>
	 * Fetch-Strategie: EntityGraph - <code>javax.persistence.loadgraph</code> (JPA 2.1)<br>
	 * created from an existing named entity graph
	 * 
	 * @param title
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitleLoadgraph(final String title) {

		EntityGraph<?> recipeGraph = entityManager.getEntityGraph("Recipe.detail");

		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%").setHint(QueryHints.JPA_LOAD_GRAPH, recipeGraph);
		
        return query.getResultList();
	}
	
	/**
	 * Gibt alle Rezepte zurück die im Titel den übergeben Wert enthalten.
	 * </p>
	 * Fetch-Strategie: setHint(...) (EclipseLink spezifisch)
	 * @param title
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitle(final String title) {
		
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%").setHint(QueryHints.BATCH, "r.ingredients").setHint(QueryHints.LOAD_GROUP_ATTRIBUTE, "ingredients");
		
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
