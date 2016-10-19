package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.anderes.edu.jpa.entity.Author;
import org.anderes.edu.jpa.entity.Author_;
import org.anderes.edu.jpa.entity.Book;
import org.anderes.edu.jpa.entity.Book_;
import org.anderes.edu.jpa.entity.PhoneBook;
import org.anderes.edu.jpa.entity.PhoneBook_;
import org.anderes.edu.jpa.entity.Publisher;
import org.anderes.edu.jpa.entity.Publisher_;
import org.anderes.edu.jpa.model.AuthorInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CriteriaQueryTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;

    /**
     * Einfache Abfrage die alle Records einer Entity zurück liefert
     */
    @Test
    public void selectAllEntities() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        
        // Ergebnismenge vom Typ Book
        final CriteriaQuery<Book> criteria = builder.createQuery(Book.class);  
        
        // Entität über die die anderen Entitäten durch Navigation erreicht werden können
        criteria.from(Book.class);                                                      
        
        final List<Book> books = entityManager.createQuery(criteria).getResultList();

        assertThat(books.size(), is(6));
    }

    
    /**
     * Einfach Abfrage in der eine WHERE verwendet wird
     */
    @Test
    public void simpleCriteriaQueryWithWhereClause() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        final Root<Book> entity = criteria.from(Book.class);
        criteria.where(builder.like(entity.get(Book_.title), "%Scrum%"));
        final List<Book> books = entityManager.createQuery(criteria).getResultList();

        assertThat(books.size(), is(2));
    }
    
    /**
     * WHERE mit Predicate
     */
    @Test
    public void whereWithPredicate() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> criteria = builder.createQuery(Book.class);
        final Root<Book> entity = criteria.from(Book.class);
        final Predicate titleLike = builder.like(entity.get(Book_.title), "%Scrum%");
        criteria.where(titleLike);
        final List<Book> books = entityManager.createQuery(criteria).getResultList();

        assertThat(books.size(), is(2));
    }
    
    /**
     * Einzelne Attribute einer Entity deklarieren
     */
    @Test
    public void defineTheAttributes() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        final Root<Author> entity = criteria.from(Author.class);
        criteria.multiselect(entity.get(Author_.firstName), entity.get(Author_.lastName));
        final List<Object[]> authors = entityManager.createQuery(criteria).getResultList();

        assertThat(authors.size(), is(2));
    }
    
    /**
     * Einzelne Attribute einer Entity deklarieren und damit ein neues Pojo instanziieren
     */
    @Test
    public void defineTheAttributesWithNewPojo() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<AuthorInfo> criteria = builder.createQuery(AuthorInfo.class);
        final Root<Author> entity = criteria.from(Author.class);
        criteria.select(builder.construct(
                        AuthorInfo.class, 
                        entity.get(Author_.firstName), 
                        entity.get(Author_.lastName)));
        final List<AuthorInfo> authors = entityManager.createQuery(criteria).getResultList();

        assertThat(authors.size(), is(2));
    }
    
    /**
     * JOIN für Entities mit Beziehungen zueinander
     */
    @Test
    public void joinRelatedEntities() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<String[]> criteria = builder.createQuery(String[].class);
        final Root<Book> entity = criteria.from(Book.class);
        final Join<Book, Publisher> publisher = entity.join(Book_.publisher);
        criteria.multiselect(entity.get(Book_.title), publisher.get(Publisher_.name));
        final List<String[]> books = entityManager.createQuery(criteria).getResultList();
        
        /* Resultat: Liste von Buchtitel und Verlegername */
        assertThat(books.size(), is(6));
    }

    /**
     * Mehrere JOIN's für Entities mit Beziehungen, WHERE Klausel und DISTINCT
     */
    @Test
    public void joinedRelatedEntitiesWithSimpleWhereClause() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        final Root<Author> entity = criteria.from(Author.class);
        final Join<Author, Book> book = entity.join(Author_.books);
        final Join<Book, Publisher> publisher = book.join(Book_.publisher);
        criteria.where(builder.like(publisher.get(Publisher_.name), "%Redline%"));
        criteria.distinct(true);
        final List<Author> authors = entityManager.createQuery(criteria).getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    /**
     * JOIN für Entities die keine Beziehung zueinander haben mit zwei Predicates
     */
    @Test
    public void joinUnrelatedEntities() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        final Root<Author> author = criteria.from(Author.class);
        final Root<PhoneBook> phoneBook = criteria.from(PhoneBook.class);
        final Predicate equalFirstame = builder.equal(author.get(Author_.firstName), phoneBook.get(PhoneBook_.firstname));
        final Predicate equalLastname = builder.equal(author.get(Author_.lastName), phoneBook.get(PhoneBook_.lastname));
        criteria.where(equalFirstame, equalLastname);
        final List<Author> authors = entityManager.createQuery(criteria).getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    /**
     * Beispiel mit SubQuery
     * <p/>
     * Die JPQL Abfrage "SELECT a FROM Author a WHERE a.lastName IN (SELECT p.lastname FROM PhoneBook p WHERE p.firstname = 'Boris')"
     * als Criteria Query 
     */
    @Test
    public void subQuery() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        final Root<Author> author = criteria.from(Author.class);
        
        /* Zusammenbau der SubQuery: SELECT p.lastname FROM PhoneBook p WHERE p.firstname = 'Boris' */
        final Subquery<String> subquery = criteria.subquery(String.class);    // Ergebnismenge vom Type String: Nachnameliste
        final Root<PhoneBook> phoneBook = subquery.from(PhoneBook.class);  
        subquery.select(phoneBook.get(PhoneBook_.lastname));
        subquery.where(builder.equal(phoneBook.get(PhoneBook_.firstname), "Boris"));
        
        /* Zusammenbau der WHERE Klausel: WHERE a.lastName IN (SELECT p.lastname FROM PhoneBook p WHERE p.firstname = 'Boris') */
        criteria.where((author.get(Author_.lastName).in(subquery)));
        
        final List<Author> authors = entityManager.createQuery(criteria).getResultList();
        
        assertThat(authors.size(), is(1));
    }
    
    @BeforeClass
    public static void setUpOnce() {
        /* Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml */
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
    }
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        entityManagerFactory.close();
    }
}
