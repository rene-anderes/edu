package org.anderes.edu.jee.archetype.application;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.anderes.edu.jee.archetype.domain.Person;
import org.anderes.edu.jee.archetype.domain.PersonRepository;

@Stateless
public class PersonFacade {

	@Inject
	private PersonRepository repository;
	
	public Person findById(final Long id) {
		return repository.findOne(id);
	}
}
