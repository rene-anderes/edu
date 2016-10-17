package org.anderes.edu.jpa.repository;

import java.util.ArrayList;
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
    public List<Book> getBooksByJpql(final String title, final Optional<String> authorFirstName, final Optional<String> authorLastName) {
        final StringBuilder queryString = new StringBuilder(); 
        queryString.append("SELECT b FROM Book b ").append("WHERE b.title LIKE :title ");
        if (authorFirstName.isPresent() || authorLastName.isPresent()) {
            queryString.append("AND b IN ").append("(SELECT b FROM Author a JOIN a.books b WHERE ");
            if (authorLastName.isPresent() && authorFirstName.isPresent()) {
                queryString.append("a.lastName = :lastname AND a.firstName = :firstname");
            } else if (authorLastName.isPresent()) {
                queryString.append("a.lastName = :lastname");
            } else if (authorFirstName.isPresent()) {
                queryString.append("a.firstName = :firstname");
            }
            queryString.append(")");
        }
        final TypedQuery<Book> query = entityManager.createQuery(queryString.toString(), Book.class).setParameter("title", "%" + title + "%");
        authorLastName.ifPresent(value -> query.setParameter("lastname", value));
        authorFirstName.ifPresent(value -> query.setParameter("firstname", value));
        return query.getResultList();
    }
    
    public List<Book> getBooksByCriteria(final String title, final Optional<String> authorFirstName, final Optional<String> authorLastName) {

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        final Root<Book> entity = criteria.from(Book.class);
        final Predicate titleLike = cb.like(entity.get("title"), "%" + title + "%");
        criteria.where(titleLike);

        return entityManager.createQuery(criteria).getResultList();
    }
}
