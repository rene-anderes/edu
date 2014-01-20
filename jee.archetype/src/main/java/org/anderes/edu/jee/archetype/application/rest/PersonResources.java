package org.anderes.edu.jee.archetype.application.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.anderes.edu.jee.archetype.application.PersonFacade;
import org.anderes.edu.jee.archetype.domain.Person;

@Path("/")
@Stateless
public class PersonResources {

	@EJB
	private PersonFacade facade;

	@GET
	@Path("{id}")
	public Person getPerson(@PathParam("id") Long id) {
		return facade.findById(id);
	}

}
