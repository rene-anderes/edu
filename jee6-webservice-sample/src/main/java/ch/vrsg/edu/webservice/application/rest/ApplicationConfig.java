package ch.vrsg.edu.webservice.application.rest;

import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("resources")
public class ApplicationConfig extends Application {

	public Set<Class<?>> getClasses() {
        return getRestClasses();
    }

	private Set<Class<?>> getRestClasses() {
		Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
		resources.add(EmployeeResource.class);
        return resources;    
    }
}