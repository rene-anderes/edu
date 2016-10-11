package org.anderes.edu.jpa.relation.manytomany;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    @BeforeClass
    public static void setUpOnce() {
        // Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml
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
    
    @Test
    public void shouldBeFindProject() {
       final Project laboro = entityManager.find(Project.class, 3000L);
       assertThat(laboro.getName(), is("Laboro"));
       final Project hawai = entityManager.find(Project.class, 3001L);
       assertThat(hawai.getName(), is("HAWAI"));
       final Project loganto = entityManager.find(Project.class, 3002L);
       assertThat(loganto.getName(), is("Loganto"));
       final Project bbm = entityManager.find(Project.class, 3003L);
       assertThat(bbm.getName(), is("BBM"));
       final Project imposto = entityManager.find(Project.class, 3004L);
       assertThat(imposto.getName(), is("Imposto"));
    }
    
    @Test
    public void shouldBeFindEmployee() {
        final Employee jeff = entityManager.find(Employee.class, 4000L);
        assertThat(jeff.getFirstname(), is("Jeff"));
        assertThat(jeff.getLastname(), is("Meier"));
        assertThat(jeff.getProjects(), is(not(nullValue())));
        assertThat(jeff.getProjects().size(), is(2));
        final Employee clif = entityManager.find(Employee.class, 4001L);
        assertThat(clif.getFirstname(), is("Clif"));
        assertThat(clif.getLastname(), is("Meier"));
        final Employee peter = entityManager.find(Employee.class, 4002L);
        assertThat(peter.getFirstname(), is("Peter"));
        assertThat(peter.getLastname(), is("Jones"));
    }
}
