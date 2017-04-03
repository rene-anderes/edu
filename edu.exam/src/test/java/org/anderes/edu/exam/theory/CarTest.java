package org.anderes.edu.exam.theory;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CarTest {

    /**
     * Test des Konstruktors mit Fahrzeugtyp
     */

    @Test
    @Ignore
    public void shouldBeAudi() {
	Car car = new Car("Audi");
	assertNull("Das Objekt ist null", car);
	assertEquals("Audi", car.getType());
    }

    /**
     * Test des Konstruktors mit Farzeugtyp und Anzahl Türen
     */

    @Test
    @Ignore
    public void shouldBeVWwith4Doors() {
	Car car = new Car("VW", 4);
	assertEquals(4, car.getDoors());
	assertEquals(car.getType(), "Audi");
    }
}
