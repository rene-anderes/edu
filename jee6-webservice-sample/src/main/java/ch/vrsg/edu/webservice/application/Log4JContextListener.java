package ch.vrsg.edu.webservice.application;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.LogLog;

public class Log4JContextListener implements ServletContextListener {
    
    private final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
    private final String LOG4J_INIT_FILE_KEY = "log4j-init-file";
    private final String LOG4J_INIT_FILENAME_KEY = "log4j-filename";
    private final String LOG4J_ENVIROMENT_VARIABLE_KEY = "log4j-enviroment";
    private String configFile;

    /*package*/ String getConfigFile() {
        return configFile;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();
        configFile = getConfigFileFromInitParameter(context);
        if (configFile == null) {
            configFile = getConfigFileFromEnvVariable(context);
        }
        if (configFile != null) {
            PropertyConfigurator.configure(configFile);
        }
        
    }

    private String getConfigFileFromInitParameter(ServletContext context) {
        String log4jInitFile = context.getInitParameter(LOG4J_INIT_FILE_KEY);
        if (isBlank(log4jInitFile)) {
            LogLog.debug("Kein 'context-param' '" + LOG4J_INIT_FILE_KEY + "' angegeben.");
            return null;
        }
        return log4jInitFile;
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
        String log4jConfigFilename = context.getInitParameter(LOG4J_INIT_FILENAME_KEY);
        if (isBlank(log4jConfigFilename)) {
            LogLog.debug("Kein 'context-param' '" + LOG4J_INIT_FILENAME_KEY + "' angegeben.");
            return null;
        }
        return log4jConfigFilename;
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LogManager.shutdown();
    }

}
