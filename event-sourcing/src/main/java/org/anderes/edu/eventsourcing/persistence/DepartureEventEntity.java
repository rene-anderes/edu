package org.anderes.edu.eventsourcing.persistence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPARTURE")
public class DepartureEventEntity extends ShippingEventEntity {

    private String ship;

    public DepartureEventEntity() {
        super();
    }
    
    public DepartureEventEntity(String ship) {
        super();
        this.ship = ship;
    }
    
    public String getShip() {
        return ship;
    }
}
