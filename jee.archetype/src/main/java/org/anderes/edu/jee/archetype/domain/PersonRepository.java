package org.anderes.edu.jee.archetype.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersonRepository {
	
	@PersistenceContext(unitName = "defaultUnit")
	private EntityManager entityManager;
	
	public Person findOne(final Long id) {
		return entityManager.find(Person.class, id);
	}

}
