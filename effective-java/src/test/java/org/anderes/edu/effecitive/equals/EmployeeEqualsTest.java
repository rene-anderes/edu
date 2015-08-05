package org.anderes.edu.effecitive.equals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import org.anderes.edu.effecitive.domain.Address;
import org.anderes.edu.effecitive.domain.Employee;
import org.anderes.edu.effecitive.domain.Project;
import org.junit.Test;

public class EmployeeEqualsTest {

    
    @Test
    public void shouldBeEquals() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        
        assertEquals("Die zwei Objekte sollte mittels equals gleich sein:", employee, otherEmployee);
        assertNotSame("Die zwei Objekte dürfen nicht die Selben (selbe Referenz) sein:", employee, otherEmployee);
    }
    
    @Test
    public void shouldBeNotEqualsAge() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        employee.setAge(64);
        
        assertNotEquals("Die zwei Objekte sollte mittels equals NICHT gleich sein:", employee, otherEmployee);
        assertNotSame("Die zwei Objekte dürfen nicht die Selben (selbe Referenz) sein:", employee, otherEmployee);
    }
    @Test
    public void shouldBeNotEqualsResponsibilities() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        employee.setResponsibilities(new String[] { "Wrong Responibility" });
        
        assertNotEquals("Die zwei Objekte sollte mittels equals NICHT gleich sein:", employee, otherEmployee);
        assertNotSame("Die zwei Objekte dürfen nicht die Selben (selbe Referenz) sein:", employee, otherEmployee);
    }
    
    @Test
    public void shouldBeNotEqualsProjects() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        employee.addProject(new Project("Laboro"));
        
        assertNotEquals("Die zwei Objekte sollte mittels equals NICHT gleich sein:", employee, otherEmployee);
        assertNotSame("Die zwei Objekte dürfen nicht die Selben (selbe Referenz) sein:", employee, otherEmployee);
    }
    
    @Test
    public void shouldBeNotEqualsAddress() {
        final Employee employee = createEmployee();
        final Employee otherEmployee = createEmployee();
        otherEmployee.getAddress().setStreet("Hauptstrasse 3");
        
        assertEquals("Die zwei Objekte sollte mittels equals gleich sein:", employee, otherEmployee);
        
        otherEmployee.getAddress().setStreet("Dorfstrasse 3");
        
        assertNotEquals("Die zwei Objekte sollte mittels equals NICHT gleich sein:", employee, otherEmployee);
        assertNotSame("Die zwei Objekte dürfen nicht die Selben (selbe Referenz) sein:", employee, otherEmployee);
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
