package org.anderes.edu.upncalc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.jar.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemInfoService {

    private final static String MANIFEST = "/META-INF/MANIFEST.MF";
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    
    public SystemInfoService() {
        super();
    }

    public String getImplementationVersion() {
        logger.debug("read manifest-file: " + this.getClass().getResource(MANIFEST));
        try (InputStream inputStream = this.getClass().getResourceAsStream(MANIFEST)) {
            Manifest manifest = new Manifest(inputStream);
            return manifest.getMainAttributes().getValue("Implementation-Version");
        } catch (IOException e) {
            logger.debug(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
    
    public String getJREVersion() {
        return System.getProperty("java.version");
    }

}
