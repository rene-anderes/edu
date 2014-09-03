package org.anderes.edu.employee.application.boundary;

import java.math.BigDecimal;
import java.util.List;

import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.application.boundary.dto.Links;
import org.anderes.edu.employee.application.boundary.dto.ObjectFactory;
import org.anderes.edu.employee.domain.Address;
import org.anderes.edu.employee.domain.Employee;
import org.apache.commons.lang3.Validate;

public class DtoMapperCopy implements DtoMapper {

    private final String baseUrl;
    
    private DtoMapperCopy(final String baserUrl) {
        super();
        this.baseUrl = baserUrl;
    }

    public static DtoMapper build(final String baserUrl) {
        Validate.notNull(baserUrl);
        return new DtoMapperCopy(baserUrl);
    }
    
    private ObjectFactory factory = new ObjectFactory();

	@Override
	public EmployeeDto mapToEmployeeDto(final Employee employee) {
		final EmployeeDto dto = factory.createEmployeeDto();
		dto.setFirstname(employee.getFirstName());
		dto.setLastname(employee.getLastName());
		dto.setSalary(new BigDecimal(employee.getSalary()));
		dto.setGender(employee.getGender().name());
		if (employee.getJobTitle() != null) {
			dto.setJobtitle(employee.getJobTitle().getTitle());
		}
		return dto;
	}

	@Override
	public Employees mapToEmployees(final List<Employee> employees) {
		final Employees list = factory.createEmployees();
		for (Employee employee : employees) {
		    Employees.Employee e = factory.createEmployeesEmployee();
			e.setFirstname(employee.getFirstName());
			e.setLastname(employee.getLastName());
			if (employee.getJobTitle() != null) {
			    e.setJobtitle(employee.getJobTitle().getTitle());
			}
			e.setLinks(createLinks(employee));
			list.getEmployee().add(e);
		}
		return list;
	}
	
	private Links createLinks(final Employee employee) {
        final Links links = factory.createLinks();
        final Links.Link link = factory.createLinksLink();
        link.setRel("employee");
        link.setUrl(baseUrl + "/" + employee.getId());
        links.getLink().add(link);
        return links;
    }

	@Override
    public AddressDto mapToAddressDto(final Address address) {
		final AddressDto addressDto = factory.createAddressDto();
		addressDto.setCity(address.getCity());
		addressDto.setCountry(address.getCountry());
		addressDto.setPostalCode(address.getPostalCode());
		addressDto.setProvince(address.getProvince());
		addressDto.setStreet(address.getStreet());
		return addressDto;
	}
}
