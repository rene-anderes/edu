package org.anderes.edu.employee.application.boundary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.domain.Employee;

@Singleton
public class DtoMapperCopy implements DtoMapper {

	@Override
	public EmployeeDto mapToEmployeeDto(final Employee employee) {
		final EmployeeDto dto = new EmployeeDto();
		dto.setFirstname(employee.getFirstName());
		dto.setLastname(employee.getLastName());
		dto.setSalary(new BigDecimal(employee.getSalary()));
		if (employee.getJobTitle() != null) {
			dto.setJobtitle(employee.getJobTitle().getTitle());
		}
		return dto;
	}

	@Override
	public Employees mapToEmployees(final List<Employee> employees) {
		final Employees list = new Employees();
		for (Employee employee : employees) {
			list.getEmployeeDto().add(mapToEmployeeDto(employee));
		}
		return list;
	}

	@Override
	public List<EmployeeDto> mapToEmployeeCollection(final List<Employee> employees) {
		final List<EmployeeDto> list = new ArrayList<>(employees.size());
		for (Employee employee : employees) {
			list.add(mapToEmployeeDto(employee));
		}
		return list;
	}

}
