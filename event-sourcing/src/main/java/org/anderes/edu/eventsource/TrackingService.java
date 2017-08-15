package org.anderes.edu.eventsource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrackingService {
    
    final List<ShippingEvent> events = new ArrayList<ShippingEvent>();

    public void recordArrival(LocalDateTime date, Ship ship, Port port) {
        final ArrivalEvent event = new ArrivalEvent(date, ship, port);
        event.process();
        log(event);
    }

    private void log(ShippingEvent event) {
        events.add(event);
    }

    public void recordDeparture(LocalDateTime date, Ship ship, Port port) {
        final DepartureEvent event = new DepartureEvent(date, ship, port);
        event.process();
        log(event);
    }

    public void recordLoad(LocalDateTime date, Cargo cargo, Ship ship) {
        final LoadEvent event = new LoadEvent(date, cargo, ship);
        event.process();
        log(event);
    }

    
}
