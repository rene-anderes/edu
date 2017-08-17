package org.anderes.edu.eventsourcing;

public enum Port implements Location<Port> {

    AT_SEA, 
    NOT_DEFINED,
    HAMBURG,
    CAPETOWN;

    @Override
    public String getDescription() {
        return name();
    }
    
}
