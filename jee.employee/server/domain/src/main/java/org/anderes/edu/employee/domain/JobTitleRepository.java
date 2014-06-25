package org.anderes.edu.employee.domain;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * Repository für den CRUD Zugriff auf die Job-Title.
 * </p>
 * JPA 2.1: Die Annotation @Transactional verwendet um sicher zu stellen,
 * das die entsprechenden Methoden mittels Transaktion ablaufen.
 */
public class JobTitleRepository implements Repository<JobTitle, Long> {

    @Inject
    private EntityManager entityManager;
    
    @Override
    public JobTitle findOne(final Long id) {
        return entityManager.find(JobTitle.class, id);
    }

    @Transactional(value = TxType.MANDATORY)
    @Override
    public JobTitle save(final JobTitle entity) {
        return entityManager.merge(entity);
    }

    @Transactional(value = TxType.MANDATORY)
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
