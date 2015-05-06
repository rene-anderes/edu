package ch.vrsg.edu.webservice.application;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
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
    public void shouldBeFindConfigInitFile() {
        final String expectedFile = testfiles.resolve("log4j_test1.properties").toAbsolutePath().toString();
       
        when(mockContext.getInitParameter("log4j-init-file")).thenReturn(expectedFile);
        
        listener.contextInitialized(mockEvent);
        
        assertThat(listener.getConfigFile(), is(expectedFile));
    }
    
    @Test
    public void shouldBeFindEnvVariable() {
        final String expectedFile = testfiles.resolve("log4j.properties").toAbsolutePath().toString();
        
        when(mockContext.getInitParameter("log4j-enviroment")).thenReturn("VRSG_HOME");
        
        listener.contextInitialized(mockEvent);
        
        assertThat("Umgebungsvariable VRSG_HOME nicht gesetzt?", listener.getConfigFile(), is(expectedFile));
    }
    
    @Test
    public void shouldBeFindEnvVariableWithFilename() {
        final String expectedFile = testfiles.resolve("log4j_test1.properties").toAbsolutePath().toString();
        
        when(mockContext.getInitParameter("log4j-filename")).thenReturn("log4j_test1.properties");
        when(mockContext.getInitParameter("log4j-enviroment")).thenReturn("VRSG_HOME");
        
        listener.contextInitialized(mockEvent);
        
        assertThat("Umgebungsvariable VRSG_HOME nicht gesetzt?", listener.getConfigFile(), is(expectedFile));
    }
}
