package org.anderes.edu.employee.application.boundary.rest;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.DtoMapperCopy;
import org.anderes.edu.employee.domain.Employee;

@Path("/employees")
@Stateless
@TransactionAttribute(NEVER)
@Produces({APPLICATION_JSON, APPLICATION_XML})
public class EmployeeResource {
	
	@EJB
	private EmployeeFacade facade;
	@Context
	private UriInfo uriInfo;
	
	@GET
    @Path("/")
    public Response findEmployees(@QueryParam("salary") final Double salary) {
        if (salary == null || salary.equals(Double.valueOf(0D))) {
            return findEmployees();
        } 
        return findEmployeesBySalary(salary);
    }
	
    private Response findEmployees() {
	    final List<Employee> employees = facade.findEmployees();
        final DtoMapper mapper = DtoMapperCopy.build(baseUrl());
        return Response.ok().entity(mapper.mapToEmployees(employees)).build();
	}
	
    private Response findEmployeesBySalary(final Double salary) {
        final List<Employee> employees = facade.findEmployeeBySalary(salary);
        final DtoMapper mapper = DtoMapperCopy.build(baseUrl());
        return Response.ok().entity(mapper.mapToEmployees(employees)).build();
    }
    
	@GET
	@Path("/{id: [0-9]+}")
	public Response findEmployee(@PathParam("id") final Long employeeId) {
		final Employee employee = facade.findOne(employeeId);
		if (employee == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		final DtoMapper mapper = DtoMapperCopy.build(baseUrl());
		return Response.ok().entity(mapper.mapToEmployeeDto(employee)).build();
	}
	
	private String baseUrl() {
	    return uriInfo.getAbsolutePath().toString();
	}
}
