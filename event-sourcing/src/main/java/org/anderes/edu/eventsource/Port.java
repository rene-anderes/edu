package org.anderes.edu.eventsource;

public enum Port implements Location {

    AT_SEA, 
    NOT_DEFINED,
    HAMBURG,
    CAPETOWN;

    @Override
    public String getDescription() {
        return name();
    }
    
}
