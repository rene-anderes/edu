package org.anderes.edu.employee.application.boundary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.domain.Address;
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
		if (employee.getAddress() != null) {
			dto.setAddressDto(mapToAddressDto(employee.getAddress()));
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
	public List<EmployeeDto> mapToEmployeeDtoCollection(final List<Employee> employees) {
		final List<EmployeeDto> list = new ArrayList<>(employees.size());
		for (Employee employee : employees) {
			list.add(mapToEmployeeDto(employee));
		}
		return list;
	}

	private AddressDto mapToAddressDto(final Address address) {
		final AddressDto addressDto = new AddressDto();
		addressDto.setCity(address.getCity());
		addressDto.setCountry(address.getCountry());
		addressDto.setPostalCode(address.getPostalCode());
		addressDto.setProvince(address.getProvince());
		addressDto.setStreet(address.getStreet());
		return addressDto;
	}
}
