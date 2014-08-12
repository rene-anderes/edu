package org.anderes.edu.builder;

import java.util.UUID;

public class Car {

    private int doors;
    private UUID serialNo;

    private Car() {
        doors = 2;
        serialNo = UUID.randomUUID();
    }

    public static Car build() {
        return new Car();
    }

    public int getDoors() {
        return doors;
    }

    public String getSerialNo() {
        return serialNo.toString();
    }
}
