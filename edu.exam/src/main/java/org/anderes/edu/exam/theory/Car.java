package org.anderes.edu.exam.theory;

public class Car {

    private String type;
    private int doors;

    public Car(String type) {
	this.type = type;
    }

    public Car(String type, int doors) {
	this(type);
	this.doors = doors;
    }

    public String getType() {
	return type;
    }

    public int getDoors() {
        return doors;
    }

}
