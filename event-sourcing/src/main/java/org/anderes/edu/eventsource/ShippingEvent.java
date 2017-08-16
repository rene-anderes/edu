package org.anderes.edu.eventsource;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class ShippingEvent {

    private final String uuid = UUID.randomUUID().toString();
    private LocalDateTime accurred;
    private LocalDateTime recorded;
    
    public ShippingEvent(LocalDateTime date) {
        this.accurred = date;
        recorded = LocalDateTime.now();
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getAccurred() {
        return accurred;
    }

    public LocalDateTime getRecorded() {
        return recorded;
    }

    public abstract void process();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShippingEvent other = (ShippingEvent) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "uuid=" + uuid + ", accurred=" + accurred + ", recorded=" + recorded;
    }
}
