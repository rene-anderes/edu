package org.anderes.edu.appengine.cookbook;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

public class OfyHelper implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectifyService.register(Recipe.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // App Engine does not currently invoke this method.
    }

}
