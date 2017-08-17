package org.anderes.edu.eventsourcing;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.anderes.edu.eventsourcing.persistence.ArrivalEventEntity;

public class EventLogPersistence {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("eclipseLinkPU");

    public void log(ArrivalEvent event) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ArrivalEventEntity entity = new ArrivalEventEntity(event.getShip().getName(), event.getPort().getDescription());
        entity.setAccurred(event.getAccurred());
        entity.setRecorded(event.getRecorded());
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public void log(ShippingEvent event) {
        if (event instanceof ArrivalEvent) {
            log((ArrivalEvent) event);
        }
        
    }
}
