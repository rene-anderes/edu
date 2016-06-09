package org.anderes.edu.testing.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MyFirstJUnitTest {

    @Test
    public void myFirstTestMethode() {
        List<String> names = new ArrayList<>(Arrays.asList("Jack", "Jo", "Will", "Bill"));
        assertNotNull("Liste darf nicht null sein", names);
        assertFalse(names.isEmpty());
        assertTrue(names.contains("Jo"));
        assertEquals(4, names.size());
    }
    
    @Test
    public void shouldBeAddNewName() {
        List<String> names = new ArrayList<>(Arrays.asList("Jack", "Jo", "Will", "Bill"));
        assertTrue(names.add("Bob"));
        assertEquals(5, names.size());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void shouldBeAddWithException() {
        List<String> names = Arrays.asList("Jack", "Jo", "Will", "Bill");
        assertTrue(names.add("Bob"));
    }
}
