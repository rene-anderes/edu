package org.anderes.edu.di.initial;

public class CarApplication {

    public static void main(String[] args) {
	Car car = new CarImpl();
	car.setPedalPressure(5);
	int speed = car.getSpeed();
	System.out.println("Speed of the car is " + speed);
    }
}
