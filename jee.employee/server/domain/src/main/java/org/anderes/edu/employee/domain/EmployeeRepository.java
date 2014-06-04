package org.anderes.edu.employee.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.anderes.edu.employee.domain.Address_;
import org.anderes.edu.employee.domain.Employee_;
import org.eclipse.persistence.annotations.BatchFetchType;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 * Repository für den Zugriff auf die Entität "Employee".
 * <p/>
 * Beispiele für
 * <ul>
 * <li>Zugriff via EntityManager auf die Persistence (CRUD)</li>
 * <li>Abfragen mit JPQL</li>
 * <li>Abfragen mit Criteria-Query</li>
 * <li>Caching</li>
 * </ul>
 * 
 * @author René Anderes
 *
 */
public class EmployeeRepository implements Repository<Employee, Long> {

    @PersistenceContext(unitName="employee")
    private EntityManager entityManager;
    @Inject
    private Logger logger;
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ CRUD - Functions  */
    
    @Override
    public Employee findOne(final Long id) {
        return entityManager.find(Employee.class, id);
    }
    
    @Override
    public Employee save(final Employee entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(final Employee entity) {
        entityManager.remove(entity);
    }

    @Override
    public boolean exists(final Long id) {
        final Employee entity = findOne(id);
        if (entity != null) {
            return entityManager.contains(entity);
        }
        return false;
    }
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / CRUD - Functions */
    
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Criteria-Query - Functions */
    
    /**
     * CriteriaQuery-sample for a single object
     */
    public Employee findOneUsingCriteriaQuery(final Long id) {
        
      final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      final CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
      final Root<Employee> entity = criteria.from(Employee.class);
      criteria.select(entity);
      criteria.where(cb.equal(entity.get(Employee_.id), id));
      final TypedQuery<Employee> query = entityManager.createQuery(criteria);
      return query.getSingleResult();
    }

    /**
     * CriteriaQuery-sample for a list of objects
     */
    public List<Employee> findEmployeeByGender(final Gender gender) {
        
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        final Root<Employee> entity = criteria.from(Employee.class);
        final Predicate hasSex = cb.equal(entity.get(Employee_.gender), gender);
        criteria.where(hasSex); // with Predicate
        final TypedQuery<Employee> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
    
    /**
     * CriteriaQuery-sample for a single data element.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Double findMaxSalary() {

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root e = cq.from(Employee.class);
        cq.select(cb.max(e.get(Employee_.salary)));
        final Query query = entityManager.createQuery(cq);
        return (Double)query.getSingleResult();
    }
    
    /**
     * CriteriaQuery-sample for a List of data elements.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<String> findAllFirstname() {

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root e = cq.from(Employee.class);
        cq.select(e.get(Employee_.firstName));
        final Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    

    /**
     * CriteriaQuery-sample for a List of element arrays.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Object[]> getAllNames() {
        
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root e = cq.from(Employee.class);
        cq.multiselect(e.<String>get(Employee_.firstName), e.<String>get(Employee_.lastName));
        final Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    
    /**
     * Select the average salaries grouped by city, ordered by the average salary.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Object[]> getAvarageSalaries() {
        
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery cq = cb.createQuery();
        final Root e = cq.from(Employee.class);
        final Expression avg = cb.avg(e.<Number>get(Employee_.salary));
        cq.multiselect(avg, e.<String>get(Employee_.address).<String>get(Address_.city));
        cq.groupBy(e.get(Employee_.address).get(Address_.city));
        cq.orderBy(cb.asc(avg));
        final Query query = entityManager.createQuery(cq);
        return query.getResultList();
    }
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / Criteria-Query - Functions */
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Java Persistence Query Language (JPQL) */
    
    /**
     * Beispiel mittels Named-Query
     */
    public List<Employee> findEmployeeBySalary(final Double salary) {
        
        final TypedQuery<Employee> query = entityManager.createNamedQuery(Employee.FINDALLEMPLOYEE_BY_SALARY, Employee.class);
        query.setParameter("salary", salary);
        
        return query.getResultList();
    }
    
    /**
     * Beispiel mit statischer JPQL-Query
     */
    public List<Employee> findEmployeeByCity(final String city) {
        
        final TypedQuery<Employee> query = entityManager.createQuery("Select e from Employee e where e.address.city = :city", Employee.class);
        query.setParameter("city", city);
        
        return query.getResultList();
    }
   
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / Java Persistence Query Language (JPQL) */
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fetch Strategien */

    /**
     * Sample with join fetch by Criteria Query
     */
    public Employee findOneEmployeeAddress(final Long id) {

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        final Root<Employee> entity = criteria.from(Employee.class);
        criteria.where(cb.equal(entity.get(Employee_.id), id));
        /* ----- join fetch */
        entity.fetch(Employee_.address, JoinType.LEFT);
        entity.fetch(Employee_.jobTitle, JoinType.LEFT);
        /* ----- / join fetch */
        final TypedQuery<Employee> query = entityManager.createQuery(criteria);
        
        return query.getSingleResult();
    }
       
    /**
     * Sample with join fetch by JPQL
     */
    public Employee findOneEmployeeAddressJpql(final Long id) {
        
        final TypedQuery<Employee> query = 
            entityManager.createQuery("Select e from Employee e left join fetch e.address left join fetch e.jobTitle where e.id = :id", Employee.class);
        query.setParameter("id", id);
        
        return query.getSingleResult();
    }
    
    /**
     * Sample with join fetch by EclispeLink Batch Fetching
     */
    public Employee findOneEmployeeAddressQueryHint(final Long id) {
        
        final TypedQuery<Employee> query = 
            entityManager.createQuery("Select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", id);
       
        /* Batch fetch query hint */
        query.setHint(QueryHints.BATCH, "e.address");
        /*  EclipseLink supports three different batch fetching types, JOIN, EXISTS, IN */
        query.setHint(QueryHints.BATCH_TYPE, BatchFetchType.EXISTS);
        query.setHint(QueryHints.LOAD_GROUP_ATTRIBUTE, "address");
        query.setHint(QueryHints.BATCH, "e.jobTitle");
        query.setHint(QueryHints.BATCH_TYPE, BatchFetchType.EXISTS);
        query.setHint(QueryHints.LOAD_GROUP_ATTRIBUTE, "jobTitle");
        
        return query.getSingleResult();
    }
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / Fetch Strategien */
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Caching */
    
    /**
     * Sample with query cache
     */
    public Employee findOneQueryCache(final Long id) {
        final TypedQuery<Employee> query = entityManager.createQuery("Select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", id);
        
        /* ----- query cache */
        query.setHint(QueryHints.CACHE_USAGE, CacheUsage.CheckCacheThenDatabase);
        /* ----- / query cache */
        
        logger.fine("-> cache usage: " + query.getHints().get(QueryHints.CACHE_USAGE));
        return query.getSingleResult();
    }
    
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ / Caching */
    
}
