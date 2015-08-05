package org.anderes.edu.effecitive.clone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import org.anderes.edu.effecitive.domain.Address;
import org.anderes.edu.effecitive.domain.Employee;
import org.anderes.edu.effecitive.domain.Project;
import org.junit.Test;

public class EmployeeCloneTest {

    @Test
    public void shouldBeCloneEmployee() throws CloneNotSupportedException {
        final Employee employee = createEmployee();
        
        final Employee clone = employee.clone();
        
        assertEquals("Die zwei Objekte Employee sollte mittels equals gleich sein:", employee, clone);
        assertNotSame("Die zwei Objekte Employee dürfen nicht die Selben (selbe Referenz) sein:", employee, clone);
        assertEquals("Die zwei Objekte Address sollte mittels equals gleich sein:", employee.getAddress(), clone.getAddress());
        assertNotSame("Die zwei Objekte Address dürfen nicht die Selben (selbe Referenz) sein:", employee.getAddress(), clone.getAddress());
        assertEquals("Die zwei Collections sollte mittels equals gleich sein:", employee.getProjects(), clone.getProjects());
        assertNotSame("Die zwei Collections dürfen nicht die Selben (selbe Referenz) sein:", employee.getProjects(), clone.getProjects());
        employee.getProjects().forEach(p -> {
            clone.getProjects().forEach(cloneProject -> {
                if (cloneProject.equals(p)) {
                    assertNotSame("Die zwei Objekte Project dürfen nicht die Selben (selbe Referenz) sein:", p, cloneProject);
                }
            });
        });
    }
    
    @Test
    public void shouldBeCloneEmployeeWithoutAddressAndProjects() throws CloneNotSupportedException {
        final Employee employee = new Employee();
        employee.setName("Jeff Steffensen");
        employee.setAge(39);
        
        final Employee clone = employee.clone();
        
        assertEquals("Die zwei Objekte Employee sollte mittels equals gleich sein:", employee, clone);
        assertNotSame("Die zwei Objekte Employee dürfen nicht die Selben (selbe Referenz) sein:", employee, clone);
        assertThat("Die zwei Objekte Address sollte mittels equals gleich sein:", employee.getAddress(), is(nullValue()));
        assertEquals("Die zwei Collections sollte mittels equals gleich sein:", employee.getProjects(), clone.getProjects());
        assertNotSame("Die zwei Collections dürfen nicht die Selben (selbe Referenz) sein:", employee.getProjects(), clone.getProjects());
    }
    
    private Employee createEmployee() {
        final Employee employee = new Employee();
        employee.setName("John Way");
        employee.setAge(46);
        employee.setResponsibilities(createResponsibilities());
        employee.setAddress(createAddress());
        employee.addProject(new Project("Loganto"));
        employee.addProject(new Project("Laboro"));
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
