package org.anderes.edu.employee.application.boundary.rest;

import static javax.ws.rs.core.MediaType.*;
import static java.nio.charset.StandardCharsets.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ViolationException> {

    @Override
    public Response toResponse(ViolationException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).type(TEXT_PLAIN_TYPE).encoding(UTF_8.name()).build();
    }

}
