package org.anderes.edu.jpa.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CarPersistenceTest {

    private EntityManager entityManager;
    private Validator validator;
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    @Before
    public void setUp() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        entityManager = entityManagerFactory.createEntityManager();
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }

    @Test
    public void shouldBePersist() {
    	
    	// given
    	final Car newCar = new Car();
    	newCar.setColor("red").setSerialNumber(1000001);

    	// when
        entityManager.getTransaction().begin();
        entityManager.persist(newCar);
        entityManager.getTransaction().commit();
    	
        // then
        final Car storedCar = entityManager.find(Car.class, newCar.getId());
        assertThat(storedCar.getColor(), is("red"));
        assertThat(storedCar.getSerialNumber(), is(1000001));
    }
    
    @Test
    public void shouldBeNotPersist() {
        
        // given
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage(startsWith("Bean Validation constraint(s) violated"));
        
        final Car newCar = new Car();
        newCar.setColor("red");
        
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate( newCar );
        assertThat(constraintViolations, is(not(nullValue())));
        assertThat(constraintViolations.size(), is(1));

        // when
        entityManager.getTransaction().begin();
        entityManager.persist(newCar);
        entityManager.getTransaction().commit();
    }
    
}
