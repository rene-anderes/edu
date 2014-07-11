package org.anderes.edu.employee.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.anderes.edu.employee.domain.SmallProject;
import org.anderes.edu.employee.domain.SmallProjectRepository;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Dieser JUnit-Test zeigt, wie ein Repository ohne Zuhilfenahme von
 * Arquillian getestet werden kann.
 * </p>
 * Dieser Test setzt voraus, dass die Testdaten geladen wurden:<br>
 * Module Database: {@code mvn flyway:migrate verify -Pderby,testdata}
 * </p>
 * Für diesen Test ist es natürlich möglich dbUnit einzusetzen 
 * um die Datenbank mit Testdaten zu füllen und nach dem Test
 * zu überprüfen.
 * 
 * @author René Anderes
 * 
 */
public class SmallProjectRepositoryOutsideContainerIT {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private SmallProjectRepository repository;

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
        entityManager = entityManagerFactory.createEntityManager();
        assertThat(entityManager.isOpen(), is(true));
        repository = new SmallProjectRepository();
        repository.setEntityManager(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    @Test
    public void shouldBeFindOne() {

        final SmallProject project = repository.findOne(53L);
        assertThat(project, is(notNullValue()));
        assertThat(project.getName(), is("Accounting Query Tool"));
    }
    
    @Test
    public void shouldBeStoreNewSmallProject() {
        // given
        final SmallProject newProject = new SmallProject("Laboro", "Ein Projekt für die Auftragsbearbeitung");
        
        // when
        entityManager.getTransaction().begin();
        final SmallProject project = repository.save(newProject);
        entityManager.getTransaction().commit();
        
        // then
        assertThat(project, is(notNullValue()));
        assertThat(project.getId(), is(notNullValue()));
        assertThat(project.getId() > 0, is(true));
    }
}
