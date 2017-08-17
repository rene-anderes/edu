package org.anderes.edu.eventsourcing;

import java.time.LocalDateTime;

public class LoadEvent extends ShippingEvent {

    private Cargo cargo;
    private Ship ship;

    public LoadEvent(LocalDateTime date, Cargo cargo, Ship ship) {
        super(date);
        this.ship = ship;
        this.cargo = cargo;
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

    @Override
    public String toString() {
        return String.format("LoadEvent (%s) [cargo=%s, ship=%s]", super.toString(), cargo, ship);
    }

}
