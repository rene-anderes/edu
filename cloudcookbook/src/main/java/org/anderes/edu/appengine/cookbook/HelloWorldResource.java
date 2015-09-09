package org.anderes.edu.appengine.cookbook;

import static javax.ws.rs.core.MediaType.*;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/helloworld")
public class HelloWorldResource {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    
	@PUT
	@Consumes(APPLICATION_JSON)
	public void putMessage(String message) {
		logger.info(message);
	}

	@GET
	@Produces({TEXT_PLAIN, TEXT_HTML})
	public String getClichedMessage() {
		return "Hello World";
	}
}
