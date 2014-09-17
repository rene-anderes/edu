package org.anderes.edu.jee.rest.sample;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.anderes.edu.jee.rest.sample.dto.Employee;
import org.anderes.edu.jee.rest.sample.dto.Employees;
import org.apache.commons.lang3.RandomStringUtils;

@Path("/employees")
public class EmployeeResource {

	
	@GET
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public Employees getAllEmployees() {
		final Employees employees = new Employees();
		final int max = Integer.parseInt(RandomStringUtils.randomNumeric(3));
		for (int i = 0; i < max; i++) {
			employees.getEmployee().add(createRndDummyEmployee());
		}
		return employees;
	}
	
	
	private Employee createRndDummyEmployee() {
		final Employee employee = new Employee();
		employee.setTitle("Herr");
		employee.setFirstname(RandomStringUtils.randomAlphabetic(50));
		employee.setLastname(RandomStringUtils.randomAlphabetic(50));
		final String persNo = String.format("%s-%s-%s", RandomStringUtils.randomNumeric(2), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomNumeric(1));
		employee.setPersonnelNo(persNo);
		return employee;
	}
}
