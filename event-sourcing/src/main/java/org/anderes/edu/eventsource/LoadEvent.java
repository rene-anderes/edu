package org.anderes.edu.eventsource;

import java.time.LocalDateTime;

public class LoadEvent extends ShippingEvent {

    private Cargo cargo;
    private Ship ship;

    public LoadEvent(LocalDateTime date, Cargo cargo, Ship ship) {
        super(date);
        this.ship = ship;
        this.cargo = cargo;
        setRecorded(LocalDateTime.now());
    }

    @Override
    public void process() {
        cargo.handleLoadEvent(this);
    }

    public Ship getShip() {
        return ship;
    }

    public Cargo getCargo() {
        return cargo;
    }

}
