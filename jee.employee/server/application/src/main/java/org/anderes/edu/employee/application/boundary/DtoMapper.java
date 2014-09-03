package org.anderes.edu.employee.application.boundary;

import java.util.List;

import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.domain.Address;
import org.anderes.edu.employee.domain.Employee;

public interface DtoMapper {
	
	EmployeeDto mapToEmployeeDto(final Employee employee);
	
	Employees mapToEmployees(final List<Employee> employees);
	
    AddressDto mapToAddressDto(final Address address);

}
