package org.anderes.edu.eventsourcing.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="EVENT_TYPE")
public abstract class ShippingEventEntity {

    @Id
    private final String uuid = UUID.randomUUID().toString();
    private LocalDateTime accurred;
    private LocalDateTime recorded;
    
    public LocalDateTime getAccurred() {
        return accurred;
    }
    public void setAccurred(LocalDateTime accurred) {
        this.accurred = accurred;
    }
    public LocalDateTime getRecorded() {
        return recorded;
    }
    public void setRecorded(LocalDateTime recorded) {
        this.recorded = recorded;
    }
    public String getUuid() {
        return uuid;
    }
}
