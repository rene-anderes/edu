package org.anderes.edu.relations.onetomany;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompanyPersonRelationTest {

    private EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testDB");
    
    @Before
    public void setUp() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }

    @Test
    public void shouldBeFindOnePerson() {
        final Person ceo = entityManager.find(Person.class, 2001L);
        
        assertThat(ceo, is(notNullValue()));
        assertThat(ceo.getLastname(), is("Krzanich"));
        assertThat(ceo.getCompany(), is(notNullValue()));
        assertThat(ceo.getCompany().getName(), is("Intel"));
    }
    
    @Test
    public void shouldBeFindOneCompany() {
        final Company amd = entityManager.find(Company.class, 10002L);
        
        assertThat(amd, is(notNullValue()));
        assertThat(amd.getName(), is("AMD"));
        assertThat(amd.getEmployees(), is(notNullValue()));
        assertThat(amd.getEmployees().size(), is(2));
    }
    
    /**
     * Wird eine Person als Mitarbeiter aus dem Betrieb gelöscht,
     * so existieren beide Objekte (Person und Company) weiter haben
     * jedoch keine Beziehung mehr.
     */
    @Test
    public void shouldBeFireOneEmployee() {
        final Company microsoft = entityManager.find(Company.class, 10003L);
        final Person billGates = entityManager.find(Person.class, 2004L);
        assertThat(microsoft.getEmployees().size(), is(3));

        microsoft.removeEmployee(billGates);
        
        entityManager.getTransaction().begin();
        entityManager.persist(microsoft);
        entityManager.getTransaction().commit();
        
        assertThat(billGates.getCompany(), is(nullValue()));
        assertThat(microsoft.getEmployees().size(), is(2));
    }
    
    /**
     * Wird eine Person gelöscht, so muss sicher gestellt werden, dass
     * die Company keine Referenz mehr hält: {@code @PreRemove} in der Klasse Person
     */
    @Test
    public void shouldBeDeleteOnePerson() {
        final Person lisaSu = entityManager.find(Person.class, 2002L);
        final Company amd = entityManager.find(Company.class, 10002L);
        assertThat(amd.getEmployees().size(), is(2));
        
        entityManager.getTransaction().begin();
        entityManager.remove(lisaSu);
        entityManager.getTransaction().commit();
        
        assertThat(amd.getEmployees().size(), is(1));
    }
    
    /**
     * Wird eine Company gelöscht, so muss sicher gestellt werden, dass
     * die Personen (in diesem Fall die Mitarbeiter) keine Verbindung
     * bzw. Referenz zu dieser Company mehr haben: {@code @PreRemove} in der Klasse Company
     */
    @Test
    public void shouldBeDeleteOneCompay() {
        final Company google = entityManager.find(Company.class, 10004L);
        assertThat(google.getEmployees().size(), is(2));
        
        entityManager.getTransaction().begin();
        entityManager.remove(google);
        entityManager.getTransaction().commit();
        
        final Person larryPage = entityManager.find(Person.class, 2009L);
        assertThat(larryPage.getCompany(), is(nullValue()));
        final Person sergeyBrin = entityManager.find(Person.class, 2010L);
        assertThat(sergeyBrin.getCompany(), is(nullValue()));
    }
    
    /**
     * Die Methode {@code persist} verhält sich wie erwartet:
     * Da kein {@code cascade} definiert ist muss jede neue Entität separat gespeichert werden.
     */
    @Test
    public void shouldBePersistNewCompanyAndNewPerson() {
        final Person person = new Person("Larry", "Ellison");
        final Company company = new Company("Oracle");
        person.setCompany(company);
        
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.persist(company);
        entityManager.getTransaction().commit();
        
        assertThat(entityManager.contains(company), is(true));
        assertThat(entityManager.contains(person), is(true));
    }
    
    /* ----------- Bidirectional associations do not play well with the EntityManager.merge method. ---------- */ 
    
    /**
     * Das verhalten der Methode {@code merge} ist nicht so richtig.
     * Die Person wird persistiert mittels Company ...??
     */
    @Test
    public void shouldBeMergeNewCompanyAndNewPerson() {
    	final Person person = new Person("Peter", "Lustig");
    	final Company company = new Company("Alphabet");
    	person.setCompany(company);
    	
    	entityManager.getTransaction().begin();
    	final Company savedCompany = entityManager.merge(company);
    	entityManager.getTransaction().commit();
    	
    	assertThat(entityManager.contains(savedCompany), is(true));
    	assertThat(entityManager.contains(company), is(false));
    	assertThat(entityManager.contains(person), is(false));
    	
    	assertThat(savedCompany.getId(), is(notNullValue()));
    	assertThat(savedCompany.getEmployees(), is(notNullValue()));
    	assertThat(savedCompany.getEmployees().size(), is(1));
    }
   
    /**
     * Auch in dieser Richtung verhält sich die Methode {@code merge}
     * nicht wie erwartet: Die Company wird mittels Person gespeichert.
     */
    @Test
    public void shouldBeMergeNewPersonAndNewCompany() {
        final Person person = new Person("Andy", "Meier");
        final Company company = new Company("Wikipedia");
        person.setCompany(company);
        
        entityManager.getTransaction().begin();
        final Person savedPerson = entityManager.merge(person);
        entityManager.getTransaction().commit();
        
        assertThat(savedPerson.getId(), is(notNullValue()));
        assertThat(savedPerson.getCompany(), is(notNullValue()));
        assertThat(savedPerson.getCompany().getId(), is(notNullValue()));
        assertThat(savedPerson.getCompany().getEmployees().size(), is(1));
    }
}
