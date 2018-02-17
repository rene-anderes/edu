package org.anderes.edu.appengine.cookbook.rest;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.googlecode.objectify.NotFoundException;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(NOT_FOUND).entity(exception).build();
    }

}
