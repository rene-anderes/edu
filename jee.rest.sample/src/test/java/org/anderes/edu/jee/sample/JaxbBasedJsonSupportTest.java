package org.anderes.edu.jee.sample;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import static javax.ws.rs.core.MediaType.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anderes.edu.jee.rest.sample.JaxbBasedJsonSupport;
import org.anderes.edu.jee.rest.sample.jaxbdto.Employee;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

public class JaxbBasedJsonSupportTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(JaxbBasedJsonSupport.class);
    }
    
    @Test
    public void shouldBeFindAll() {
        final Response response = target("employees").request(APPLICATION_JSON_TYPE).buildGet().invoke();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final List<Employee> employees = response.readEntity(new GenericType<List<Employee>>() {});
        assertThat(employees, is(notNullValue()));
        assertThat(employees.size() > 99, is(true));
        assertThat(employees.size() < 1000, is(true));
    }
    
    @Test
    public void shouldBeFindOne() {
        final Response response = target("employees/1002").request(APPLICATION_JSON_TYPE).buildGet().invoke();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final Employee employee = response.readEntity(Employee.class);
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getTitle(), is("Herr"));
    }
    
    @Test
    public void shouldBeFindOneXml() {
        final Response response = target("employees/1002").request(APPLICATION_XML_TYPE).buildGet().invoke();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final Employee employee = response.readEntity(Employee.class);
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getTitle(), is("Herr"));
    }
    
    @Test
    public void testApplicationWadl() {
        final WebTarget target = target();
        final String applicationWadl = target.path("application.wadl").request().get(String.class);
        assertTrue("Something wrong. Returned wadl length is not > 0", applicationWadl.length() > 0);
    }
}
