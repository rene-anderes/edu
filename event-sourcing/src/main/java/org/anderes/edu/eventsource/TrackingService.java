package org.anderes.edu.eventsource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrackingService {
    
    final List<ShippingEvent> events = new ArrayList<ShippingEvent>();

    public void recordArrival(LocalDateTime date, Ship ship, Port port) {
        ArrivalEvent event = new ArrivalEvent(date, ship, port);
        event.process();
        log(event);
    }

    private void log(ArrivalEvent event) {
        events.add(event);
    }

    
}
