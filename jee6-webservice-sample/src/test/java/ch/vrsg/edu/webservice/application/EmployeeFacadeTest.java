package ch.vrsg.edu.webservice.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;
import ch.vrsg.edu.webservice.application.EmployeeNotFoundException;

public class EmployeeFacadeTest {
    
    
    @Test
    public void shouldBeOneEmployee() throws EmployeeNotFoundException {
        final EmployeeFacade facade = new EmployeeFacade();
        final Employee employee = facade.findEmployee("Leonardo", "Da Vinci");
        assertThat(employee, is(notNullValue()));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void shouldBenotFound() throws EmployeeNotFoundException {
        final EmployeeFacade facade = new EmployeeFacade();
        facade.findEmployee("Leonardo", "Di Caprio");
    }
}
