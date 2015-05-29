package ch.vrsg.edu.webservice.application;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;

public class Log4JContextListener implements ServletContextListener {
    
    private final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
    private final String LOG4J_CONFIGURATION_KEY = "log4jConfiguration";
    private final String LOG4J_CONFIGURATION_FILE_KEY = "log4jConfigurationFile";
    private final String LOG4J_ENVIROMENT_VARIABLE_KEY = "log4jEnvironment";
    private String configFile;

    /** only for unit-test */
    /*package*/ String getConfigFile() {
        return configFile;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();
        final URL log4jUri = getConfigFileFromInitParameter(context);
        if (log4jUri != null) {
            PropertyConfigurator.configure(log4jUri);
            configFile = log4jUri.toString();
        } else {
            configFile = getConfigFileFromEnvVariable(context);
            PropertyConfigurator.configure(configFile);
        }
    }

    private URL getConfigFileFromInitParameter(ServletContext context) {
        String log4jConfiguration = context.getInitParameter(LOG4J_CONFIGURATION_KEY);
        if (isBlank(log4jConfiguration)) {
            LogLog.debug("Kein 'context-param' '" + LOG4J_CONFIGURATION_KEY + "' angegeben.");
            return null;
        }
        try {
            return new URI(log4jConfiguration).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            LogLog.error("Der 'context-param' '" + LOG4J_CONFIGURATION_KEY + "' ist keine URI / URL.");
            return null;
        }
    }
    
    private String getConfigFileFromEnvVariable(ServletContext context) {
        final String envVariableName = context.getInitParameter(LOG4J_ENVIROMENT_VARIABLE_KEY);
        if (envVariableName == null) {
            return null;
        }
        String log4PathFromEnv = System.getenv(envVariableName);
        if (log4PathFromEnv == null) {
            return null;
        }
        String configFilename = getConfigFilenameFromInitParameter(context);
        if (configFilename == null) {
            return Paths.get(log4PathFromEnv, DEFAULT_CONFIGURATION_FILE).toString();
        }
        return Paths.get(log4PathFromEnv, configFilename).toString();
    }
    
    private String getConfigFilenameFromInitParameter(ServletContext context) {
        String log4jConfigFilename = context.getInitParameter(LOG4J_CONFIGURATION_FILE_KEY);
        if (isBlank(log4jConfigFilename)) {
            LogLog.debug("Kein 'context-param' '" + LOG4J_CONFIGURATION_FILE_KEY + "' angegeben.");
            return null;
        }
        return log4jConfigFilename;
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LogManager.shutdown();
    }

}
