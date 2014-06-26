package org.anderes.edu.employee.application.boundary;

import java.util.List;

import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.domain.Employee;

public interface DtoMapper {
	
	public EmployeeDto mapToEmployeeDto(final Employee employee);
	
	public Employees mapToEmployees(final List<Employee> employees);
	
	public List<EmployeeDto> mapToEmployeeCollection(final List<Employee> employees);

}
