package org.anderes.edu.checksum;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.anderes.edu.checksum.LogConfigurationBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class LogConfigurationBuilderTest {
    
    
    @Test
    public void shouldBeCorrectLogging() {
        // given
        final Path applicationLogPath = Paths.get("target", "application.log");
        final Path errorLogPath = Paths.get("target", "error.log");
        LogConfigurationBuilder.init(applicationLogPath, errorLogPath);
        
        // when
        final Logger logger = LogManager.getLogger();
        logger.info("Test-Message 'application'");
        final Logger loggerFile = LogManager.getLogger("FileWithError");
        loggerFile.info("Test-Message 'error'");
        
        // then
        final File applicationLogFile = applicationLogPath.toFile();
        assertThat(applicationLogFile.exists(), is(true));
        final File errorLogFile = errorLogPath.toFile();
        assertThat(errorLogFile.exists(), is(true));
    }

}
