package org.anderes.edu.employee.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Dieser Test soll das JPA Optimistic Locking demonstrieren
 * </p>
 * Dieser Test setzt voraus, dass die Testdaten geladen wurden:<br>
 * Module Database: {@code mvn flyway:migrate verify -Poracle,testdata}
 * 
 * @author René Anderes
 * 
 */
public class SmallProjectRepositoryOptimisticLockingIT {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager firstEntityManager;
    private EntityManager secondEntityManager;
    private SmallProjectRepository firstRepository;
    private SmallProjectRepository secondRepository;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("employee", getPersistenceProperties());
        assertThat("EntityManagerFactory konnte nicht instanziiert werden.", entityManagerFactory, is(notNullValue()));
    }

    private static Map<String, String> getPersistenceProperties() {
        final Map<String, String> properties = new HashMap<>(1);
        properties.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "META-INF/outsideContainer-persistence.xml");
        return properties;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Before
    public void setUp() throws Exception {
        // first Entity Manager
        firstEntityManager = entityManagerFactory.createEntityManager();
        assertThat(firstEntityManager.isOpen(), is(true));
        firstRepository = new SmallProjectRepository();
        firstRepository.setEntityManager(firstEntityManager);
        // second Entity Manager
        secondEntityManager = entityManagerFactory.createEntityManager();
        assertThat(secondEntityManager.isOpen(), is(true));
        secondRepository = new SmallProjectRepository();
        secondRepository.setEntityManager(secondEntityManager);
    }

    @After
    public void tearDown() throws Exception {
        if (firstEntityManager != null) {
            firstEntityManager.close();
        }
        if (secondEntityManager != null) {
            secondEntityManager.close();
        }
    }
    
    /**
     * Beispiel für das Optimistic Locking via JPA
     * </p>
     * <ol>
     * <li>der erste Entity Manager liest das Objekt</li>
     * <li>der zweite Entity Manager liest das Objekt</li>
     * <li>der zweite Entity Manager verändert das Objekt und macht ein commit</li>
     * <li>der erste Entity Manager verändert das Objekt und macht ein commit, der fehl schlägt. 
     *     Dieser schlägt fehl, weil die Version des Objekt in der Zwischenzeit erhöht wurde.</li>
     * </ol>
     */
    @Test(expected = RollbackException.class)
    public void shouldBeOptimisticLockException() {

        firstEntityManager.getTransaction().begin();
        // erstes Lesen der Daten
        final SmallProject project = firstRepository.findOne(53L);
        assertThat(project, is(notNullValue()));
        assertThat(project.getName(), is("Accounting Query Tool"));
        
        // zweite Transaktion verändert die bereits gelesenen Daten
        otherEntityManagerWithOwnTransaction(53L);
        
        // Daten ändern und speichern
        project.setDescription("Diese Beschreibung wird nicht gespeichert");
        firstRepository.save(project);
        firstEntityManager.getTransaction().commit();
        
    }
    
    /**
     * Beispiel für das Optimistic Locking bzw. Reftresh via JPA
     * </p>
     * <ol>
     * <li>der erste Entity Manager liest das Objekt</li>
     * <li>der zweite Entity Manager liest das Objekt</li>
     * <li>der zweite Entity Manager verändert das Objekt und macht ein commit</li>
     * <li>der erste Entity Manager <b>macht einen refresh</b>, verändert das Objekt und macht einen commit.</li>
     * </ol>
     * Dieser Vorgang funktioniert, da der erste Entity Manager die Änderungen, die durch den 
     * zweiten Entity Manager gemacht wurden, durch die Aktualisierung (refresh) mitbekommt.
     */
    @Test
    public void shouldBeSaveOk() {

        firstEntityManager.getTransaction().begin();
        // erstes Lesen der Daten
        final SmallProject project = firstRepository.findOne(54L);
        assertThat(project, is(notNullValue()));
        assertThat(project.getName(), is("Staff Query Tool"));
        
        // zweite Transaktion verändert die bereits gelesenen Daten
        otherEntityManagerWithOwnTransaction(54L);
        
        // Daten refresh, ändern und speichern
        firstEntityManager.refresh(project);
        project.setDescription("Ein Tool für die Abrechnung");
        final SmallProject storedProject = firstRepository.save(project);
        firstEntityManager.getTransaction().commit();
        
        assertThat(storedProject.getDescription(), is("Ein Tool für die Abrechnung"));
    }
    
    private void otherEntityManagerWithOwnTransaction(final Long projectId) {
        
        secondEntityManager.getTransaction().begin();
        final SmallProject project = secondRepository.findOne(projectId);
        assertThat(project, is(notNullValue()));
        
        project.setDescription("Neue Beschreibung");
        
        secondRepository.save(project);
        secondEntityManager.getTransaction().commit();
    }

}
