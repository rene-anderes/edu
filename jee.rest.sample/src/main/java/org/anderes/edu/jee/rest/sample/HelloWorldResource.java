package org.anderes.edu.jee.rest.sample;

import static javax.ws.rs.core.MediaType.TEXT_HTML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("helloworld")
public class HelloWorldResource {

	@PUT
	@Consumes(TEXT_PLAIN)
	public void putMessage(String message) {
		// Store the message
	}

	@GET
	@Produces({TEXT_PLAIN, TEXT_HTML})
	public String getClichedMessage() {
		return "Hello World";
	}
	
	@POST
	@Consumes(TEXT_PLAIN)
	public Response postMessage(final String message) {
	    return Response.ok(message).encoding("UTF-8").build();
	}
}
