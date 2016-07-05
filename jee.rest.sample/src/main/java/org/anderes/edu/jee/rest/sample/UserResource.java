package org.anderes.edu.jee.rest.sample;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("users")
public class UserResource {

	@GET
	@Path("{username}")
	@Produces(TEXT_PLAIN)
	public Response getUser(@PathParam("username") String userName) {
	    final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(5);
        cacheControl.setPrivate(true);
		return Response.ok().entity(userName).cacheControl(cacheControl).build();
	}

	/**
	 * Wird für das Mapping der {@code List<String>} MOXy verwendet, wird ein Fehler 500
	 * auf dem Server ausgelöst: 
	 * "MessageBodyWriter not found for media type=application/json, 
	 *  type=class java.util.ArrayList, genericType=java.util.List<java.lang.String>."<br>
	 *  Das ist ein MOXy-Problem und tritt mit z.B. Jackson nicht auf.
	 */
	@GET
	@Produces(APPLICATION_JSON)
	public Response getAllUser() {
	    final List<String> userlist = new ArrayList<String>(Arrays.asList("Bill", "Tom", "Bob"));
	    final GenericEntity<List<String>> entity = new GenericEntity<List<String>>(userlist) {};
        return Response.ok().encoding(UTF_8.name()).entity(entity).build();
	}
}
