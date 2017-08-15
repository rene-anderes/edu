package org.anderes.edu.eventsource;

import java.time.LocalDateTime;

public abstract class ShippingEvent {

    private LocalDateTime accurred;
    private LocalDateTime recorded;
    
    public ShippingEvent(LocalDateTime date) {
        this.accurred = date;
    }

    public LocalDateTime getAccurred() {
        return accurred;
    }

    public LocalDateTime getRecorded() {
        return recorded;
    }

    protected void setRecorded(LocalDateTime recorded) {
        this.recorded = recorded;
    }

    public abstract void process();
}
