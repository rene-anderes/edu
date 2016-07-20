package org.anderes.edu.jpa.cookbook.solution1;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.Validate;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 * In diesem Beispiel eines Repository wird der Entity-Manager für jeden
 * Zugriff neue Instanziert und wieder geschlossen. Damit wird der 
 * Persistence-Context wieder abgebaut und alle Entitäten erhalten den 
 * Zustand "detached".
 * <p>
 * Beachte:
 * <p>
 * Die JPA Spec (Version 2.0) sagt in Kapitel 3.2.7, "Detached Entities", 
 * sinngemäss, dass eine Applikation nach dem Ende des 
 * Persistence Contexts nur noch auf den "verfügbaren Zustand" einer Entität 
 * zugreifen darf. Der verfügbare Zustand ist dabei die Summe aller Attribute,
 * die nicht (implizit oder explizit) als "Lazy" markiert sind, zusammen mit 
 * der Summe aller Attribute, auf die bereits von der Applikation zugegriffen
 * wurde. Dazu zählen auch alle Beziehungen, die entweder mit FetchType.EAGER 
 * markiert sind oder bereits geladen wurden, sei es über eine Query (als 
 * direktes Ergebnis oder über ein JOIN FETCH) oder über EntityManager.find. 
 * Zwar schreibt die Spezifikation nicht vor, dass auf andere Attribute nicht 
 * zugegriffen werden darf; aber Applikationen, die das tun, sind nicht mehr 
 * "portable", d. h. der Persistence-Provider kann nicht mehr ohne Weiteres 
 * ausgetauscht werden.<br>
 * Und tatsächlich erlaubt auch nur EclipseLink den Zugriff auf nicht geladene
 * Attribute nach dem Ende des Persistence Contexts, und das auch nur, wenn 
 * die Entität zwischendurch nicht serialisiert, also z. B. an eine 
 * Remote EJB übergeben wurde.
 * 
 * @author René Anderes
 *
 */
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
	 * <p>
	 * Bei eingeschaltetem Shared Cache wird die Query gespeichert.
	 * 
	 * @param title Titel oder Teil eines Titels
	 * @return Rezept
	 */
	public Collection<Recipe> getRecipesByTitle(final String title) {
		Validate.notBlank(Validate.notNull(title));
	    final EntityManager entityManager = entityManagerFactory.createEntityManager();
		final TypedQuery<Recipe> query = entityManager.createNamedQuery(Recipe.RECIPE_QUERY_BYTITLE, Recipe.class);
		query.setParameter("title", "%" + title + "%");
		
		/* ----- query cache */
		query.setHint(QueryHints.CACHE_USAGE, CacheUsage.CheckCacheThenDatabase);
		/* siehe http://www.eclipse.org/eclipselink/documentation/2.6/jpa/extensions/queryhints.htm */
		/* ----- / query cache */

		
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
     * Beispiel für 'JPQL Constructor Expressions' : NEW
     * 
     */
    public Collection<RecipeShort> getRecipesShortByIngredient(String ingredientDescription) {
        final String queryStr = "Select NEW org.anderes.edu.jpa.cookbook.solution1.RecipeShort(r.id, r.title, r.preamble) "
                        + "from Recipe r join r.ingredients i where i.description like :description";
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final TypedQuery<RecipeShort> query = entityManager.createQuery(queryStr, RecipeShort.class);
        query.setParameter("description", "%" + ingredientDescription + "%");
        
        final List<RecipeShort> recipes =  query.getResultList();
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
		Recipe savedRecipe = null;
		if (entity.getId() == null || entity.getId() == 0) {
		    entityManager.persist(entity);
		    savedRecipe = entity;
		} else {
		    savedRecipe = entityManager.merge(entity);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		return savedRecipe;
	}
		
	public void remove(final Recipe entity) {
		Validate.notNull(entity, "Der Parameter darf nicht null sein.");
		
		final EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		final Recipe toDelete = entityManager.merge(entity);
		entityManager.remove(toDelete);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	/**
	 * Zugriff und Mapping einer View mittels JPA
	 * <p>
	 * Anstelle einer JPQl Abfrage "SELECT t FROM TagCounterView t" wird hier
	 * eine Criteria Query eingesetzt. Dies hat jedoch nichts mit dem Mapping
	 * einer View zu tun, sondern soll als Abwechslung da sein.
	 */
    public List<TagCounterView> getTags() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<TagCounterView> criteria = cb.createQuery(TagCounterView.class);
        final Root<TagCounterView> entity = criteria.from(TagCounterView.class);
        criteria.select(entity);
        final List<TagCounterView> recipes =  entityManager.createQuery(criteria).getResultList();
        entityManager.close();
        return recipes;
    }
}
