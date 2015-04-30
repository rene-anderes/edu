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
    private final String VRSG_HOME_KEY = "VRSG_HOME";
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
        String vrsgHome = System.getenv(VRSG_HOME_KEY);
        if (vrsgHome == null) {
            return null;
        }
        String configFilename = getConfigFilenameFromInitParameter(context);
        if (configFilename == null) {
            return Paths.get(vrsgHome, DEFAULT_CONFIGURATION_FILE).toString();
        }
        return Paths.get(vrsgHome, configFilename).toString();
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
