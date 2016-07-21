package org.anderes.edu.jpa.execute_around_methode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.anderes.edu.jpa.basicattributes.PersonBasic;
import org.anderes.edu.jpa.basicattributes.PersonBasic.Gender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepositoryUtilsTest {

    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
    }

    @After
    public void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    @Test
    public void shouldBePersonCollectionWithoutTransaction() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final JpaFunction<List<PersonBasic>> function = 
                        () -> entityManager.createQuery("SELECT p FROM PersonBasic p WHERE p.lastname LIKE :lastname", PersonBasic.class)
                        .setParameter("lastname", "%Gates%").getResultList();

        final RepositoryUtils<List<PersonBasic>> repositoryUtils = new RepositoryUtils<>();
        final List<PersonBasic> results = repositoryUtils.withoutTransaction(entityManager, function);

        assertThat(results, is(notNullValue()));
        assertThat(results.size(), is(1));
    }

    @Test
    public void shouldBeSaveNewPersonWithTransaction() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();

        final PersonBasic entity = createPersonBasic();
        final JpaFunction<PersonBasic> function = () -> {
            entityManager.persist(entity);
            return entity;
        };
        
        final RepositoryUtils<PersonBasic> repositoryUtils = new RepositoryUtils<PersonBasic>();
        final PersonBasic savedPerson = repositoryUtils.withTransaction(entityManager, function);

        assertThat(savedPerson, is(notNullValue()));
        assertThat(savedPerson.getId(), is(notNullValue()));
        assertThat(savedPerson.getId() > 0, is(true));
    }
    
    @Test
    public void shouldBeDeletePersonWithTransaction() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final VoidJpaFunction<PersonBasic> function = () -> {
            Optional<PersonBasic> entity = Optional.ofNullable(entityManager.find(PersonBasic.class, Long.valueOf(99999)));
            entityManager.remove(entity.orElseThrow(() -> new IllegalArgumentException("Der Datensatz existiert nicht.")));
        };
        
        final RepositoryUtils<PersonBasic> repositoryUtils = new RepositoryUtils<PersonBasic>();
        repositoryUtils.withTransaction(entityManager, function);
    }

    private PersonBasic createPersonBasic() {
        final PersonBasic melinda = new PersonBasic("Melinda", "Gates");
        melinda.setGender(Gender.FEMALE);
        melinda.setSalary(BigDecimal.valueOf(50000D));
        melinda.setBirthday(august(15, 1964));
        return melinda;
    }

    private Calendar august(int dayOfMonth, int year) {
        final Calendar birthday = Calendar.getInstance();
        birthday.set(year, Calendar.AUGUST, dayOfMonth);
        return birthday;
    }
}
