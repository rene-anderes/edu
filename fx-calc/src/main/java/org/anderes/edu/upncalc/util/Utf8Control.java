package org.anderes.edu.upncalc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class Utf8Control extends Control {
    
    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                    throws IllegalAccessException, InstantiationException, IOException {

        final String bundleName = toBundleName(baseName, locale);
        final String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        if (reload) {
            final URL url = loader.getResource(resourceName);
            bundle = handleUrl(url);
        } else {
            try(InputStream stream = loader.getResourceAsStream(resourceName)) {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            }
        }
        return bundle;
    }

    private ResourceBundle handleUrl(final URL url) throws IOException, UnsupportedEncodingException {
        ResourceBundle bundle = null;
        if (url != null) {
            final URLConnection connection = url.openConnection();
            if (connection != null) {
                connection.setUseCaches(false);
                try(InputStream stream = connection.getInputStream()) {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
                }
            }
        }
        return bundle;
    }
}
