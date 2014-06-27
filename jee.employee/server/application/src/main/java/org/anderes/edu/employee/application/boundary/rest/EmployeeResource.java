package org.anderes.edu.employee.application.boundary.rest;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.domain.Employee;

@Path("/employee")
@Stateless
@TransactionAttribute(NEVER)
@Produces({APPLICATION_JSON, APPLICATION_XML})
public class EmployeeResource {
	
	@EJB
	private EmployeeFacade facade;
	@Inject
	private DtoMapper mapper;
	
	@GET
	@Path("/{id: [0-9]+}")
	public Response findEmployee(@PathParam("id") final Long employeeId) {
		final Employee employee = facade.findOne(employeeId);
		if (employee == null) {
			return Response.noContent().build();
		}
		return Response.ok().entity(mapper.mapToEmployeeDto(employee)).build();
	}
	
	@GET
	@Path("/GetEmployeesBySalary")
	public Response findEmployeesBySalary(@QueryParam("salary") final Double salary) {
		final List<Employee> employees = facade.findEmployeeBySalary(salary);
		return Response.ok().entity(mapper.mapToEmployees(employees)).build();
	}
}
