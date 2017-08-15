package org.anderes.edu.eventsource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Ship implements Location<Ship> {

    private final String name;
    private Port port = Port.NOT_DEFINED;
    private Set<Cargo> lading = new HashSet<>();
    
    public Ship(String name) {
        super();
        this.name = name;
    }
    
    public Port getPort() {
        return port;
    }

    public void setPort(Port location) {
        this.port = location;
    }

    public String getName() {
        return name;
    }

    public void handleDepartueEvent(final DepartureEvent event) {
        setPort(Port.AT_SEA);
    }

    public void handleArrivalEvent(final ArrivalEvent event) {
        setPort(event.getPort());
        
    }

    @Override
    public String getDescription() {
        return name;
    }

    public void addCargo(Cargo cargo) {
        lading.add(cargo);
    }

    public Set<Cargo> getCargo() {
        return Collections.unmodifiableSet(lading);
    }

    @Override
    public Ship getLocation() {
        return this;
    }
}
