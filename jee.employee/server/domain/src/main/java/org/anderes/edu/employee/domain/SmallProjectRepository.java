package org.anderes.edu.employee.domain;

import javax.inject.Inject;
import javax.persistence.EntityManager;


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

}
