package org.anderes.edu.jpa.basicattributes;

import static java.util.Calendar.JANUARY;
import static org.anderes.edu.jpa.basicattributes.PersonBasic.Gender.FEMALE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonBasicTest {

    private EntityManager entityManager;
    
    @Before
    public void setUp() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }

    @Test
    public void shouldBePersist() {
    	
    	// given
    	final PersonBasic person = new PersonBasic("Mona-Lisa", "DaVinci");
    	person.setBirthday(birthday());
    	person.setSalary(BigDecimal.valueOf(45000D));
    	person.setGender(FEMALE);

    	// when
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    	
        // then
        final PersonBasic storedPerson = entityManager.find(PersonBasic.class, person.getId());
        assertThat(storedPerson.getFirstname(), is("Mona-Lisa"));
        assertThat(storedPerson.getLastname(), is("DaVinci"));
        assertThat(storedPerson.getBirthday(), is(birthday()));
        assertThat(storedPerson.getSalary(), is(BigDecimal.valueOf(45000D)));
        assertThat(storedPerson.getState(), is(nullValue()));
        assertThat(storedPerson.getGender(), is(FEMALE));
    }
    
    private Calendar birthday() {
    	final Calendar birthday = Calendar.getInstance();
    	birthday.clear();
    	birthday.set(1973, JANUARY, 1);
    	return birthday;
    }
    
    @Test
    public void shouldBePersistNameAndFirstname() {
    	
    	// given
    	final PersonBasic person = new PersonBasic("Mona-Lisa", "DaVinci");

    	// when
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    	
        // then
        final PersonBasic storedPerson = entityManager.find(PersonBasic.class, person.getId());
        assertThat(storedPerson.getFirstname(), is("Mona-Lisa"));
        assertThat(storedPerson.getLastname(), is("DaVinci"));
        assertThat(storedPerson.getBirthday(), is(nullValue()));
        assertThat(storedPerson.getSalary(), is(nullValue()));
        assertThat(storedPerson.getState(), is(nullValue()));
        assertThat(storedPerson.getGender(), is(nullValue()));
    }
    
    @Test
    public void shouldBePersistWithoutTransient() {
    	
    	// given
    	final PersonBasic person = new PersonBasic("Mona-Lisa", "DaVinci");
    	person.setState("OK");

    	// when
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    	
        // then
        assertThat(person.getFirstname(), is("Mona-Lisa"));
        assertThat(person.getLastname(), is("DaVinci"));
        assertThat(person.getBirthday(), is(nullValue()));
        assertThat(person.getSalary(), is(nullValue()));
        assertThat(person.getGender(), is(nullValue()));
        
        // ==== Transient bleibt unangetastet
        assertThat(person.getState(), is("OK"));				
        
        final PersonBasic storedPerson = entityManager.find(PersonBasic.class, person.getId());
        assertThat(storedPerson.getFirstname(), is("Mona-Lisa"));
        assertThat(storedPerson.getLastname(), is("DaVinci"));
        assertThat(storedPerson.getBirthday(), is(nullValue()));
        assertThat(storedPerson.getSalary(), is(nullValue()));
        assertThat(storedPerson.getGender(), is(nullValue()));
        
        // ==== Aus dem Enity Manager (fist level cache)
        assertThat(storedPerson.getState(), is("OK"));
        
        
        final TypedQuery<PersonBasic> query = entityManager.createQuery("select p from PersonBasic p where p.id = :id", PersonBasic.class);
        query.setParameter("id", person.getId());
        final PersonBasic findPerson = query.getSingleResult();
        assertThat(findPerson.getFirstname(), is("Mona-Lisa"));
        assertThat(findPerson.getLastname(), is("DaVinci"));
        assertThat(findPerson.getBirthday(), is(nullValue()));
        assertThat(findPerson.getSalary(), is(nullValue()));
        assertThat(findPerson.getGender(), is(nullValue()));
        
        // ==== Aus dem Enity Manager (fist level cache)
        assertThat(findPerson.getState(), is("OK"));
    }
    
    @Test
    public void shouldBeMergeWithoutTransient() {
    	
    	// given
    	final PersonBasic person = new PersonBasic("Mona-Lisa", "DaVinci");
    	person.setState("OK");

    	// when
        entityManager.getTransaction().begin();
        final PersonBasic storedPerson = entityManager.merge(person);
        entityManager.getTransaction().commit();
    	
        // then
        assertThat(person.getFirstname(), is("Mona-Lisa"));
        assertThat(person.getLastname(), is("DaVinci"));
        assertThat(person.getBirthday(), is(nullValue()));
        assertThat(person.getSalary(), is(nullValue()));
        assertThat(person.getGender(), is(nullValue()));
        // Transient bleibt unangetastet
        assertThat(person.getState(), is("OK"));			
        
        assertThat(storedPerson.getFirstname(), is("Mona-Lisa"));
        assertThat(storedPerson.getLastname(), is("DaVinci"));
        assertThat(storedPerson.getBirthday(), is(nullValue()));
        assertThat(storedPerson.getSalary(), is(nullValue()));
        assertThat(storedPerson.getGender(), is(nullValue()));
        
        // Transient wird initialisiert auf null, da merge ein
        // kopie des Person-Objekt erstellt (via Serialisierung)
        assertThat(storedPerson.getState(), is(nullValue()));			
    }
}
