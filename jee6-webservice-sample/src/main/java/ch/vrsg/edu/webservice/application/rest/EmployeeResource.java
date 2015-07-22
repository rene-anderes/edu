 
package ch.vrsg.edu.webservice.application.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import ch.vrsg.edu.webservice.application.Employee;
import ch.vrsg.edu.webservice.application.EmployeeFacade;


@Path("/employees")
public class EmployeeResource {

    @Inject
    private EmployeeFacade facade;
    
	@GET
	@Path("/")
	@Produces(APPLICATION_JSON)
	public Response findAll() {
	    
	    final Collection<Employee> list = facade.findAll();
        final GenericEntity<Collection<Employee>> genericList = new GenericEntity<Collection<Employee>>(list) {};
        
        return Response.ok(genericList).encoding(UTF_8.name()).build();
	}

}