package org.anderes.edu.jee.archetype.domain;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PersonRepository {
	
	@Inject
	private EntityManager entityManager;
	
	public Person findOne(final Long id) {
		return entityManager.find(Person.class, id);
	}

}
