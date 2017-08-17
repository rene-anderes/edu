package org.anderes.edu.eventsourcing.consuming;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.anderes.edu.eventsourcing.Ship;
import org.anderes.edu.eventsourcing.ShippingEvent;
import org.anderes.edu.eventsourcing.TrackingService;

public class View {
    
    private final TrackingService service;

    public View(TrackingService service) {
        super();
        this.service = service;
    }

    public void getShipLocationByDate(Ship kingKamehameha, LocalDateTime date) {
        List<ShippingEvent> logs = service.getLog().stream()
                        .sorted((e1,e2) -> e1.getAccurred().compareTo(e2.getAccurred())).filter(e -> e.getAccurred().isBefore(date))
                        .peek(e -> System.out.println(e.getAccurred()))
                        .collect(Collectors.toList());
        
    }
}
