package org.anderes.edu.eventsource;

public class Ship {

    private final String name;
    private Port location = Port.NOT_DEFINED;
    
    public Ship(String name) {
        super();
        this.name = name;
    }
    
    public Port getLocation() {
        return location;
    }

    public void setLocation(Port location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }
}
