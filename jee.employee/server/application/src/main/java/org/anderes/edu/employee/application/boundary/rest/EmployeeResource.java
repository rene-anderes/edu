package org.anderes.edu.employee.application.boundary.rest;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.domain.Employee;

@Path("/employee")
@Stateless
@TransactionAttribute(NEVER)
public class EmployeeResource {
	
	@EJB
	private EmployeeFacade facade;
	@Inject
	private DtoMapper mapper;
	
	@GET
	@Path("/{id: [0-9]+}")
	@Produces({APPLICATION_JSON, APPLICATION_XML})
	public EmployeeDto findEmployee(@PathParam("id") final Long employeeId) {
		final Employee employee = facade.findOne(employeeId);
		if (employee == null) {
			return new EmployeeDto();
		}
		return mapper.mapToEmployeeDto(employee);
	}
}
