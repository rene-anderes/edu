package org.anderes.edu.effective.immutability;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.anderes.edu.effective.domain.Address;
import org.anderes.edu.effective.domain.Employee;
import org.anderes.edu.effective.domain.Project;
import org.anderes.edu.effective.immutability.AddressBase;
import org.anderes.edu.effective.immutability.ImmutableAddress;
import org.anderes.edu.effective.immutability.ImmutableEmployee;
import org.anderes.edu.effective.immutability.ImmutableProject;
import org.anderes.edu.effective.immutability.ProjectBase;
import org.junit.Before;
import org.junit.Test;

public class EmployeeImmutableTest {
    
    private ImmutableEmployee immutableEmployee;
    private Employee employee;
    
    @Before
    public void setup() {
        employee = createEmployee();
        immutableEmployee = new ImmutableEmployee(employee);
    }
    
    @Test
    public void shouldBeImmutable() {
        
        assertThat(immutableEmployee.getAddress(), is(notNullValue()));
        assertThat(immutableEmployee.getAddress(), is(instanceOf(ImmutableAddress.class)));
        assertThat(immutableEmployee.getAddress(), is(instanceOf(AddressBase.class)));
        immutableEmployee.getProjects().forEach(p -> {
            assertThat(p, is(instanceOf(ImmutableProject.class)));
            assertThat(p, is(instanceOf(ProjectBase.class)));
        });
        assertThat(immutableEmployee.getAge(), is(46));
        assertThat(immutableEmployee.getName(), is("John Way"));
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
