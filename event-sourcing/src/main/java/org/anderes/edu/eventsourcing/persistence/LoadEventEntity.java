package org.anderes.edu.eventsourcing.persistence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LOAD")
public class LoadEventEntity extends ShippingEventEntity {

    private String cargo;
    private String ship;

    public LoadEventEntity() {
        super();
    }
    
    public LoadEventEntity(String cargo, String ship) {
        super();
        this.cargo = cargo;
        this.ship = ship;
    }
    
    public String getCargo() {
        return cargo;
    }

    public String getShip() {
        return ship;
    }
}
