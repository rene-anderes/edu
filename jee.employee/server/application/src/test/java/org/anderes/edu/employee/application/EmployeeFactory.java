package org.anderes.edu.employee.application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.anderes.edu.employee.domain.Address;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.Gender;
import org.anderes.edu.employee.domain.JobTitle;
import org.anderes.edu.employee.domain.LargeProject;
import org.anderes.edu.employee.domain.PhoneNumber;
import org.anderes.edu.employee.domain.Project;
import org.anderes.edu.employee.domain.SmallProject;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.*;

import org.apache.commons.lang3.reflect.FieldUtils;

public abstract class EmployeeFactory {
    
    private final static Random RANDOM = new Random();
    
    public static Employee createEmployeeWithId70() {
        final Employee employee = new Employee();
        setId(employee, 70L);
        employee.setFirstName("John");
        employee.setLastName("Way");
        employee.setJobTitle(new JobTitle("Manager"));
        employee.setSalary(53005D);
        employee.setGender(Gender.Male);
        
        final PhoneNumber phoneCellular = new PhoneNumber("Cellular", "416", "9871133");
        final PhoneNumber phoneHome = new PhoneNumber("Home", "613", "1234455");
        employee.addPhoneNumber(phoneHome);
        employee.addPhoneNumber(phoneCellular);
        
        final Address address = new Address();
        setId(address, 38L);
        address.setCity("Perth");
        address.setCountry("Canada");
        address.setPostalCode("Y3Q2N9");
        address.setProvince("ONT");
        address.setStreet("234 Caledonia Lane");
        employee.setAddress(address);
        
        final Project smallProject = new SmallProject("Small House", "Small Project");
        setId(smallProject, 22L);
        final Project largeProject = new LargeProject();
        setId(largeProject, 33L);
        largeProject.setName("Large House");
        largeProject.setDescription("Large Project");
        employee.addProject(smallProject);
        employee.addProject(largeProject);
        
        return employee;
    }

    public static <T extends Object> T setId(T object, final Long id) {
        try {
            FieldUtils.writeField(object, "id", Long.valueOf(id), true);
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }
        return object;
    }

    public static Employee createEmployeeForSave() {
        final Employee employee = new Employee();
        employee.setFirstName("Peter");
        employee.setLastName("Steffensen");
        employee.setJobTitle(new JobTitle("Developer"));
        employee.setSalary(56000D);
        employee.setGender(Gender.Male);
        return employee;
    }

    public static Employee createSavedEmployee() {
        final Employee employee = createEmployeeForSave();
        setId(employee, 1007L);
        return employee;
    }
    public static List<Employee> createRandomEmployeeCollection(int count) {
        ArrayList<Employee> employees = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            employees.add(createRandomEmployee());
        }
        return employees;
    }
    
    public static Employee createRandomEmployee() {
        final Employee employee = new Employee();
        setId(employee, nextLong(10, 100));
        employee.setFirstName(randomAlphabetic(15));
        employee.setLastName(randomAlphabetic(15));
        employee.setJobTitle(new JobTitle(randomAlphabetic(15)));
        employee.setSalary(nextDouble(45000, 150000));
        employee.setGender(getRandomGender());
        employee.addPhoneNumber(createRandomPhoneNumber());
        employee.addPhoneNumber(createRandomPhoneNumber());
        employee.setAddress(createRandomAddress());
        return employee;
    }

    public static Address createRandomAddress() {
        Address address = new Address();
        setId(address, nextLong(1, 1000));
        address.setCity(randomAscii(7));
        address.setCountry(randomAscii(2));
        address.setPostalCode(Integer.toString(nextInt(1000, 10000)));
        address.setProvince(randomAscii(3));
        address.setStreet(randomAlphabetic(15));
        return address;
    }
    
    public static PhoneNumber createRandomPhoneNumber() {
        return new PhoneNumber(randomAscii(5), Integer.toString(nextInt(100, 1000)), Integer.toString(nextInt(1000000, 10000000)));
    }
    
    public static Gender getRandomGender() {
        if (RANDOM.nextBoolean()) {
            return Gender.Male;
        } else {
            return Gender.Female;
        }
    }
}
