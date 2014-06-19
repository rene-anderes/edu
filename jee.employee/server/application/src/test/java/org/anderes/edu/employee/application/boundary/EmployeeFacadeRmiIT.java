
package org.anderes.edu.employee.application.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.jboss.arquillian.persistence.CleanupStrategy.DEFAULT;
import static org.jboss.arquillian.persistence.TestExecutionPhase.BEFORE;
import static org.jboss.arquillian.persistence.TestExecutionPhase.NONE;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.anderes.edu.employee.application.EmployeeFacade;
import org.anderes.edu.employee.application.boundary.dto.EmployeeDto;
import org.anderes.edu.employee.domain.Employee;
import org.anderes.edu.employee.domain.logger.LoggerProducer;
import org.anderes.edu.employee.persistence.EntityManagerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class) 
@Cleanup(phase = NONE, strategy = DEFAULT)
public class EmployeeFacadeRmiIT {
    
    /** Wird der JEE-Container gewechselt, auch arquillian.xml anpassen */
    private final static boolean GLASSFISH = true;
    
    private static EmployeeFacadeRemote facadeRemote;
 
    @Deployment(name = "test")
    public static JavaArchive createDeployment() {
        return ShrinkWrap
            .create(JavaArchive.class, "test.jar")
            // Application-Layer
            .addClasses(EmployeeFacade.class, EmployeeFacadeRemote.class, EmployeeFacadeRmi.class)
            // DTO's
            .addClass(EmployeeDto.class)
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
    
    @BeforeClass
    public static void referenceRemoteFacade() throws Exception {
       if (GLASSFISH) {
           facadeRemote = lookupGlassfish();
       } else {
           facadeRemote = lookupWebLogic();
       }
       assertThat("Die Remote-Fassade wurde nicht instanziert.", facadeRemote, is(notNullValue()));
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
    public void getSalaryList() throws Exception {
        
        // given
        // Daten aus der Datenbank
        
        // when
        List<EmployeeDto> salaryList = facadeRemote.getSalaryList(Double.valueOf(45000D));
        
        // then
        assertThat(salaryList, is(not(nullValue())));
        assertThat(salaryList.size(), is(9));
        for (EmployeeDto employee : salaryList) {
            assertThat(employee.getSalary().doubleValue() > 45000D, is(true));
        }
    }
    
    private static EmployeeFacadeRemote lookupGlassfish() throws NamingException {
        final Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put(Context.URL_PKG_PREFIXES, "com.sun.enterprise.naming");
        properties.put(Context.PROVIDER_URL, "jnp://localhost:3700");
        final Context ctx = new InitialContext(properties);

        final String lookup = String.format("java:global/test/%s", EmployeeFacadeRmi.class.getSimpleName());
        return (EmployeeFacadeRemote) ctx.lookup(lookup);
    }
    
    private static EmployeeFacadeRemote lookupWebLogic() throws NamingException {

        final String url = "t3://localhost:7001";
        final Context ctx = getInitialContext(url);
        final String lookup = String.format("java:global/test/%s", EmployeeFacadeRmi.class.getSimpleName());
        try {
            Object home = ctx.lookup(lookup);
            return (EmployeeFacadeRemote) narrow(home, EmployeeFacadeRemote.class);
        } catch (NamingException ne) {
            System.out.println("The client was unable to lookup the EJBHome.  Please make sure ");
            System.out.println("that you have deployed the ejb with the JNDI name" + lookup + " on the WebLogic server at " + url);
            throw ne;
        }
    }

    private static Object narrow(Object ref, Class<?> c) {
        return PortableRemoteObject.narrow(ref, c);
    }
    
    private static Context getInitialContext(final String url) throws NamingException {
        try {
            final Properties h = new Properties();
            h.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            h.put(Context.PROVIDER_URL, url);
            return new InitialContext(h);
        } catch (NamingException ne) {
            System.out.println("We were unable to get a connection to the WebLogic server at " + url);
            System.out.println("Please make sure that the server is running.");
            throw ne;
        }
    }
}
