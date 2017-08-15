package org.anderes.edu.eventsource;

import java.time.LocalDateTime;

public class DepartureEvent extends ShippingEvent {

    private Ship ship;
    private Port port;
    
    public DepartureEvent(LocalDateTime date, Ship ship, Port port) {
        super(date);
        this.ship = ship;
        this.port = port;
        setRecorded(LocalDateTime.now());
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public void process() {
        ship.handleDepartueEvent(this);
    }

}
