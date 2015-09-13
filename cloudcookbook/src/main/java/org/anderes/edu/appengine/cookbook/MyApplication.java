package org.anderes.edu.appengine.cookbook;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> clazz = new HashSet<Class<?>>();
        clazz.add(HelloWorldResource.class);
        clazz.add(RecipeResource.class);
        return clazz;
    }
}
