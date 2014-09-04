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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.application.boundary.dto.Links;
import org.anderes.edu.employee.application.boundary.dto.ObjectFactory;
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
	@Inject
	private DtoMapper mapper;
	
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
	    final Employees dto = createLinksForEmployees(mapper.mapToEmployees(employees));
        return Response.ok().entity(dto).build();
	}
	
    private Response findEmployeesBySalary(final Double salary) {
        final List<Employee> employees = facade.findEmployeeBySalary(salary);
        final Employees dto = createLinksForEmployees(mapper.mapToEmployees(employees));
        return Response.ok().entity(dto).build();
    }
    
	@GET
	@Path("/{id: [0-9]+}")
	public Response findEmployee(@PathParam("id") final Long employeeId) {
		final Employee employee = facade.findOne(employeeId);
		if (employee == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		final EmployeeDto dto = mapper.mapToEmployeeDto(employee);
		dto.setLinks(createLinksForEmployee());
		return Response.ok().entity(dto).build();
	}
	
	@GET
	@Path("/{id: [0-9]+}/address")
	public Response findAddressForEmployee(@PathParam("id") final Long employeeId) {
	    final Employee employee = facade.findOne(employeeId);
        if (employee == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        return Response.ok().entity(mapper.mapToAddressDto(employee.getAddress())).build();
	}
	
	
	private String baseUrl() {
	    return uriInfo.getAbsolutePath().toString();
	}
	
	private Links createLinksForEmployee() {
	    final ObjectFactory factory = new ObjectFactory();
        final Links links = factory.createLinks();
        final Links.Link addressLink = factory.createLinksLink();
        addressLink.setRel("address");
        addressLink.setUrl(baseUrl() + "/address");
        links.getLink().add(addressLink);
        final Links.Link projectsLink = factory.createLinksLink();
        projectsLink.setRel("projects");
        projectsLink.setUrl(baseUrl() + "/projects");
        links.getLink().add(projectsLink);
        return links;
    }
	
	private Employees createLinksForEmployees(final Employees employees) {
	    for (Employees.Employee employee : employees.getEmployee()) {
            employee.setLinks(createLinksForEmployees(employee));
        }
	    return employees;
	}
	
	private Links createLinksForEmployees(final Employees.Employee employee) {
	    final ObjectFactory factory = new ObjectFactory();
        final Links links = factory.createLinks();
        final Links.Link link = factory.createLinksLink();
        link.setRel("employee");
        link.setUrl(baseUrl() + "/" + employee.getId());
        links.getLink().add(link);
        return links;
    }
}
