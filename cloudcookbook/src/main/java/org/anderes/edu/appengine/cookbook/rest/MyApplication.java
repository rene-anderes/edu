package org.anderes.edu.appengine.cookbook.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.logging.LoggingFeature;

@Path("services")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazz = new HashSet<Class<?>>(3);
        clazz.add(RecipeResource.class);
        clazz.add(RecipeSyncResource.class);
        clazz.add(NotFoundExceptionMapper.class);
        return clazz;
    }
    
    @Override
    public Set<Object> getSingletons() {
        final HashSet<Object> instances = new HashSet<Object>(1);
        instances.add(new LoggingFeature(Logger.getLogger(MyApplication.class.getName()), LoggingFeature.Verbosity.PAYLOAD_ANY));
        return instances;
}
}
