package org.anderes.edu.jpa;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.anderes.edu.jpa.entity.Author;
import org.anderes.edu.jpa.entity.Author_;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.LoadGroup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Beispiele für Fetch-Strategien
 * 
 * @author René Anderes
 *
 */
public class QueryFetchStrategyTest {

    private EntityManager entityManager;
    private PersistenceUnitUtil util;
    private static EntityManagerFactory entityManagerFactory;
       
    /**
     * Join Fetch mit Criteria Query
     */
    @Test
    public void joinFetchWithCriteriaQuery() {
        
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Author> criteria = builder.createQuery(Author.class);
        final Root<Author> entity = criteria.from(Author.class);
        
        entity.fetch(Author_.books);
        criteria.distinct(true);
        
        final List<Author> authors = entityManager.createQuery(criteria).getResultList();
        
        assertThat(authors.size(), is(2));
        authors.stream().forEach(author -> {
            assertThat(util.isLoaded(author, Author_.books.getName()), is(true));
        });
    }
    
    /**
     * Join Fetch mit Criteria Query
     */
    @Test
    public void joinFetchWithJPQL() {
        
        final List<Author> authors = entityManager.createQuery("SELECT DISTINCT a FROM Author a JOIN FETCH a.books", Author.class).getResultList();
        
        assertThat(authors.size(), is(2));
        authors.stream().forEach(author -> {
            assertThat(util.isLoaded(author, Author_.books.getName()), is(true));
        });
    }
    
    /**
     * EclipseLink Batch Fetching
     */
    @Test
    public void batchFetching() {
        
        final TypedQuery<Author> query = entityManager.createQuery("SELECT DISTINCT a FROM Author a", Author.class);
        
        /* load Group */
        final LoadGroup group = new LoadGroup();
        group.addAttribute(Author_.books.getName());
        query.setHint(QueryHints.LOAD_GROUP, group);
        /* Specify the use of batch loading to reduce the number of SQL statements */
        query.setHint(QueryHints.BATCH, "a." + Author_.books.getName());
        /* EclipseLink supports three different batch fetching types, JOIN, EXISTS, IN */
        query.setHint(QueryHints.BATCH_TYPE, BatchFetchType.EXISTS);
        
        final List<Author> authors = query.getResultList();
        
        assertThat(authors.size(), is(2));
        authors.stream().forEach(author -> {
            assertThat(util.isLoaded(author, Author_.books.getName()), is(true));
        });
    }
    
    @BeforeClass
    public static void setUpOnce() {
        /* Der Name der Persistence-Unit entspricht dem in der Konfigurationsdatei META-INF/persistence.xml */
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
