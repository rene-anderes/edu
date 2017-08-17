package org.anderes.edu.eventsourcing.persistence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ARRIVAL")
public class ArrivalEventEntity extends ShippingEventEntity {

    private String ship;
    private String port;
    
    public ArrivalEventEntity() {
        super();
    }
    
    public ArrivalEventEntity(String ship, String port) {
        super();
        this.ship = ship;
        this.port = port;
    }

    public String getShip() {
        return ship;
    }

    public String getPort() {
        return port;
    }
}
