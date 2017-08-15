package org.anderes.edu.eventsource;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.anderes.edu.eventsource.Port.*;

import java.time.LocalDateTime;
import static java.time.Month.*;

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
        service.recordDeparture(LocalDateTime.of(2017, JULY, 15, 19, 12), kingKamehameha, HAMBURG);
        
        assertThat(kingKamehameha.getPort(), is(AT_SEA));
    }
    
    @Test
    public void loadCargoToShip() {
        service.recordArrival(LocalDateTime.of(2017, JULY, 22, 12, 33), meta, CAPETOWN);
        service.recordLoad(LocalDateTime.of(2017, JULY, 22, 15, 00), refact, meta);
        
        assertThat(refact.getLocation().getDescription(), is("Meta"));
        assertThat(meta.getCargo(), hasItem(refact));
    }
}
