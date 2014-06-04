/*
 * Copyright (c) 2013 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.persistence;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class EntityManagerProducer {
 
    @Produces
    @Default
    @PersistenceContext(unitName = "employee")
    EntityManager entityManager;
    
}
