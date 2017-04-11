package org.anderes.edu.exam.theory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

    private Address address;
    
    @Before
    public void setup() {
        address = new Address();
    }
    
    @Test
    public void shouldBeCorrectStreet() {
        address.setStreet("Rorschacherstrasse");
        
        assertNull("Die Strasse darf nicht null sein", address.getStreet());
        assertEquals(address.getStreet(), "Die Strasse stimmt nicht überein", "Rorschacherstrasse");
    }
    
    @Test
    public void shouldBeCorrectTown() {
        address.setTown("St. Gallen");
        address.setZipCode(9000);
        
        assertEquals(9000, address.getZipCode());
        assertEquals(address.getTown(), "St. Gallen");
    }
}
