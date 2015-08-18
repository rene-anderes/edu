package org.anderes.edu.relations.onetomany;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    public void shouldBeMergePersonWithCompany() {
    	final Person person = new Person("Peter", "Lustig");
    	final Company company = new Company("Intel");
    	person.setCompany(company);
    	company.addEmployee(person);
    	
    	entityManager.getTransaction().begin();
    	final Person savedPerson = entityManager.merge(person);
    	entityManager.getTransaction().commit();
    	
    	assertThat(savedPerson.getId(), is(notNullValue()));
    	assertThat(savedPerson.getCompany(), is(notNullValue()));
    	assertThat(savedPerson.getCompany().getId(), is(notNullValue()));
    	assertThat(savedPerson.getCompany().getEmployees().size(), is(1));
    }
    
    @Test
    public void shouldBePersistPersonWithCompany() {
    	final Person person = new Person("Peter", "Lustig");
    	final Company company = new Company("Intel");
    	person.setCompany(company);
    	company.addEmployee(person);
    	
    	entityManager.getTransaction().begin();
    	entityManager.persist(person);
    	entityManager.getTransaction().commit();
    	
    	assertThat(person.getId(), is(notNullValue()));
    	assertThat(person.getCompany(), is(notNullValue()));
    	assertThat(person.getCompany().getId(), is(notNullValue()));
    	assertThat(person.getCompany().getEmployees().size(), is(1));
    }
    
    @Test
    public void shouldBeMergeCompanyWithoutPerson() {
    	final Person person = new Person("Peter", "Lustig");
    	final Company company = new Company("Intel");
    	person.setCompany(company);
    	company.addEmployee(person);
    	
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
}
