package org.anderes.edu.employee.application.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.DtoMapperCopy;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.ObjectFactory;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.Gender;
import org.junit.Test;

public class DtoMapperTest {

    private DtoMapper mapper = new DtoMapperCopy();
    
    @Test
    public void shouldBeMapEmployeeDto() {
        // given
        EmployeeDto dto = createNewEmployeeDto();
        // when
        Employee employee = mapper.mapToEmployee(dto);
        // then
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getFirstName(), is("Peter"));
        assertThat(employee.getLastName(), is("Steffensen"));
        assertThat(employee.getGender(), is(Gender.Male));
        assertThat(employee.getJobTitle().isPresent(), is(true));
        assertThat(employee.getJobTitle().get().getTitle(), is("Developer"));
        assertThat(employee.getSalary(), is(56000D));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldBeWrongEmployeeDto() {
        // given
        EmployeeDto dto = createNewEmployeeDto();
        dto.setGender("unknown");
        // when
        mapper.mapToEmployee(dto);
        // then Exception
    }
    
    private EmployeeDto createNewEmployeeDto() {
        final ObjectFactory factory = new ObjectFactory();
        final EmployeeDto dto = factory.createEmployeeDto();
        dto.setFirstname("Peter");
        dto.setLastname("Steffensen");
        dto.setGender("Male");
        dto.setSalary(BigDecimal.valueOf(56000D));
        dto.setJobtitle("Developer");
        return dto;
    }
}
