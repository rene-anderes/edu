package ch.vrsg.edu.webservice.application;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Log4JContextListenerTest {

    private final Path testfiles = Paths.get("src", "test", "files");
    private Log4JContextListener listener;
    private ServletContextEvent mockEvent;
    private ServletContext mockContext;
    
    @Before
    public void setup() {
        listener = new Log4JContextListener();
        mockEvent = mock(ServletContextEvent.class);
        mockContext = mock(ServletContext.class);
        when(mockEvent.getServletContext()).thenReturn(mockContext = mock(ServletContext.class));
    }
    
    @After
    public void tearDown() {
        listener.contextDestroyed(mockEvent);
        File toDelete = new File("example.log");
        if (toDelete.exists()) {
            assertThat(toDelete.delete(), is(true));
        }
        System.setProperty("log4j.configuration", "");
    }
    
    @Test
    public void shouldBeFindConfigInitFileAsUrl() throws Exception {
        final String expectedFile = testfiles.resolve("log4j_test1.properties").toUri().toURL().toString();
       
        when(mockContext.getInitParameter("log4jConfiguration")).thenReturn(expectedFile);
        
        listener.contextInitialized(mockEvent);
        
        assertThat(listener.getConfigFile(), is(expectedFile));
    }
    
    @Test
    public void shouldBeFindConfigInitFileAsUri() throws Exception {
        final URI expectedUri = testfiles.resolve("log4j_test1.properties").toUri();
       
        when(mockContext.getInitParameter("log4jConfiguration")).thenReturn(expectedUri.toString());
        
        listener.contextInitialized(mockEvent);
        
        assertThat(listener.getConfigFile(), is(expectedUri.toURL().toString()));
    }
    
    @Test
    public void shouldBeFindEnvVariable() {
        final String expectedFile = testfiles.resolve("log4j.properties").toAbsolutePath().toString();
        
        when(mockContext.getInitParameter("log4jEnvironment")).thenReturn("VRSG_HOME");
        
        listener.contextInitialized(mockEvent);
        
        assertThat("Umgebungsvariable VRSG_HOME nicht gesetzt?", listener.getConfigFile(), is(expectedFile));
    }
    
    @Test
    public void shouldBeFindEnvVariableWithFilename() {
        final String expectedFile = testfiles.resolve("log4j_test1.properties").toAbsolutePath().toString();
        
        when(mockContext.getInitParameter("log4jConfigurationFile")).thenReturn("log4j_test1.properties");
        when(mockContext.getInitParameter("log4jEnvironment")).thenReturn("VRSG_HOME");
        
        listener.contextInitialized(mockEvent);
        
        assertThat("Umgebungsvariable VRSG_HOME nicht gesetzt?", listener.getConfigFile(), is(expectedFile));
    }
    
    @Test
    public void shouldBeFindEnvVariableWithFileInSubDirectory() {
        final String expectedFile = testfiles.resolve("AppName/log4j_test2.properties").toAbsolutePath().toString();
        
        when(mockContext.getInitParameter("log4jConfigurationFile")).thenReturn("AppName/log4j_test2.properties");
        when(mockContext.getInitParameter("log4jEnvironment")).thenReturn("VRSG_HOME");
        
        listener.contextInitialized(mockEvent);
        
        assertThat("Umgebungsvariable VRSG_HOME nicht gesetzt?", listener.getConfigFile(), is(expectedFile));
    }
}
