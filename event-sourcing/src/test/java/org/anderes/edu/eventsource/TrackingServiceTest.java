package org.anderes.edu.eventsource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

public class TrackingServiceTest {

    @Test
    public void recordArrival() {
        TrackingService service = new TrackingService();
        Port port = Port.HAMBURG;
        Ship ship = new Ship("KingKameamea");
        service.recordArrival(LocalDateTime.now(), ship, port);
        
        assertThat(ship.getLocation(), is(Port.HAMBURG));
    }
}
