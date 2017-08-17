package org.anderes.edu.eventsourcing.persistence;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PortEntity {

    @Id
    private final String uuid = UUID.randomUUID().toString();
    private String name;
    
    public PortEntity() {
        super();
    }

    public PortEntity(String name) {
        super();
        this.name = name;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getName() {
        return name;
    }
    
}
