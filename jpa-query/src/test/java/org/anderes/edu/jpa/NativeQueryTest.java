package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.anderes.edu.jpa.entity.Book;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Beispiele für 'Native Query'
 * 
 * @author René Anderes
 *
 */
public class NativeQueryTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    /**
     * Einfaches Beispiel welche die Verwendung von nativen SQL zeigt
     */
    @Test
    @SuppressWarnings("unchecked")
    public void simpleNativeQuery() {
        final List<Book> books = entityManager.createNativeQuery("SELECT * FROM BOOK ORDER BY TITLE", Book.class).getResultList();
        
        assertThat(books.size(), is(6));
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
