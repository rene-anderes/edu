package org.anderes.edu.employee.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Optional;

import javax.ejb.EJB;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.domain.Address;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.Gender;
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
public class EmployeeFacadeIT {

    @EJB
    private EmployeeFacade facade;
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Application-Layer
            .addClass(EmployeeFacade.class)
            // Domain-Layer-Klassen
            .addPackage(Employee.class.getPackage())
            // EntityManager Producer vor Small- and LargeProjectRepository
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
    @InSequence(2)
    public void shouldBeFindOneEmployee() {

        final Optional<Employee> optional = facade.findOne(59L);
        assertThat(optional, is(notNullValue()));
        assertThat(optional.isPresent(), is(true));
        final Employee employee = optional.get();
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getFirstName(), is("Jim-Bob"));
        assertThat(employee.getLastName(), is("Jefferson"));
        assertThat(employee.getGender(), is(Gender.Male));
        assertThat(employee.getJobTitle(), is(notNullValue()));
        assertThat(employee.getJobTitle().isPresent(), is(true));
        assertThat(employee.getJobTitle().get().getTitle(), is("VP"));
        assertThat(employee.getPhoneNumbers(), is(notNullValue()));
        assertThat(employee.getPhoneNumbers().size(), is(2));
        assertThat(employee.getAddress(), is(notNullValue()));
        assertThat(employee.getAddress().isPresent(), is(true));
        final Address address = employee.getAddress().get();
        assertThat(address.getCity(), is("Montreal"));
        assertThat(address.getCountry(), is("Canada"));
        assertThat(address.getPostalCode(), is("Q2S5Z5"));
        assertThat(address.getProvince(), is("QUE"));
        assertThat(address.getStreet(), is("1 Canadien Place"));
    }

}
