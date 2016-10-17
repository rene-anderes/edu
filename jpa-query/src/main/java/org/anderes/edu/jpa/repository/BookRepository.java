package org.anderes.edu.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.anderes.edu.jpa.entity.Book;
import org.anderes.edu.jpa.entity.Book_;

/**
 * Dieses Repository dient dazu die zwei unterschiedlichen Query-Arten
 * JPQL und Criteria Query zu vergleichen. 
 *
 */
public class BookRepository {

    private final EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    public static BookRepository createNewInstance() {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        return new BookRepository();
    }
    
    private BookRepository() {
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    /**
     * Beispiel mit JPQL: Sind Parameter optional, wird der Zusammenbau einer JPQL abenteuerlich! 
     */
    public List<Book> getBooksByJpql(final String title, final Optional<String> description) {
    	if (title == null || description == null) {
    		throw new IllegalArgumentException("Argumente dürfen nicht null sein!");
    	}
    	
        final StringBuilder queryString = new StringBuilder(); 
        queryString.append("SELECT b FROM Book b ").append("WHERE b.title LIKE :title ");
        if (description.isPresent() ) {
        	queryString.append("OR b.description LIKE :description");
        }
        final TypedQuery<Book> query = entityManager
        		.createQuery(queryString.toString(), Book.class)
        		.setParameter("title", "%" + title + "%");
        description.ifPresent(value -> query.setParameter("description", "%" + value + "%"));
        return query.getResultList();
    }
    
    /**
     * Der dynamische Aufbau einer Query ist mittels Criteria Query elegant möglich
     */
    public List<Book> getBooksByCriteria(final String title, final Optional<String> description) {
    	if (title == null || description == null) {
    		throw new IllegalArgumentException("Argumente dürfen nicht null sein!");
    	}
    	
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        final Root<Book> book = criteria.from(Book.class);
        final Predicate titleLike = cb.like(book.get(Book_.title), "%" + title + "%");
        if (description.isPresent()) {
        	final Predicate descriptionLike = cb.like(book.get(Book_.description), "%" + description.get() + "%");
        	criteria.where(cb.or(titleLike, descriptionLike));
        } else {
        	criteria.where(titleLike);
        }
        
        return entityManager.createQuery(criteria).getResultList();
    }
}
