package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import org.anderes.edu.jpa.entity.Author;
import org.anderes.edu.jpa.entity.Author_;
import org.anderes.edu.jpa.entity.Book;
import org.anderes.edu.jpa.entity.Book_;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Beispiele für Fetch-Strategien
 * <p/>
 * Beachte: Damit die Test's funktionieren, muss das statische weaving aktiviert sein.
 * 
 * @author René Anderes
 *
 */
public class FetchStrategyTest {

    private EntityManager entityManager;
    private PersistenceUnitUtil util;
    private static EntityManagerFactory entityManagerFactory;
       
    /**
     * Beispiel EAGER Loading per Default
     */
    @Test
    public void findOneBook() {
        
        final Book book = entityManager.find(Book.class, 3000L);
        
        /*
         * Zwei SQL-Queries werden ausgeführt:
         * SELECT ID, DESCRIPTION, ISBN, TITLE, PUBLISHER_ID FROM BOOK WHERE (ID = 3000)
         * SELECT ID, NAME FROM PUBLISHER WHERE (ID = 2001)
         */
        
        assertThat(book, is(not(nullValue())));
        assertThat(util.isLoaded(book, Book_.publisher.getName()), is(true));
    }
    
    /**
     * Beispiel mit LAZY Loading per Default
     */
    @Test
    public void findOneAuthor() {
        
        final Author author = entityManager.find(Author.class, 1000L);
        
        /*
         * Eine SQL-Query wird ausgeführt: 
         * SELECT ID, FIRSTNAME, LASTNAME, VITA FROM AUTHOR WHERE (ID = 1000)
         */
        
        assertThat(author, is(not(nullValue())));
        assertThat(util.isLoaded(author, Author_.books.getName()), is(false));
    }

    /**
     * Beispiel einer Verwendung einer NamedEntityGraph: LoadGraph
     */
    @Test
    public void namedEntityLoadGraph() {
   
        final EntityGraph<?> entityGraph = entityManager.getEntityGraph("Author.books");
         
        final Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("javax.persistence.loadgraph", entityGraph);
        final Author author = entityManager.find(Author.class, 1000L, hints);
        
        assertThat(author, is(not(nullValue())));
        assertThat(util.isLoaded(author, Author_.books.getName()), is(true));
    }
    
    /**
     * Beispiel einer Verwendung einer NamedEntityGraph: FetchGraph
     */
    @Test
    public void namedEntityFetchGraph() {
   
        final EntityGraph<?> entityGraph = entityManager.getEntityGraph("Book.plain");
         
        final Map<String, Object> hints = new HashMap<String, Object>();
        hints.put("javax.persistence.fetchgraph", entityGraph);
        final Book book = entityManager.find(Book.class, 3000L, hints);
        
        assertThat(book, is(not(nullValue())));
        assertThat(util.isLoaded(book, Book_.id.getName()), is(true));
        assertThat(util.isLoaded(book, Book_.title.getName()), is(true));
        assertThat(util.isLoaded(book, Book_.publisher.getName()), is(false));
        assertThat(util.isLoaded(book, Book_.description.getName()), is(false));
    }
    
    @BeforeClass
    public static void setUpOnce() {
        /* Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml */
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
    }
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
        util = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
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
