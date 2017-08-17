package org.anderes.edu.eventsourcing.consuming;

import static java.time.Month.*;
import static org.anderes.edu.eventsourcing.Port.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.anderes.edu.eventsourcing.Cargo;
import org.anderes.edu.eventsourcing.Ship;
import org.anderes.edu.eventsourcing.TrackingService;
import org.junit.Before;
import org.junit.Test;

public class ViewTest {

    private TrackingService service;

    @Before
    public void setup() {
        service = new TrackingService();
        final Ship kingKamehameha = new Ship("King-Kamehameha");
        final Ship meta = new Ship("Meta");
        final Cargo refact = new Cargo("Refactoring");
        service.recordArrival(LocalDateTime.of(2017, JULY, 21, 22, 15), kingKamehameha, HAMBURG);
        service.recordArrival(LocalDateTime.of(2017, JULY, 22, 12, 33), meta, CAPETOWN);
        service.recordLoad(LocalDateTime.of(2017, JULY, 22, 15, 00), refact, meta);
        service.recordDeparture(LocalDateTime.of(2017, JULY, 22, 10, 00), kingKamehameha);
        service.recordArrival(LocalDateTime.of(2017, AUGUST, 1, 8, 00), kingKamehameha, CAPETOWN);
    }
        
    @Test
    public void shipByDate() {
        View view = new View(service);
        final Ship kingKamehameha = new Ship("King-Kamehameha");
        view.getShipLocationByDate(kingKamehameha, LocalDateTime.of(2017, JULY, 31, 11, 00));
        
        assertThat(kingKamehameha.getPort(), is(AT_SEA));
    }
}
