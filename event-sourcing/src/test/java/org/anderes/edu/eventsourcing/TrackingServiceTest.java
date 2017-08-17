package org.anderes.edu.eventsourcing;

import static org.anderes.edu.eventsourcing.Port.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import static java.time.Month.*;

import org.anderes.edu.eventsourcing.Cargo;
import org.anderes.edu.eventsourcing.Ship;
import org.anderes.edu.eventsourcing.TrackingService;
import org.junit.Before;
import org.junit.Test;

public class TrackingServiceTest {

    private TrackingService service;
    private Ship kingKamehameha;
    private Ship meta;
    private Cargo refact;

    @Before
    public void setup() {
        service = new TrackingService();
        kingKamehameha = new Ship("King-Kamehameha");
        meta = new Ship("Meta");
        refact = new Cargo("Refactoring");
    }
    
    @Test
    public void arrivalSetsShipsLocation() {
        service.recordArrival(LocalDateTime.now(), kingKamehameha, HAMBURG);
        service.recordArrival(LocalDateTime.of(2017, JULY, 22, 12, 33), meta, CAPETOWN);
        service.recordLoad(LocalDateTime.of(2017, JULY, 22, 15, 00), refact, meta);
        
        assertThat(kingKamehameha.getPort(), is(HAMBURG));
   }
    
    @Test
    public void departurePutsShipOutToSea() {
        service.recordArrival(LocalDateTime.of(2017, JULY, 15, 12, 33), kingKamehameha, HAMBURG);
        service.recordDeparture(LocalDateTime.of(2017, JULY, 15, 19, 12), kingKamehameha);
        
        assertThat(kingKamehameha.getPort(), is(AT_SEA));
    }
    
    @Test
    public void loadCargoToShip() {
        service.recordArrival(LocalDateTime.of(2017, JULY, 22, 12, 33), meta, CAPETOWN);
        service.recordLoad(LocalDateTime.of(2017, JULY, 22, 15, 00), refact, meta);
        
        assertThat(refact.getLocation().getDescription(), is("Meta"));
        assertThat(meta.getCargo(), hasItem(refact));
    }
    
    @Test
    public void unloadCargoToShip() {
        service.recordArrival(LocalDateTime.of(2017, JULY, 22, 12, 33), meta, CAPETOWN);
        service.recordLoad(LocalDateTime.of(2017, JULY, 22, 15, 00), refact, meta);
        service.recordDeparture(LocalDateTime.of(2017, JULY, 22, 19, 10), meta);
        service.recordArrival(LocalDateTime.of(2017, JULY, 30, 00, 24), meta, HAMBURG);
        service.recordUnload(LocalDateTime.of(2017, JULY, 30, 9, 00), refact, HAMBURG);
        
        assertThat(refact.getLocation().getDescription(), is("HAMBURG"));
        assertThat(meta.getCargo().contains(refact), is(false));
        assertThat(meta.getPort(), is(HAMBURG));
    }
}
