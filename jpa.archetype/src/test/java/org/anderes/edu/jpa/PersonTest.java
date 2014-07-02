package org.anderes.edu.jpa;

import static java.util.Calendar.JANUARY;
import static org.anderes.edu.jpa.Person.Gender.FEMALE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private EntityManager entityManager;
    
    @Before
    public void setUp() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testDB");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }
    
    @Test
    public void simpleTest() {
        addPersonToDatabase();
        
        final Collection<Person> persons = getAllPersons();
        
        assertThat(persons, is(not(nullValue())));
        assertThat(persons.size(), is(1));
    }
    
    private Collection<Person> getAllPersons() {
        final TypedQuery<Person> query = entityManager.createQuery("Select e From Person e", Person.class);
        return query.getResultList();
    }

    private void addPersonToDatabase() {
    	final Person person = new Person("Mona-Lisa", "DaVinci");
    	person.setBirthday(birthday());
    	person.setSalary(BigDecimal.valueOf(45000D));
    	person.setGender(FEMALE);

    	// when
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    private Calendar birthday() {
    	final Calendar birthday = Calendar.getInstance();
    	birthday.clear();
    	birthday.set(1973, JANUARY, 1);
    	return birthday;
    }
}
