package org.anderes.edu.eventsourcing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackingService {
    
    final List<ShippingEvent> events = new ArrayList<ShippingEvent>();
    final EventLogPersistence persistenceLogger = new EventLogPersistence();

    public void recordArrival(LocalDateTime date, Ship ship, Port port) {
        final ArrivalEvent event = new ArrivalEvent(date, ship, port);
        event.process();
        log(event);
    }

    private void log(ShippingEvent event) {
        events.add(event);
        System.out.println(event);
        //persistenceLogger.log(event);
    }

    public void recordDeparture(LocalDateTime date, Ship ship) {
        final DepartureEvent event = new DepartureEvent(date, ship);
        event.process();
        log(event);
    }

    public void recordLoad(LocalDateTime date, Cargo cargo, Ship ship) {
        final LoadEvent event = new LoadEvent(date, cargo, ship);
        event.process();
        log(event);
    }

    public void recordUnload(LocalDateTime date, Cargo cargo, Port port) {
        final UnloadEvent event = new UnloadEvent(date, cargo, port);
        event.process();
        log(event);
    }

    public List<ShippingEvent> getLog() {
        return Collections.unmodifiableList(events);
    }
}
