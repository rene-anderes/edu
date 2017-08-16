package org.anderes.edu.eventsource;

public class Cargo {

    private final String description;
    private Location<?> location;
    
    public Cargo(String description) {
        this.description = description;
    }

    public Location<?> getLocation() {
        return location;
    }

    public void setLocation(Location<?> location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void handleLoadEvent(LoadEvent event) {
        location = event.getShip();
        event.getShip().addCargo(event.getCargo());
    }

    public void handleUnloadEvent(UnloadEvent unloadEvent) {
        ((Ship)location).removeCargo(unloadEvent.getCargo());
        location = unloadEvent.getPort();
        
    }

    @Override
    public String toString() {
        return description;
    }

}
