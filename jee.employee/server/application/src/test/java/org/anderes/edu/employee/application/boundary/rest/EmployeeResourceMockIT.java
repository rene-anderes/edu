
package org.anderes.edu.employee.application.boundary.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.employee.application.DevMock;
import org.anderes.edu.employee.application.DevMocks;
import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.EmployeeFactory;
import org.anderes.edu.employee.application.LargeProjectFacade;
import org.anderes.edu.employee.application.SmallProjectFacade;
import org.anderes.edu.employee.application.boundary.DtoMapper;
import org.anderes.edu.employee.application.boundary.DtoMapperCopy;
import org.anderes.edu.employee.application.boundary.dto.AddressDto;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.application.boundary.dto.Link;
import org.anderes.edu.employee.application.boundary.dto.ObjectFactory;
import org.anderes.edu.employee.application.boundary.dto.ProjectDto;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Dieser Test zeitg auf wie man Arqillian und Mockito kombinieren kann.<br>
 * In diesem Beispiel wird das Data-Repository (welches mittels {@code @Inject} eingebunden wird)
 * durch eine Mock-Klasse ersetzt werden kann.
 * <p/>
 * Da hier {@code @RunAsClient} verwendet wird, kann das Mock-Objekt hier nicht injected und konfiguriert werden.
 * Dies übernimmt die Klasse {@code DevMocks}.
 * 
 * @author René Anderes
 *
 */
@RunWith(Arquillian.class) 
public class EmployeeResourceMockIT {
	
    private static final String REST = "rest";
    private static final String EMPLOYEES = "employees";
    
    @Deployment(name = "test")
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml"); 
        
        /* Hier wird der Stereotyp als Alternative eim beans.xml eingetragen */
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class)
                        .addDefaultNamespaces().beanDiscoveryMode("all")
                        .getOrCreateAlternatives().stereotype(DevMock.class.getName()).up();
        return ShrinkWrap
            .create(WebArchive.class)
            // REST Boundery
            .addPackages(true, EmployeeApplication.class.getPackage())
            // Application-Layer
            .addClasses(EmployeeFacade.class, LargeProjectFacade.class, SmallProjectFacade.class)
            // DTO's
            .addPackage(EmployeeDto.class.getPackage())
            .addClasses(DtoMapper.class, DtoMapperCopy.class)
            // Domain-Layer-Klassen
            .addPackage(Employee.class.getPackage())
            // Logger Producer
            .addClass(LoggerProducer.class)
            // Testabhängigkeiten
            .addClasses(DevMock.class, DevMocks.class, EmployeeFactory.class)
            // Resourcen
            .addAsWebInfResource(new StringAsset(beansXml.exportAsString()), beansXml.getDescriptorName())
            .addAsLibraries(pom.resolve("org.mockito:mockito-core").withTransitivity().asFile())
            .addAsResource(new File("target/classes/META-INF/validation.xml"), "META-INF/validation.xml")
            .addAsResource(new File("target/classes/META-INF/validation/constraints-dto.xml"), "META-INF/validation/constraints-dto.xml");
            // workaround für Bug https://java.net/jira/browse/GLASSFISH-21141
//            .addAsLibraries(pom.resolve("com.fasterxml.jackson.module:jackson-module-jaxb-annotations").withTransitivity().asFile());
            
    }
    
    @Test
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
        assertThat(address.getPostalCode(), is("Y3Q2N9"));
    }
    
    @Test
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
        final List<ProjectDto> projectsDto = response.readEntity(new GenericType<List<ProjectDto>>() {});
        assertThat(projectsDto.size(), is(2));
        projectsDto.forEach(project -> {
            assertThat(project.getDescription(), is(notNullValue()));
            assertThat(project.getName(), is(notNullValue()));
            assertThat(project.getId(), is(notNullValue()));
        });
    }
    
    @Test
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
    	final List<EmployeeDto> employees = response.readEntity(new GenericType<List<EmployeeDto>>() {});
    	assertThat(employees.size(), is(9));
    	for (EmployeeDto employee : employees) {
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
        final List<EmployeeDto> employees =  response.readEntity(new GenericType<List<EmployeeDto>>() {});
        assertThat(employees.size(), is(12));
        for (EmployeeDto employee : employees) {
            assertThat(employee.getLink().size(), is(1));
            final Link link = employee.getLink().get(0);
            assertThat(link.getRel(), is("employee"));
            assertThat(link.getUrl().matches(getResourcesPathAsString(deploymentUrl) + "[0-9]{2,2}"), is(true));
        }
    }
    
    @Test
    @RunAsClient
    public void shouldBeStoreNewEmployee(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final EmployeeDto employeeDto = createNewEmployeeDto();
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl);
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request().buildPost(Entity.entity(employeeDto, APPLICATION_JSON_TYPE)).invoke();
        
        // then
        assertThat(response.getStatus(), is(Status.CREATED.getStatusCode()));
        assertThat(response.getHeaders().get("Location"), is(notNullValue()));
        assertThat(response.getHeaders().get("Location").size(), is(1));
        assertThat(response.getHeaders().get("Location").get(0), is(getResourcesPathAsString(deploymentUrl) + "1007"));
    }
    
    @Test
    @RunAsClient
    public void shouldBeNotStoreWithWrongData(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final EmployeeDto employeeDto = createNewEmployeeDto();
        employeeDto.setGender("unknown");
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl);
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request().buildPost(Entity.entity(employeeDto, APPLICATION_JSON_TYPE)).invoke();
        
        // then
        assertThat(response.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
    }
    
    @Test
    @RunAsClient
    public void shouldBeNotStoreEmptyData(@ArquillianResource URL deploymentUrl) throws Exception {
        // given
        final EmployeeDto employeeDto = new EmployeeDto();
        final UriBuilder uri = createUriFromDeploymentPath(deploymentUrl);
        final WebTarget target = ClientBuilder.newClient().target(uri).register(JacksonFeature.class);
        
        // when
        final Response response = target.request().buildPost(Entity.entity(employeeDto, APPLICATION_JSON_TYPE)).invoke();
        
        // then
        assertThat(response.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
    }
        
    private EmployeeDto createNewEmployeeDto() {
        final ObjectFactory factory = new ObjectFactory();
        final EmployeeDto dto = factory.createEmployeeDto();
        dto.setFirstname("Peter");
        dto.setLastname("Steffensen");
        dto.setGender("Male");
        dto.setSalary(BigDecimal.valueOf(56000D));
        dto.setJobtitle("Developer");
        return dto;
    }

    private UriBuilder createUriFromDeploymentPath(final URL deploymentUrl) {
    	return UriBuilder.fromPath(getResourcePath(deploymentUrl).toString()).
  	                scheme(deploymentUrl.getProtocol()).host(deploymentUrl.getHost()).port(deploymentUrl.getPort());
    }
    
    private URI getResourcePath(final URL deploymentUrl) {
        return UriBuilder.fromPath(deploymentUrl.getPath()).path(REST).path(EMPLOYEES).build();
        
    }
}
