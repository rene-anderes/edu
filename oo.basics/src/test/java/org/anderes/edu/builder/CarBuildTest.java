package org.anderes.edu.builder;

import static org.junit.Assert.*;

import org.anderes.edu.builder.Car;
import org.junit.Test;


public class CarBuildTest {

    @Test
    public void buildConstructure() {
	Car car = Car.build();
	assertNotNull(car);
    }
}
