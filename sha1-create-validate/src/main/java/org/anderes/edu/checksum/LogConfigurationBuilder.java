package org.anderes.edu.checksum;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.logging.log4j.Level.*;

import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public abstract class LogConfigurationBuilder {

    public static void init(Path applicationLogPath, Path errorLogPath) {
        checkNotNull(applicationLogPath);
        checkNotNull(errorLogPath);

        final ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        
        // Pattern-Layout -> Console
        final LayoutComponentBuilder consolePattern = builder.newLayout("PatternLayout");
        consolePattern.addAttribute("pattern", "%d{DATE} %-5level : %msg%n");
        
        // Pattern-Layout -> Application-File
        final LayoutComponentBuilder applicationFilePattern = builder.newLayout("PatternLayout");
        applicationFilePattern.addAttribute("pattern", "%d{DATE} %-5level %logger{36} - %msg%n");
        
        // Pattern-Layout -> Error-File
        final LayoutComponentBuilder errorFilePattern = builder.newLayout("PatternLayout");
        errorFilePattern.addAttribute("pattern", "%d{DATE} - %msg%n");
        
        // Console-Appender
        final AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.add(consolePattern);
        builder.add(console);
        
        // Application-File Appender
        final AppenderComponentBuilder applicationFile = builder.newAppender("ApplicationFile", "File");
        applicationFile.addAttribute("fileName", applicationLogPath.toString());
        applicationFile.add(applicationFilePattern);
        builder.add(applicationFile);
        
        // Error-File Appender
        final AppenderComponentBuilder errorFile = builder.newAppender("ErrorFile", "File");
        errorFile.addAttribute("fileName", errorLogPath.toString());
        errorFile.add(errorFilePattern);
        builder.add(errorFile);
        
        // Root Logger
        final RootLoggerComponentBuilder rootLogger = builder.newRootLogger(INFO);
        rootLogger.add(builder.newAppenderRef("stdout"));
        rootLogger.add(builder.newAppenderRef("ApplicationFile"));
        builder.add(rootLogger);
        
        // Additional Loggers
        final LoggerComponentBuilder logger = builder.newLogger("FileWithError", INFO);
        logger.add(builder.newAppenderRef("ErrorFile"));
        logger.addAttribute("additivity", false);
        builder.add(logger);
     
        Configurator.initialize(builder.build());
    }

}
