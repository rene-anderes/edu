 
package org.anderes.edu.jms.boundary;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.anderes.edu.jms.ejb.EnterpriseJavaBeanProducer;

/**
 * URL des REST-Services: http://localhost:8080/jee-jms/resources/messages
 * 
 * @author René Anderes
 */
@Path("messages")
public class RestController {

	@EJB
    private EnterpriseJavaBeanProducer sender;

	@POST
	@Consumes("text/plain")
	public Response publishMessage(String message) throws JMSException { 
	    if (message == null || message.isEmpty()) {
	        throw new WebApplicationException(BAD_REQUEST);
	    }
		sender.publishMessage(message);
		return Response.ok().entity(message).build();
	}
}