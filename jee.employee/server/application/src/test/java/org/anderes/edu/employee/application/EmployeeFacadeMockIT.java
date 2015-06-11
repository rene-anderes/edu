package org.anderes.edu.employee.application;

import static org.anderes.edu.employee.domain.Gender.Male;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.EmployeeRepository;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
 * Dieser Test zeigt auf wie man Arqillian und Mockito kombinieren kann.<br>
 * In diesem Beispiel wird das Data-Repository (welches mittels {@code @Inject} eingebunden wird)
 * durch eine Mock-Klasse ersetzt werden kann.
 * 
 * @author René Anderes
 *
 */
@RunWith(Arquillian.class) 
public class EmployeeFacadeMockIT {

    /** Fassade die getestet werden soll */
    @EJB
    private EmployeeFacade facade;
    
    /** Das Mock-Objekt wird für das Aufzeichnung des Verhaltens benötigt */
    @Inject
    private EmployeeRepository mockEmployeeRepository;
    
    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml"); 
        
        /* Hier wird der Stereotyp als Alternative im beans.xml eingetragen */
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class)
                        .addDefaultNamespaces().beanDiscoveryMode("all")
                        .createAlternatives().stereotype(DevMock.class.getName()).up();
        return ShrinkWrap
            .create(WebArchive.class)
            // Application-Layer
            .addClasses(EmployeeFacade.class)
            // Domain-Layer-Klassen
            .addPackage(Employee.class.getPackage())
            // Logger Producer
            .addClass(LoggerProducer.class)
            // Testabhängigkeiten
            .addClasses(DevMock.class, DevMocks.class, EmployeeFactory.class)
            // Resourcen
            .addAsWebInfResource(new StringAsset(beansXml.exportAsString()), beansXml.getDescriptorName())
            .addAsLibraries(pom.resolve("org.mockito:mockito-core").withTransitivity().asFile());
    }
       
    @Test
    public void shouldBeFindOneEmployee() {
        Long personId = Long.valueOf(70);
        Employee value = EmployeeFactory.createEmployeeWithId70();
        when(mockEmployeeRepository.findOneEmployeeAddress(personId)).thenReturn(value);

        
        final Optional<Employee> optional = facade.findOne(personId);
        assertThat(optional, is(notNullValue()));
        assertThat(optional.isPresent(), is(true));
        final Employee employee = optional.get();
        assertThat(employee, is(notNullValue()));
        assertThat(employee.getPhoneNumbers(), is(notNullValue()));
        assertThat(employee.getPhoneNumbers().size(), is(2));
        assertThat(employee.getFirstName(), is("John"));
        assertThat(employee.getLastName(), is("Way"));
        assertThat(employee.getJobTitle().isPresent(), is(true));
        assertThat(employee.getJobTitle().get().getTitle(), is("Manager"));
        assertThat(employee.getSalary(), is(53005D));
        assertThat(employee.getGender(), is(Male));
        
        verify(mockEmployeeRepository, atLeastOnce());
    }

}
