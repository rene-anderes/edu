package org.anderes.logging;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Log4j2ToDatabaseTest {

    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final Marker UnitTestMarker = MarkerManager.getMarker("UnitTest-Marker");
    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    @BeforeClass
    public static void setUpOnce() {
        // Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("loggingPersistenceUnit");
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
    
    @Test
    public void shouldBeSimpleLog() {
       
        logger.info("Dies ist eine einfache Log-Meldung");
        
        final TypedQuery<JpaLogEntity> query = entityManager.createNamedQuery("LogEntity.All", JpaLogEntity.class);
        final Collection<JpaLogEntity> logs = query.getResultList();
        
        assertThat(logs, is(notNullValue()));
        assertThat(logs.size() > 0, is(true));
    }
    
    @Test
    public void shouldBeLogWithMarker() {
        logger.info(UnitTestMarker, "Dies ist eine Log-Meldung mit Marker"); 
    }
    
    @Test
    public void shouldBeLogWithThreadContextMap() {
        
        // Given
        ThreadContext.put("User", "Unit-Test-User");
        ThreadContext.put("IP-Address", "123.445.876.9");
        
        // when
        logger.warn("Dies ist eine Log-Meldung mit ThreadContextMap"); 
        
        // Then
        // check the database
        ThreadContext.clearMap();
        
    }
    
    @Test
    public void shouldBeLogWithThreadContextStack() {
        
        // Given
        ThreadContext.push("JUnit-Test-User");
        
        // when
        logger.trace("Dies ist eine Log-Meldung mit ThreadContextStack"); 
        
        // Then
        // check the database
        ThreadContext.clearStack();
    }
    
    @Test
    public void shouldBeLogWithThrown() {
        
        // Given
        final Exception e = new IllegalArgumentException("Spezielle Meldung aus der Exception");
        
        // When
        logger.error("Dies ist eine Log-Meldung mit 'thrown'", e);
        
        // Then
        // check the database
        
    }
}
