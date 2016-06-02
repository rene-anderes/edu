package ch.vrsg.edu.webservice.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;
import ch.vrsg.edu.webservice.application.EmployeeNotFoundException;

public class EmployeeFacadeTest {
    
    private EmployeeFacade facade;
    
    @Before
    public void setup() {
        facade = new EmployeeFacade();
        facade.setLogger(Logger.getLogger(EmployeeFacadeTest.class.getName()));
    }
    
    @Test
    public void shouldBeOneEmployee() throws EmployeeNotFoundException {
        final Employee employee = facade.findEmployee("Leonardo", "Da Vinci");
        assertThat(employee, is(notNullValue()));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void shouldBenotFound() throws EmployeeNotFoundException {
        facade.findEmployee("Leonardo", "Di Caprio");
    }
    
    @Test
    public void shouldBeFindAll() {
        final Collection<Employee> employees = facade.findAll();
        assertThat(employees, is(notNullValue()));
        assertThat(employees.size(), is(3));
    }
}
