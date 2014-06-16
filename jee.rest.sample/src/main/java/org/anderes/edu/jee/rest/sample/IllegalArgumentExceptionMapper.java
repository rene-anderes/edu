package org.anderes.edu.jee.rest.sample;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements
		ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(final IllegalArgumentException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(TEXT_PLAIN).build();
	}
}
