package org.anderes.edu.employee.domain;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 * Beispiel eines Repositories (hier für {@code SmallProject}), das 
 * das Transaktions-Management selber übernimmt.
 * 
 * @author René Anderes
 * 
 */
public class SmallProjectRepositoryBeanManagedTransaction {

	@Resource
	private UserTransaction utx;
	@PersistenceUnit
	private EntityManagerFactory emf;

	public SmallProject findOne(final Long id) throws Exception {
		final EntityManager entityManager = emf.createEntityManager();
		final SmallProject project = entityManager.find(SmallProject.class, id);
		return project;
	}

	public SmallProject save(final SmallProject entity) throws Exception {
		try {
			utx.begin();
			final EntityManager entityManager = emf.createEntityManager();
			final SmallProject project = entityManager.merge(entity);
			utx.commit();
			return project;
		} catch (Exception e) {
			utx.rollback();
			throw e;
		}
	}

	public void delete(final SmallProject entity) throws Exception {
		final EntityManager entityManager = emf.createEntityManager();
		deleteLocal(entity, entityManager);
	}

	private void deleteLocal(final SmallProject entity,
			final EntityManager entityManager) throws Exception {
		try {
			utx.begin();
			entityManager.joinTransaction();
			entityManager.remove(entity);
			utx.commit();
		} catch (Exception e) {
			utx.rollback();
			throw e;
		}
	}

	/**
	 * Löscht alle Projekte mit dem entsprechenden Projektnamen
	 */
	public void delete(final String projectName) throws Exception {
		final EntityManager entityManager = emf.createEntityManager();
		final List<SmallProject> projects = findByNameLocal(projectName, entityManager);
		for (final SmallProject smallProject : projects) {
			deleteLocal(smallProject, entityManager);
		}
	}

	/**
	 * Findet alle Projekte mit dem entsprechenden Projektnamen
	 */
	private List<SmallProject> findByNameLocal(final String projectName, final EntityManager entityManager) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<SmallProject> criteria = cb
				.createQuery(SmallProject.class);
		final Root<SmallProject> entity = criteria.from(SmallProject.class);
		final Predicate thisName = cb.like(entity.get(SmallProject_.name), projectName);
		criteria.where(thisName);
		final TypedQuery<SmallProject> query = entityManager
				.createQuery(criteria);
		return query.getResultList();
	}
}
