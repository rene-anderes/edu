package org.anderes.edu.exam.theory;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class PersonTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test des Konstruktors mit Vorname der Person
     */
    @Test
    @Ignore
    public void firstTest() {
	Person person = new Person("Hans");
	assertNull("Das Objekt ist null", person);
	assertEquals(person.getFirstname(), "Hans");
    }
    
    /**
     * Test des Konstruktors mit Vor- und Nachname der Person
     */
    @Test
    @Ignore
    public void secondTest() {
	Person person = new Person("Peter", "Muster");
	assertEquals("Hans", person.getFirstname());
	assertEquals("Muster", person.getLastname());
    }

}
