package org.anderes.edu.jee.rest.sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.*;

@Path("/users/{username}")
public class UserResource {

	@GET
	@Produces(TEXT_PLAIN)
	public Response getUser(@PathParam("username") String userName) {
		return Response.ok().entity(userName).build();
	}

}
