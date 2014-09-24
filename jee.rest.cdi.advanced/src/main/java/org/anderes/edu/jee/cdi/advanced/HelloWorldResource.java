package org.anderes.edu.jee.cdi.advanced;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/helloworld")
public class HelloWorldResource {

    @Inject @Relaxed
    private RelaxedGreetingService service;
    
	@GET
	@Produces(TEXT_PLAIN)
	public String getClichedMessage() {
		return service.sayHello();
	}
}
