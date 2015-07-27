 
package ch.vrsg.edu.webservice.application.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;


@Path("/employees")
@Stateless
public class EmployeeResource {

    @EJB    // @Inject geht wegen Bug mit JAX-RS 2.0 und WebLogic 12.1.3 nicht
    private EmployeeFacade facade;
    @Context
    private UriInfo uriInfo;
    
	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	public Response findAll() {
	    
	    final Collection<Employee> list = facade.findAll();
        final GenericEntity<Collection<Employee>> genericList = new GenericEntity<Collection<Employee>>(list) {};
        
        return Response.ok(genericList).encoding(UTF_8.name()).build();
	}
	
	@POST
	@Path("/")
	@Consumes(APPLICATION_JSON)
	public Response saveEmployee(Employee employee) {
	    Integer id = facade.save(employee);
	    UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path("employees").path(id.toString());
	    return Response.created(builder.build()).build();
	}

}