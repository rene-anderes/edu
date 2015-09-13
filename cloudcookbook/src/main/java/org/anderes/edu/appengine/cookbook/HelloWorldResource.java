package org.anderes.edu.appengine.cookbook;

import static javax.ws.rs.core.MediaType.*;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/helloworld")
public class HelloWorldResource {

    private Logger logger = Logger.getLogger(this.getClass().getName());
   	
	@PUT
    @Consumes(TEXT_PLAIN)
    public void putMessage(String message) {
        logger.info("Meldung (text/plain): " + message);
    }

	@GET
	@Produces(TEXT_PLAIN)
	public String getClichedMessage() {
	    return "Hello World";
	}
	
	@GET
	@Path("/test")
    @Produces(APPLICATION_JSON)
    public Response getMessage() {
        return Response.ok(new MessageDto("Hello World")).build();
    }
}
