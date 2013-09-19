package org.anderes.edu.equals;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ObjectEqualsTest {

    private Address addressOne;
    private Address addressTwo;
    private Set<Address> addressSet = new HashSet<Address>();
    
    @Before
    public void setupBefore() {
        addressOne = new Address("Rorschacherstrasse", "1", "C");
        addressTwo = new Address("Rorschacherstrasse", "1", "C");
    }
    
    @Test
    public void addressEqualsAndSame() {
        assertEquals(addressOne, addressTwo);
        assertNotSame(addressOne, addressTwo);
    }
    
    @Test
    public void AddressInSet() {
        addressSet.add(addressOne);
        assertEquals(1, addressSet.size());
        addressSet.add(addressTwo);
        assertEquals(1, addressSet.size());
    }

}
