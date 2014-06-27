
package org.anderes.edu.employee.application.boundary.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.DtoMapperCopy;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Employees;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class) 
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeResourceIT {
	
    @Deployment(name = "test")
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Application-Layer
            .addClasses(EmployeeFacade.class, EmployeeApplication.class, EmployeeResource.class)
            // DTO's
            .addPackage(EmployeeDto.class.getPackage())
            .addClasses(DtoMapper.class, DtoMapperCopy.class)
            // Domain-Layer-Klassen
            .addPackage(Employee.class.getPackage())
            // EntityManager-Producer
            .addClass(EntityManagerProducer.class)
            // Logger Producer
            .addClass(LoggerProducer.class)
            // Resourcen
            .addAsManifestResource(new File("target/test-classes/META-INF/derby-persistence.xml"), "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
        
    @Test
    @InSequence(1)
    @CleanupUsingScript(value = "delete_all_rows.sql", phase = BEFORE)
    @UsingDataSet(value = { 
        "prepare-address.json", "prepare-employee.json", "prepare-degree.json",
        "prepare-email.json", "prepare-jobtitle.json", "prepare-emp_job.json",
        "prepare-project.json", "prepare-lproject.json", "prepare-phone.json",
        "prepare-proj_emp.json", "prepare-response.json", "prepare-salary.json"
        })
    public void setupDatabase() {
        // Datenbank mit Testdaten füllen
    }
    
    @Test
    @InSequence(2)
    @RunAsClient
    public void shouldBeFindOne(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUri(deploymentUrl).path("70");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();
    	
    	// then
    	assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    	assertThat(response.hasEntity(), is(true));
    	final EmployeeDto employee = response.readEntity(EmployeeDto.class);
    	assertThat(employee, is(notNullValue()));
    	assertThat(employee.getFirstname(), is("John"));
    	assertThat(employee.getLastname(), is("Way"));
    	assertThat(employee.getJobtitle(), is("Manager"));
    	assertThat(employee.getSalary(), is(BigDecimal.valueOf(53005)));
    }
    
    @Test
    @InSequence(2)
    @RunAsClient
    public void shouldBeNotFindOne(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUri(deploymentUrl).path("1007");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    	assertThat(response.hasEntity(), is(false));
    }
    
    @Test
    @InSequence(3)
    @RunAsClient
    public void shouldBeWrongUrl(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUri(deploymentUrl).path("A70");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
    }
    
    @Test
    @InSequence(3)
    @RunAsClient
    public void shouldBeFindBySalary(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUri(deploymentUrl).path("GetEmployeesBySalary").queryParam("salary", 45000D);
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    	assertThat(response.hasEntity(), is(true));
		final Employees employees = response.readEntity(Employees.class);
    	assertThat(employees.getEmployeeDto().size(), is(9));
    	for (EmployeeDto employee : employees.getEmployeeDto()) {
            assertThat(employee.getSalary().doubleValue() > 45000D, is(true));
        }
    }
    
    private UriBuilder createUri(final URL deploymentUrl) {
    	final UriBuilder uri = UriBuilder.fromPath(deploymentUrl.getPath()).scheme(deploymentUrl.getProtocol())
    			.host(deploymentUrl.getHost()).port(deploymentUrl.getPort())
    			.path("rest").path("employee");
    	return uri;
    }
}
