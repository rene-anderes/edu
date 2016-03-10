package org.anderes.edu.jee.rest.sample;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.anderes.edu.jee.rest.sample.jaxbdto.Employee;

/**
 * REST-Service für Employee
 * <p>
 *  JAXB based JSON support: JAXB-Bean ist die Basis für das Data-Transfer-Object<br>
 *  In diesem Fall wird das Employee Data-Transfer-Object wird mittels XSD und JAXB generiert.
 * <p>
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#json">Jersey JSON support</a><br>
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#json.moxy">MOXy is a default and preferred way of supporting JSON binding</a> 
 * 
 * @author René Anderes
 *
 */
@Path("employees")
public class JaxbBasedJsonSupport {

	@GET
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public Response getAllEmployees() {
		final Collection<Employee> employees = createEmployeeCollection();
		final GenericEntity<Collection<Employee>> genericList = new GenericEntity<Collection<Employee>>(employees) {};
		return Response.ok(genericList).encoding(UTF_8.name()).build();
	}
	
	@GET
	@Path("{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public Response findOne(@PathParam("id") String id) { 
	    final Employee employee = createRndDummyEmployee();
	    return Response.ok(employee).encoding(UTF_8.name()).build();
	}
	
	private Collection<Employee> createEmployeeCollection() {
	    final Collection<Employee> employees = new ArrayList<>();
        final int max = Integer.parseInt(randomNumeric(3));
        for (int i = 0; i < max; i++) {
            employees.add(createRndDummyEmployee());
        }
        return employees;
	}
	
	private Employee createRndDummyEmployee() {
		final Employee employee = new Employee();
		employee.setTitle("Herr");
		employee.setFirstname(randomAlphabetic(50));
		employee.setLastname(randomAlphabetic(50));
		final String persNo = String.format("%s-%s-%s", randomNumeric(2), randomNumeric(4), randomNumeric(1));
		employee.setPersonnelNo(persNo);
		return employee;
	}
	
}
