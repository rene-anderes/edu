package org.anderes.edu.jee.archetype.domain;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;

public class PersonRepository {
	
	@PersistenceContext(unitName = "defaultUnit")
	private EntityManager entityManager;
	
	public Person findOne(final Long id) {
		return entityManager.find(Person.class, id);
	}

	public Collection<Person> findAll() {
	    return entityManager.createQuery("select p from Person p", Person.class).getResultList();
	}
	
	@Transactional
	public Person save(final Person person) {
	    Validate.notNull(person);
	    Person savedPerson = null;
	    if (person.getId() == null || person.getId() == 0) {
	        entityManager.persist(person);
	        savedPerson = person;
	    } else {
	        savedPerson = entityManager.merge(person);
	    }
	    return savedPerson;
	}
}
