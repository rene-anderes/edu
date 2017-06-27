package org.anderes.edu.jee.rest.sample;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception-Mapper: Wird eine IllegalArgumentException von einer
 * Methode geworfen, so wird diese in einen 'Bad Request' umgewandelt.
 * 
 * @author René Anderes
 *
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(final IllegalArgumentException exception) {
		return Response.status(BAD_REQUEST).entity(exception.getMessage()).type(TEXT_PLAIN).build();
	}
}
