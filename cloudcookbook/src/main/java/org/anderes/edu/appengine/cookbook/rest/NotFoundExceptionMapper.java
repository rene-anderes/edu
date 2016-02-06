package org.anderes.edu.appengine.cookbook.rest;

import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import javax.ws.rs.ext.ExceptionMapper;

import com.googlecode.objectify.NotFoundException;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(NOT_FOUND).build();
    }

}
