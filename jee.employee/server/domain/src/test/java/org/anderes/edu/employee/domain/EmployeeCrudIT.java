package org.anderes.edu.employee.domain;

import static org.anderes.edu.employee.domain.Gender.Female;
import static org.hamcrest.CoreMatchers.*;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDate;

import javax.inject.Inject;

import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeCrudIT {

    @Inject
    private EmployeeRepository repository;

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
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    @ShouldMatchDataSet(value = {
        // Die veränderten Tabellen
        "expected-address-afterInsert.json", "expected-employee-afterInsert.json", "expected-salary-afterInsert.json",
        // Die unveränderten Tabellen
        "prepare-degree.json", "prepare-email.json", "prepare-emp_job.json",
        "prepare-jobtitle.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-project.json", "prepare-response.json" },
        excludeColumns = { "ADDRESS.ADDRESS_ID", "EMPLOYEE.ADDR_ID", "EMPLOYEE.EMP_ID", "SALARY.EMP_ID" },
        orderBy = { "ADDRESS.CITY", "SALARY.SALARY" })
    public void shoullBeSaveNewEmployee() {
        
        // when
        final Employee employee = repository.save(createNewEmployee());
        
        // then
        final LocalDate expectedDate = LocalDate.of(2014, 5, 1);
        assertThat(employee.getPeriod().getStartDate(), is(expectedDate));
        assertThat(employee.getPeriod().getEndDate(), is(LocalDate.MAX));
        assertThat(employee.getParkingSpace().isPresent(), is(false));
        
    }

    private Employee createNewEmployee() {
        final Employee employee = new Employee();
        employee.setFirstName("Petra");
        employee.setLastName("Schneider");
        employee.setGender(Female);
        employee.setAddress(createNewAddress());
        employee.setSalary(56900D);
        employee.getPeriod().setStartDate(2014, 5, 1);
        return employee;
    }

    private Address createNewAddress() {
        final Address address = new Address();
        address.setCity("Avignon");
        address.setCountry("France");
        address.setStreet("11 rue de la république");
        return address;
    }

    @Test
    @Transactional(value = TransactionMode.COMMIT)
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    @ShouldMatchDataSet(value = {
        // Die veränderten Tabellen
        "expected-address-afterDelete.json", "expected-employee-afterDelete.json", "expected-salary-afterDelete.json",
        "expected-emp_job-afterDelete.json", "expected-proj_emp-afterDelete.json", "expected-response-afterDelete.json",
        // Die unveränderten Tabellen
        "prepare-degree.json", "prepare-email.json", 
        "prepare-jobtitle.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-project.json" })
    public void shouldBeDeleteEmployee() {
        // given
        final Employee employee = repository.findOne(50l);
        
        // when
        repository.delete(employee);
        
        // then
        // Mittels @ShouldMatchDataSet wird überprüft, ob in der Tabelle auch die richtige Werte stehen
    }
    
    @Test
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    @ShouldMatchDataSet(value = { 
        // Die veränderten Tabellen
        "expected-salary-afterModify.json", "expected-employee-afterModify.json", 
        // Die unveränderten Tabellen
        "prepare-address.json", "prepare-degree.json", "prepare-email.json", "prepare-emp_job.json",
        "prepare-jobtitle.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-project.json", "prepare-response.json" },
        orderBy = { "EMPLOYEE.EMP_ID", "SALARY.EMP_ID" })
    public void shouldBeSalaryModify() {
        // given
        final Employee existsEmployee = repository.findOne(Long.valueOf(70));
        assertThat("Wurde die Datenbank nicht richtig gefüllt?!", existsEmployee, is(not(nullValue())));
        existsEmployee.setSalary(Double.valueOf(55005D));

        // when
        final Employee savedEmployees = repository.save(existsEmployee);

        // then
        assertThat("Der Rückgabewert darf  nicht null sein", savedEmployees, is(not(nullValue())));

        // Mittels @ShouldMatchDataSet wird überprüft, ob in der Tabelle auch die richtige Werte stehen
    }
    
    @Test
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    public void shouldBeFindOne() {

        // when
        final Employee employee = repository.findOne(Long.valueOf(70));

        // then
        assertThat("Mitarbeiter nicht gefunden.", employee, is(not(nullValue())));
        assertThat("Mitarbeiter existiert nicht", repository.exists(Long.valueOf(70)), is(true));
    }
}
