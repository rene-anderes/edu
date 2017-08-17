package org.anderes.edu.eventsourcing.persistence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UNLOAD")
public class UnloadEventEntity extends ShippingEventEntity {

    private String cargo;
    private String port;
    
    public UnloadEventEntity() {
        super();
    }
    
    public UnloadEventEntity(String cargo, String port) {
        super();
        this.cargo = cargo;
        this.port = port;
    }
    
    public String getCargo() {
        return cargo;
    }

    public String getPort() {
        return port;
    }
}
