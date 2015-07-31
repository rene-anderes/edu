package org.anderes.edu.employee.application.boundary.rest;

import javax.ejb.ApplicationException;

@ApplicationException
public class ViolationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ViolationException(String message) {
        super(message);
    }

}
