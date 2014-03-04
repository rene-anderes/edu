package org.anderes.edu.relations.onetomany;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneToManyTest {

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
    	// given
        final Person storedPerson = addPersonToDatabase();
        final Company storedCompany = addCompanyToDatabase();
        savePersonToCompanyAsEmployee(storedCompany, storedPerson);
        
        // when
        final Collection<Person> persons = getAllPersons();
        
        // then
        assertThat(persons, is(not(nullValue())));
        assertThat(persons.size(), is(1));
        final Person person = persons.iterator().next();
        assertThat(person.getCompany(), is(not(nullValue())));
        
        // when
        final Collection<Person> personsFromCompany = getAllPersonsFromCompany(storedCompany);
        
        // then
        assertThat(personsFromCompany, is(not(nullValue())));
        assertThat(personsFromCompany.size(), is(1));
    }
    
    private Collection<Person> getAllPersons() {
        final TypedQuery<Person> query = entityManager.createQuery("Select p From Person p", Person.class);
        return query.getResultList();
    }
    
    private Collection<Person> getAllPersonsFromCompany(final Company company) {
    	final Company storedCompany = entityManager.find(Company.class, company.getId());
    	return storedCompany.getEmployees();
    }

    private void savePersonToCompanyAsEmployee(final Company company, final Person person) {
    	entityManager.getTransaction().begin();
    	final Person storedPerson = entityManager.find(Person.class, person.getId());
    	final Company storedCompany = entityManager.find(Company.class, company.getId());
    	storedCompany.addEmployee(storedPerson);
    	entityManager.merge(storedPerson);
    	entityManager.getTransaction().commit();
    }
    
    private Person addPersonToDatabase() {
        final Person person = new Person("Mona-Lisa", "DaVinci");
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        return person;
    }
    
    private Company addCompanyToDatabase() {
    	final Company company = new Company();
    	entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.getTransaction().commit();
        return company;
    }

}
