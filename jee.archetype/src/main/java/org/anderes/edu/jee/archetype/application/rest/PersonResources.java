package org.anderes.edu.jee.archetype.application.rest;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.*;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.anderes.edu.jee.archetype.domain.Person;
import org.anderes.edu.jee.archetype.domain.PersonRepository;

@Path("persons")
public class PersonResources {

    @Inject
    private PersonRepository repository;
    
	@GET
	@Path("{id}")
	@Produces(APPLICATION_JSON)
	public Person getPerson(@PathParam("id") Long id) {
		return repository.findOne(id);
	}

	@GET
	@Produces(APPLICATION_JSON)
	public Response findAll() {
	    final Collection<Person> persons = repository.findAll();
	    final GenericEntity<Collection<Person>> entity = new GenericEntity<Collection<Person>>(persons) {};
        return Response.ok().encoding(UTF_8.name()).entity(entity).build();
	}
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response saveNew(Person person) {
	    final Person personAfterSave = repository.save(person);
	    final URI location = UriBuilder.fromResource(PersonResources.class).path(Long.toString(personAfterSave.getId())).build();
	    return Response.created(location).build();
	}
}
