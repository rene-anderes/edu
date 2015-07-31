package org.anderes.edu.beanvalidation.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {

    private Validator validator;
    
    @Before
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @Test
    public void shouldBeValidateEmployee() {
        
        // given
        final Employee employee = createEmployee();

        // when
        final Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

        // then
        assertThat(constraintViolations.size(), is(0));
    }
    
    @Test
    public void shouldBeConstraintsViolations() {
        
        // given
        final Employee employee = createEmployeeWithWrongData();

        // when
        final Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

        // then
        assertThat(constraintViolations.size(), is(5));
    }

    private Employee createEmployeeWithWrongData() {
        final Employee employee = new Employee();
        employee.setFirstname("P");
        employee.setLastname("S");
        employee.setGender("unknown");
        employee.setJobtitle("");
        employee.setSalary(BigDecimal.valueOf(0d));
        return employee;
    }

    private Employee createEmployee() {
        final Employee employee = new Employee();
        employee.setFirstname("Peter");
        employee.setLastname("Steffensen");
        employee.setGender("Male");
        employee.setJobtitle("Developer");
        employee.setSalary(BigDecimal.valueOf(56000D));
        return employee;
    }
}
