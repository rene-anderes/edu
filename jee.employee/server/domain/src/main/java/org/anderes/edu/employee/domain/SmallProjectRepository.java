package org.anderes.edu.employee.domain;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Zugriff auf Instanzen von SmllProject
 */
public class SmallProjectRepository implements Repository<SmallProject, Long> {

    private EntityManager entityManager;
    
    @Inject
    /*package*/ void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public SmallProject findOne(final Long id) {
        return entityManager.find(SmallProject.class, id);
    }

    @Override
    public SmallProject save(final SmallProject entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final SmallProject entity) {
        entityManager.remove(entity);
    }

    @Override
    public boolean exists(final Long id) {
        SmallProject entity = findOne(id);
        if (entity != null) {
            return entityManager.contains(entity);
        }
        return false;
    }

    /**
     * Findet alle Projekte mit dem übergebenen Projektnamen
     */
    public List<SmallProject> find(final String projectName) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<SmallProject> criteria = cb.createQuery(SmallProject.class);
        final Root<SmallProject> entity = criteria.from(SmallProject.class);
        final Predicate thisName = cb.like(entity.get(SmallProject_.name), projectName);
        criteria.where(thisName);
        final TypedQuery<SmallProject> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
}
