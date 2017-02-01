package org.anderes.edu.upncalc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.jar.Manifest;

public class SystemInfoService {

    private final static String MANIFEST = "/META-INF/MANIFEST.MF";
    
    public SystemInfoService() {
        super();
    }

    public String getImplementationVersion() {
        try (InputStream inputStream = this.getClass().getResourceAsStream(MANIFEST)) {
            Manifest manifest = new Manifest(inputStream);
            return manifest.getMainAttributes().getValue("Implementation-Version");
        } catch (IOException e) {
           throw new UncheckedIOException(e);
        }
    }
    
    public String getJREVersion() {
        return System.getProperty("java.version");
    }

}
