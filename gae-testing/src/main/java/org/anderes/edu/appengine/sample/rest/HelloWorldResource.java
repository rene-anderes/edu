package org.anderes.edu.appengine.sample.rest;

import static javax.ws.rs.core.MediaType.*;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.anderes.edu.appengine.sample.dto.MessageDto;

@Path("/helloworld")
public class HelloWorldResource {

    private Logger logger = Logger.getLogger(this.getClass().getName());
   	
	@PUT
    @Consumes(TEXT_PLAIN)
    public Response putMessage(String message) {
        logger.info("Meldung (text/plain): " + message);
        return Response.ok().build();
    }

	@GET
	@Produces(TEXT_PLAIN)
	public String getClichedMessage() {
	    logger.info("GET - TEXT/PLAIN");
	    return "Hello World";
	}
	
	@GET
	@Path("/test")
    @Produces(APPLICATION_JSON)
    public Response getMessage() {
	    logger.info("GET - APPLICATION/JSON");
        return Response.ok(new MessageDto("Hello World")).build();
    }
}
