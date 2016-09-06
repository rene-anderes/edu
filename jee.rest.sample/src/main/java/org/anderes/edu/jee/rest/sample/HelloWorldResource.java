package org.anderes.edu.jee.rest.sample;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("helloworld")
public class HelloWorldResource {

	@POST
	@Consumes(TEXT_PLAIN)
	public Response putMessage(String message) {
		// Store the message
	    
	    URI location = UriBuilder.fromResource(HelloWorldResource.class).path("2033").build();
	    return Response.created(location).build();
	}

	@GET
	@Produces({TEXT_PLAIN, TEXT_HTML})
	public String getClichedMessage() {
		return "Hello World";
	}
	
}
