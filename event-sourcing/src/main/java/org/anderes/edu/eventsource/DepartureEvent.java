package org.anderes.edu.eventsource;

import java.time.LocalDateTime;

public class DepartureEvent extends ShippingEvent {

    private Ship ship;
    
    public DepartureEvent(LocalDateTime date, Ship ship) {
        super(date);
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public void process() {
        ship.handleDepartueEvent(this);
    }

    @Override
    public String toString() {
        return String.format("DepartureEvent (%s) [ship=%s]", super.toString(), ship);
    }

}
