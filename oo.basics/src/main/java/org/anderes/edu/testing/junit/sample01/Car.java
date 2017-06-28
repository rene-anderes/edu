package org.anderes.edu.testing.junit.sample01;

import java.util.HashMap;
import java.util.Map;
import static org.anderes.edu.testing.junit.sample01.DoorState.*;

public class Car {
    
    private final Integer numberOfDoor;
    private final Map<Integer, DoorState> doorConditions;

    public Car(Integer numberOfDoor) {
        super();
        this.numberOfDoor = numberOfDoor;
        doorConditions = new HashMap<>(numberOfDoor);
    }
    
    public void setDoorState(Integer doorNo, DoorState state) {
        if(doorNo < 1 || doorNo > numberOfDoor) {
            throw createException();
        }
        doorConditions.put(doorNo, state);
    }

    public boolean isDoorOpen(Integer doorNo) {
        if(doorNo < 1 || doorNo > numberOfDoor) {
            throw createException();
        }
        return doorConditions.getOrDefault(doorNo, NONE) == OPEN;
    }

    public boolean isDoorClose(Integer doorNo) {
        if(doorNo < 1 || doorNo > numberOfDoor) {
            throw createException();
        }
        return doorConditions.getOrDefault(doorNo, NONE) == CLOSE;
    }

    private IllegalArgumentException createException() {
        return new IllegalArgumentException(String.format("Die Türnummer muss grösser als 0 und nicht grösser als %s sein.", numberOfDoor));
    }
}
