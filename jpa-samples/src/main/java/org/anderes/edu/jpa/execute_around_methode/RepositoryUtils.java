package org.anderes.edu.jpa.execute_around_methode;

import javax.persistence.EntityManager;

public class RepositoryUtils<T> {

    public T withoutTransaction(final EntityManager entityManager, final JpaFunction<T> function) {
        try {
            return function.execute();
        } finally {
            entityManager.close();
        }
    }
    
    public T withTransaction(final EntityManager entityManager, final JpaFunction<T> function) {
        try {
            entityManager.getTransaction().begin();
            T result = function.execute();
            entityManager.getTransaction().commit();
            return result;
        } finally {
            entityManager.close();
        }
    }
    
    public void withTransaction(final EntityManager entityManager, final VoidJpaFunction function) {
        try {
            entityManager.getTransaction().begin();
            function.execute();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
