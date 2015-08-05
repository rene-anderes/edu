package org.anderes.edu.effecitive.equals;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.anderes.edu.effecitive.domain.Address;
import org.anderes.edu.effecitive.domain.Employee;
import org.anderes.edu.effecitive.domain.Project;
import org.junit.Test;

public class EmployeeHashCodeTest {

    @Test
    public void shouldBeCorrectHashCodeForProjects() {
        final Employee employee = new Employee();
        employee.addProject(new Project("Laboro"));
        
        assertThat(employee.getProjects().size(), is(1));
        
        employee.addProject(new Project("Laboro"));
        assertThat(employee.getProjects().size(), is(1));
        
        employee.addProject(new Project("Loganto"));
        assertThat(employee.getProjects().size(), is(2));
        
        employee.removeProject(new Project("Loganto"));
        assertThat(employee.getProjects().size(), is(1));
    }
    
    @Test
    public void shouldBeCorrectEmployee() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        
        assertThat(employee.hashCode(), is(otherEmployee.hashCode()));
    }
    
    @Test
    public void shouldBeNotCorrectEmployee() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        otherEmployee.setResponsibilities(new String[] { "Wrong Responibility" });
        
        assertThat(employee.hashCode(), is(not(otherEmployee.hashCode())));
    }
    
    private Employee createEmployee() {
        final Employee employee = new Employee();
        employee.setName("John Way");
        employee.setAge(46);
        employee.setResponsibilities(createResponsibilities());
        employee.setAddress(createAddress());
        employee.addProject(new Project("BBM"));
        return employee;
    }

    private Address createAddress() {
        final Address address = new Address();
        address.setCity("Bern");
        address.setStreet("Hauptstrasse 3");
        return address;
    }

    private String[] createResponsibilities() {
        return new String[] { "Water the office plants", "Maintain the kitchen facilities" };
    }
}
