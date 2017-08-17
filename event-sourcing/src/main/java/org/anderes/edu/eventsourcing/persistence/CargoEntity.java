package org.anderes.edu.eventsourcing.persistence;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CargoEntity {

    @Id
    private final String uuid = UUID.randomUUID().toString();
    private String description;
    
    public CargoEntity() {
        super();
    }
    
    public CargoEntity(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
