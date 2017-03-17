package org.anderes.edu.upncalc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class Utf8Control extends Control {
    
    private final class MyPrivilegedExceptionAction implements PrivilegedExceptionAction<InputStream> {
        private final boolean reloadFlag;
        private final String resourceName;
        private final ClassLoader classLoader;

        private MyPrivilegedExceptionAction(boolean reloadFlag, String resourceName, ClassLoader classLoader) {
            this.reloadFlag = reloadFlag;
            this.resourceName = resourceName;
            this.classLoader = classLoader;
        }

        @Override
        public InputStream run() throws IOException {
            InputStream is = null;
            if (reloadFlag) {
                final URL url = classLoader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        // Disable caches to get fresh data for reloading.
                        connection.setUseCaches(false);
                        is = connection.getInputStream();
                    }
                }
            } else {
                is = classLoader.getResourceAsStream(resourceName);
            }
            return is;
        }
    }


    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
        throws IllegalAccessException, InstantiationException, IOException {
        String bundleName = toBundleName(baseName, locale);
        ResourceBundle bundle = null;
        if (format.equals("java.class")) {
            bundle = handleClass(loader, bundleName, bundle);
        } else if (format.equals("java.properties")) {
            bundle = handleFile(loader, reload, bundleName, bundle);
        } else {
            throw new IllegalArgumentException("unknown format: " + format);
        }
        return bundle;
    }


    private ResourceBundle handleFile(ClassLoader loader, boolean reload, String bundleName, ResourceBundle bundle) throws IOException, UnsupportedEncodingException {
        final String resourceName = toResourceName0(bundleName, "properties");
        if (resourceName == null) {
            return bundle;
        }
        final ClassLoader classLoader = loader;
        final boolean reloadFlag = reload;
        try (InputStream stream = AccessController.doPrivileged(new MyPrivilegedExceptionAction(reloadFlag, resourceName, classLoader))) {
            if (stream != null) {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            }
        } catch (PrivilegedActionException e) {
            throw (IOException) e.getException();
        }
        return bundle;
    }


    private ResourceBundle handleClass(ClassLoader loader, String bundleName, ResourceBundle bundle) throws InstantiationException, IllegalAccessException {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends ResourceBundle> bundleClass = (Class<? extends ResourceBundle>) loader.loadClass(bundleName);

            // If the class isn't a ResourceBundle subclass, throw a ClassCastException.
            if (ResourceBundle.class.isAssignableFrom(bundleClass)) {
                bundle = bundleClass.newInstance();
            } else {
                throw new ClassCastException(bundleClass.getName() + " cannot be cast to ResourceBundle");
            }
        } catch (ClassNotFoundException e) {
            // nothing to do ...
        }
        return bundle;
    }
      

      private String toResourceName0(String bundleName, String suffix) {
          // application protocol check
          if (bundleName.contains("://")) {
              return null;
          }
          return toResourceName(bundleName, suffix);
      }
}
