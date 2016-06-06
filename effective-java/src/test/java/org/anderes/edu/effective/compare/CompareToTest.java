package org.anderes.edu.effective.compare;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Collection;

import org.anderes.edu.effective.domain.Address;
import org.anderes.edu.effective.domain.Employee;
import org.anderes.edu.effective.domain.Project;
import org.junit.Test;

public class CompareToTest {

    @Test
    public void shouldBeSortProjects() {
        final Employee employee = createEmployee();
        final Collection<Project> projects = employee.getProjects();
        final Collection<Project> expecteds = Arrays.asList(new Project("BBM"), new Project("Hawai"), new Project("Laboro"));
        
        assertArrayEquals(expecteds.toArray(), projects.toArray());
    }
    
    private Employee createEmployee() {
        final Employee employee = new Employee();
        employee.setName("John Way");
        employee.setAge(46);
        employee.setResponsibilities(createResponsibilities());
        employee.setAddress(createAddress());
        employee.addProject(new Project("Hawai"));
        employee.addProject(new Project("BBM"));
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
