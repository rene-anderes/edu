package org.anderes.edu.eventsource;

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
        return "ArrivalEvent [ship=" + ship + ", port=" + port + "]";
    }

}
