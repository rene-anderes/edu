package org.anderes.appengine.edu.echo;


import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.logging.LoggingFeature;

/**
 * Jersey Async Webapp application class.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
@ApplicationPath("/")
public class AsyncJaxrsApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final HashSet<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(LongRunningEchoResource.class);

        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final HashSet<Object> instances = new HashSet<Object>();

        instances.add(new LoggingFeature(Logger.getLogger(AsyncJaxrsApplication.class.getName()),
                LoggingFeature.Verbosity.PAYLOAD_ANY));

        return instances;
    }
}