
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.DtoMapperCopy;
import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeesDto;
import org.anderes.edu.employee.application.boundary.dto.Link;
import org.anderes.edu.employee.application.boundary.dto.ProjectsDto;
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
	
    private static final String REST = "rest";
    private static final String EMPLOYEES = "employees";

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
    	final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).path("70");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();
    	
    	// then
    	assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    	assertThat(response.hasEntity(), is(true));
    	final EmployeeDto employee = response.readEntity(EmployeeDto.class);
    	assertThat(employee, is(notNullValue()));
    	assertThat(employee.getFirstname(), is("John"));
    	assertThat(employee.getLastname(), is("Way"));
    	assertThat(employee.getJobtitle(), is("Manager"));
    	assertThat(employee.getSalary(), is(BigDecimal.valueOf(53005)));
    	assertThat(employee.getGender(), is("Male"));
    	assertThat(employee.getLink().size(), is(2));
    	Link link = employee.getLink().get(0);
    	assertThat(link.getRel(), is("address"));
    	assertThat(link.getUrl(), is(getResourcesPathAsString(deploymentUrl) + employee.getId() + "/address"));
    	link = employee.getLink().get(1);
        assertThat(link.getRel(), is("projects"));
        assertThat(link.getUrl(), is(getResourcesPathAsString(deploymentUrl) + employee.getId() + "/projects"));
    }
    
    @Test
    @InSequence(3)
    @RunAsClient
    public void shouldBeFindAddress(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).path("70").path("/address");
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();
        
        // then
        assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final AddressDto address = response.readEntity(AddressDto.class);
        assertThat(address, is(notNullValue()));
        assertThat(address.getCity(), is("Perth"));
        assertThat(address.getCountry(), is("Canada"));
        assertThat(address.getStreet(), is("234 Caledonia Lane"));
        assertThat(address.getProvince(), is("ONT"));
    }
    
    @Test
    @InSequence(4)
    @RunAsClient
    public void shouldBeFindProjects(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).path("70").path("/projects");
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();
        
        // then
        assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final ProjectsDto projectsDto = response.readEntity(ProjectsDto.class);
        assertThat(projectsDto.getProject().size(), is(3));
    }
    
    @Test
    @InSequence(5)
    @RunAsClient
    public void shouldBeNotFindOne(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).path("1007");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }
    
    @Test
    @InSequence(6)
    @RunAsClient
    public void shouldBeWrongUrl(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).path("A70");
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }
    
    @Test
    @InSequence(7)
    @RunAsClient
    public void shouldBeFindBySalary(@ArquillianResource URL deploymentUrl) throws Exception {
    	// given
    	final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl).queryParam("salary", 45000D);
    	final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
    	
    	// when
    	final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

    	// then
    	assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
    	assertThat(response.hasEntity(), is(true));
		final EmployeesDto employees = response.readEntity(EmployeesDto.class);
    	assertThat(employees.getEmployee().size(), is(9));
    	for (EmployeesDto.Employee employee : employees.getEmployee()) {
            assertThat(employee.getLink().size(), is(1));
            final Link link = employee.getLink().get(0);
            assertThat(link.getRel(), is("employee"));
            assertThat(link.getUrl().matches(getResourcesPathAsString(deploymentUrl) + "[0-9]{2,2}"), is(true));
        }
    }

    private String getResourcesPathAsString(URL deploymentUrl) throws MalformedURLException {
        final UriBuilder expectedUri = createUriFromDeploymentPath(deploymentUrl);
        return expectedUri.build().toURL().toString() + "/";
    }
    
    @Test
    @InSequence(8)
    @RunAsClient
    public void shouldBeFindEmployees(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl);
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();

        // then
        assertThat(response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        final EmployeesDto employees = response.readEntity(EmployeesDto.class);
        assertThat(employees.getEmployee().size(), is(12));
        for (EmployeesDto.Employee employee : employees.getEmployee()) {
            assertThat(employee.getLink().size(), is(1));
            final Link link = employee.getLink().get(0);
            assertThat(link.getRel(), is("employee"));
            assertThat(link.getUrl().matches(getResourcesPathAsString(deploymentUrl) + "[0-9]{2,2}"), is(true));
        }
    }
    
    private UriBuilder createUriFromDeploymentPath(final URL deploymentUrl) {
    	return UriBuilder.fromPath(getResourcePath(deploymentUrl).toString()).
  	                scheme(deploymentUrl.getProtocol()).host(deploymentUrl.getHost()).port(deploymentUrl.getPort());
    }
    
    private URI getResourcePath(final URL deploymentUrl) {
        return UriBuilder.fromPath(deploymentUrl.getPath()).path(REST).path(EMPLOYEES).build();
        
    }
}
