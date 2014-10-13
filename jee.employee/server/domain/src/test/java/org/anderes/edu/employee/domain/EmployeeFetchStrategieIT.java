package org.anderes.edu.employee.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.apache.commons.lang3.SerializationUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeFetchStrategieIT {

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
    public void shouldBeFindOneEmployeeAddress() {
        
        // when
        final Employee employee = repository.findOneEmployeeAddress(70L);
        
        // then
        assertEmployee(getDetachedEmployee(employee));
    }
    
    @Test
    @InSequence(3)
    public void shouldBeFindOneEmployeeAddressJpql() {
        
        // when
        final Employee employee = repository.findOneEmployeeAddressJpql(70L);
        
        // then
        assertEmployee(getDetachedEmployee(employee));
    }

    @Ignore("Unter EclipseLink funktioniert 'javax.persistence.loadgraph' (noch) nicht richtig!")
    @Test
    @InSequence(5)
    public void shouldBeFindOneEmployeeAddressLoadGraph() {
    	
    	// when
    	final Employee employee = repository.findOneEmployeeAddressLoadGraph(70L);
    	
        // then
    	assertEmployee(getDetachedEmployee(employee));
    }

    @Ignore("Läuft zur Zeit nur, wenn via Glassfish Remote getestet wird")
    @Test
    @InSequence(5)
    public void shouldBeFindOneEmployeeAddressFetchGraph() {
    	
    	// when
    	final Employee employee = repository.findOneEmployeeAddressFetchGraph(70L);
    	
        // then
    	assertEmployee(getDetachedEmployee(employee));
    }
    
    @Ignore("Läuft zur Zeit nur, wenn via Glassfish Remote getestet wird")
    /**
     * Der Versuch auf ein nicht aufgelösten Objekt-Graphen zuzugreifen.
     */
    @Test(expected = IllegalStateException.class)
    @InSequence(6)
    public void shouldBeIllegalStateException() {
    	
    	// when
    	final Employee employee = repository.findOneEmployeeAddressFetchGraph(70L);
    	final Employee employeeClone = getDetachedEmployee(employee);
    	
        // then
    	assertThat(employeeClone, is(notNullValue()));
        assertThat(employeeClone.getManagedEmployees().size(), is(0));
    }
    
    @Test
    @InSequence(7)
    public void shouldBeFindOneEmployeeAddressBatchFetching() {
        
        // when
        final Employee employee = repository.findOneEmployeeAddressLoadGroup(70L);
        
        // then
        assertEmployee(getDetachedEmployee(employee));
    }
    
    @Test
    @InSequence(8)
    public void shouldBeFindEmployeesBySalary() {
    	
    	// when
        List<Employee> salaryList = repository.findEmployeeBySalaryFetchJobtitle(45000D);
        
        // then
        assertThat(salaryList, is(not(nullValue())));
        assertThat(salaryList.size(), is(9));
        for (Employee employee : salaryList) {
        	final Employee detachedEmployee = getDetachedEmployee(employee);
            assertThat(detachedEmployee.getSalary().doubleValue() > 45000D, is(true));
            if (detachedEmployee.getJobTitle().isPresent()) {
            	assertThat(detachedEmployee.getJobTitle().get().getTitle(), is(notNullValue()));
            }
        }
    }
    
    @Ignore("Läuft zur Zeit nur, wenn via Glassfish Remote getestet wird")
    @Test
    @InSequence(8)
    public void shouldBeFindEmployees() {
        
        // when
        List<Employee> employees = repository.findEmployees();
        
        // then
        assertThat(employees, is(not(nullValue())));
        assertThat(employees.size(), is(12));
        for (Employee employee : employees) {
            final Employee detachedEmployee = getDetachedEmployee(employee);
            assertThat(isNoneEmpty(detachedEmployee.getFirstName()), is(true));
            assertThat(isNoneEmpty(detachedEmployee.getLastName()), is(true));
            if (detachedEmployee.getJobTitle().isPresent()) {
                assertThat(detachedEmployee.getJobTitle().get().getTitle(), is(notNullValue()));
            }
        }
    }
    
    /**
     * Die Entität aus dem EntityManager "detachen" via Serialisation
     */
    private Employee getDetachedEmployee(final Employee employee) {
    	return SerializationUtils.clone(employee);
    }
    
    private void assertEmployee(final Employee employee) {
    	assertThat(employee, is(notNullValue()));
        assertThat(employee.getFirstName(), is("John"));
        assertThat(employee.getLastName(), is("Way"));
        assertThat(employee.getAddress().isPresent(), is(true));
        assertThat(employee.getAddress().get().getProvince(), is("ONT"));
        assertThat(employee.getJobTitle().isPresent(), is(true));
        assertThat(employee.getJobTitle().get().getTitle(), is("Manager"));
    }
}
