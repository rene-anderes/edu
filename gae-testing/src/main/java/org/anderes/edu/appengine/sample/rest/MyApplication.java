package org.anderes.edu.appengine.sample.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@Path("services")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazz = new HashSet<Class<?>>();
        clazz.add(HelloWorldResource.class);
        clazz.add(UserAuthResources.class);
        return clazz;
    }
}
