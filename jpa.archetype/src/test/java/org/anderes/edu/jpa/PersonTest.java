package org.anderes.edu.jpa;

import static java.util.Calendar.JANUARY;
import static org.anderes.edu.jpa.Person.Gender.FEMALE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    
    @BeforeClass
    public static void setUpOnce() {
        // Der Name der Persistence-Unit entspricht der in der Konfigurationsdatei META-INF/persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("derbyPU");
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
    
    /**
     * Person mittels Datenbankidentität finden.
     */
    @Test
    public void shouldBeFindOnePerson() {
        
        // given
        final Person person = addPersonToDatabase();
        final Long id = person.getId();
        
        // when
        final Person findPerson = entityManager.find(Person.class, id);
        
        // then
        assertThat(findPerson, is(not(nullValue())));
        assertThat(findPerson.getFirstname(), is("Mona-Lisa"));
    }
    
    /**
     * Besipiel einet JPQL-Query
     */
    @Test
    public void shouldBeFindAllPersons() {
        
        // given
        final Person person = addPersonToDatabase();
        
        // when
        final Collection<Person> allPersons = entityManager.createQuery("Select e From Person e", Person.class).getResultList();
        
        // then
        assertThat(entityManager.contains(person), is(true));   // Das Personen-Objekt liegt im Persistence-Context
        assertThat(allPersons, is(not(nullValue())));
        assertThat(allPersons.size(), is(not(0)));
    }
    
    private Person addPersonToDatabase() {
    	final Person person = new Person("Mona-Lisa", "DaVinci");
    	person.setBirthday(january(1, 1973));
    	person.setSalary(BigDecimal.valueOf(45000D));
    	person.setGender(FEMALE);

        entityManager.getTransaction().begin();     // Transaktion gestartet
        entityManager.persist(person);              // Status der Entity auf 'managed'
        entityManager.getTransaction().commit();    // Transaktion beendet
        
        return person;
    }

    private LocalDate january(int day, int year) {
    	final Calendar birthday = Calendar.getInstance();
    	birthday.clear();
    	birthday.set(year, JANUARY, day);
    	return LocalDate.of(year, Month.JANUARY, day);
    }
}
