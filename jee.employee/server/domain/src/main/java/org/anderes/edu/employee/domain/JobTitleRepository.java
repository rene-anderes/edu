package org.anderes.edu.employee.domain;

import javax.inject.Inject;
import javax.persistence.EntityManager;


public class JobTitleRepository implements Repository<JobTitle, Long> {

    @Inject
    private EntityManager entityManager;
    
    @Override
    public JobTitle findOne(final Long id) {
        return entityManager.find(JobTitle.class, id);
    }

    @Override
    public JobTitle save(final JobTitle entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final JobTitle entity) {
        entityManager.remove(entity);
    }

    @Override
    public boolean exists(final Long id) {
        JobTitle entity = findOne(id);
        if (entity != null) {
            return entityManager.contains(entity);
        }
        return false;
    }

}
