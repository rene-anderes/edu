package org.anderes.edu.employee.domain;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.anderes.edu.employee.domain.Employee_;
import org.anderes.edu.employee.domain.LargeProject_;

/**
 * Zugriff auf Objekte vom Typ LargeProject
 */
public class LargeProjectRepository implements Repository<LargeProject, Long> {

    @Inject
    private EntityManager entityManager;
    
    @Override
    public LargeProject findOne(final Long id) {
        return entityManager.find(LargeProject.class, id);
    }

    @Override
    public LargeProject save(final LargeProject entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final LargeProject entity) {
        entityManager.remove(entity);
    }

    @Override
    public boolean exists(final Long id) {
        LargeProject entity = findOne(id);
        if (entity != null) {
            return entityManager.contains(entity);
        }
        return false;
    }

    public List<LargeProject> findAll() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<LargeProject> criteria = cb.createQuery(LargeProject.class);
        final Root<LargeProject> entity = criteria.from(LargeProject.class);
        criteria.select(entity);
        final TypedQuery<LargeProject> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fetch Strategien */

    /**
     * Sample with join fetch by Criteria Query (multi-level)
     */
    public LargeProject findProjectWithLeader(final Long projectId) {
        
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<LargeProject> criteria = cb.createQuery(LargeProject.class);
        final Root<LargeProject> entity = criteria.from(LargeProject.class);
        criteria.where(cb.equal(entity.get(LargeProject_.id), projectId));
        /* ----- join fetch multi-level */
        entity.fetch(LargeProject_.teamLeader, JoinType.LEFT).fetch(Employee_.address, JoinType.LEFT);
        /* ----- / join fetch */
        final TypedQuery<LargeProject> query = entityManager.createQuery(criteria);
        
        return query.getSingleResult();
    }
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / Fetch Strategien */
   
}
