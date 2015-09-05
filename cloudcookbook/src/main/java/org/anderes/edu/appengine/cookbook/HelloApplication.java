package org.anderes.edu.appengine.cookbook;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/services")
public class HelloApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> claz = new HashSet<Class<?>>();
        claz.add(HelloWorldResource.class);
        return claz;
    }
}
