package org.anderes.edu.eventsource;

public class Cargo {

    private final String description;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Cargo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void handleLoadEvent(LoadEvent event) {
        location = event.getShip();
        event.getShip().addCargo(event.getCargo());
    }

}
