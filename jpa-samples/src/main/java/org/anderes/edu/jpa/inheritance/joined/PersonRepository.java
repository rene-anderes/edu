package org.anderes.edu.jpa.inheritance.joined;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class PersonRepository {

	private EntityManager entityManager;
    
    private PersonRepository() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        entityManager = entityManagerFactory.createEntityManager();
    }
    
    public static PersonRepository build() {
    	return new PersonRepository();
    }
    
    /**
     * Gibt eine einzelne Person mittels ID (Datenbankidentität) zurück.
     * </p>
     * Technisch: Die Abfrage erfolgt via JPQL
     * @param id Datenbankidentität
     * @return Person
     */
    public Person getPersonById(final Long id) {
        
        return entityManager.find(Person.class, id);
    }
    
    /**
     * Gibt eine einzelne Person mittels ID (Datenbankidentität) zurück.
     * </p>
     * Technisch: Die Abfrage erfolgt via Citeria Query
     * @param id Datenbankidentität
     * @return Person
     */
    public Person getPersonByIdCriteria(final Long id) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);

        final Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Person_.id), id));
        final TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
             
        return query.getSingleResult();
    }
    
    /**
     * Liste aller Personen (natürliche und juristische Personen)
     * @return Liste aller Personen
     */
    public List<Person> getPersons() {
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p", Person.class);

        return query.getResultList();
    }
    
}
