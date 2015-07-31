package org.anderes.edu.employee.application.boundary;

import java.util.List;

import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.ProjectDto;
import org.anderes.edu.employee.domain.Address;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.Project;

public interface DtoMapper {
	
	EmployeeDto mapToEmployeeDto(final Employee employee);
	
	List<EmployeeDto> mapToEmployeesDto(final List<Employee> employees);
	
    AddressDto mapToAddressDto(final Address address);
    
    List<ProjectDto> mapToProjectsDto(final List<Project> projects);

    Employee mapToEmployee(EmployeeDto employeeDto);

}
