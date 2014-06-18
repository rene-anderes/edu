package org.anderes.edu.employee.domain;

import static org.anderes.edu.employee.domain.Gender.Female;
import static org.anderes.edu.employee.domain.Gender.Male;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.EmployeeRepository;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeCriteriaQueryIT {

    @Inject
    private EmployeeRepository repository;
    @Inject
    private Logger logger;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Domain-Klassen
            .addPackage(Employee.class.getPackage())
            // EntityManager-Producer
            .addClass(EntityManagerProducer.class)
            // Logger Producer
            .addClass(LoggerProducer.class)
            // Resourcen
            .addAsManifestResource(new File("target/test-classes/META-INF/derby-persistence.xml"), "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Test
    @InSequence(1)
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    public void fillDatabaseForSequence() {
        /* Test-Daten in die Datenbank abfüllen */
    }
 
    @Test
    @InSequence(1)
    public void shouldBeFindOne() {
        
        // when
        final Employee employee = repository.findOneUsingCriteriaQuery(70L);
        
        // then
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getFirstName(), is("John"));
        assertThat(employee.getLastName(), is("Way"));
    }
    
    @Test
    @InSequence(2)
    public void shouldBeFindByGender() {

        // when
        List<Employee> employees = repository.findEmployeeByGender(Male);

        // then
        assertThat("Die Liste der Mitarbeiter darf nicht null sein.", employees, is(not(nullValue())));
        assertThat(employees.size(), is(7));

        // when
        employees = repository.findEmployeeByGender(Female);

        // then
        assertThat("Die Liste der Mitarbeiter darf nicht null sein.", employees, is(not(nullValue())));
        assertThat(employees.size(), is(5));
    }
    
    @Test
    @InSequence(3)
    public void shouldBeFindByGenderAndPhoneType() {

        // when
        List<Employee> employees = repository.findEmployeeByGenderAndPhoneType(Male, "Home");

        // then
        assertThat("Die Liste der Mitarbeiter darf nicht null sein.", employees, is(not(nullValue())));
        assertThat(employees.size(), is(1));
    }
    
    @Test
    @InSequence(4)
    public void shouldBeMaxSalary() {
        
        // when
        final Double value = repository.findMaxSalary();
        
        // then
        assertThat(value, is(500001D));
    }
    
    @Test
    @InSequence(5)
    public void shouldBeAllFirstnames() {
        
        // when
        final List<String> firstnames = repository.findAllFirstname();
        
        // then
        assertThat(firstnames.size(), is(12));
    }
    
    @Test
    @InSequence(6)
    public void shouldBeAllNames() {
        
        // when
        final List<Object[]> names = repository.getAllNames();
        
        // then
        assertThat(names, is(notNullValue()));
        assertThat(names.size(), is(12));
        assertThat(names.iterator().next().length, is(2));
    }
    
    @Test
    @InSequence(7)
    public void shouldBeGroupedAvarageSalaries() {
        
        // when
        final List<Object[]> salaries = repository.getAvarageSalaries();
        
        // then 
        assertThat(salaries, is(notNullValue()));
        assertThat(salaries.size(), is(12));
        final Object[] data = salaries.iterator().next();
        assertThat(data.length, is(2));
        assertThat((Double)data[0], is(31000.0));
        assertThat((String)data[1], is("Metcalfe"));

        // dump result to stdout
        for (Iterator<Object[]> iter = salaries.iterator(); iter.hasNext();){
            final Object[] values = iter.next();
            final String log = String.format("City: %s - avarage salaries: %s", values[1], values[0]);
            logger.info(log);
        }
    }
}
