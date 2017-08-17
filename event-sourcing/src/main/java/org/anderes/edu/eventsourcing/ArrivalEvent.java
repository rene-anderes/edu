package org.anderes.edu.eventsourcing;

import java.time.LocalDateTime;

public class ArrivalEvent extends ShippingEvent {

    private Ship ship;
    private Port port;
    
    public ArrivalEvent(LocalDateTime date, Ship ship, Port port) {
        super(date);
        this.ship = ship;
        this.port = port;
    }

    public Ship getShip() {
        return ship;
    }

    public Port getPort() {
        return port;
    }

    @Override
    public void process() {
        ship.handleArrivalEvent(this);
    }

    @Override
    public String toString() {
        return String.format("ArrivalEvent (%s) [ship=%s, port=%s]", super.toString(), ship, port);
    }

}
