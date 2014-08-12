package org.anderes.edu.testing;

/**
 * Copyright(c) 2008 René Anderes
 * created date: 31.07.2008
 */

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Template für JUnit-Testklasse
 * 
 * @author René Anderes
 */
public class TestTemplate {

    /**
     * Konstruktor
     */
    public TestTemplate() {

    }

    /**
     * Einmaliges Aufsetzen der Testumgebung
     */
    @BeforeClass
    public static void classInit() {

    }

    /**
     * Aufräumen am Schluss aller Tests
     */
    @AfterClass
    public static void classDestroy() {

    }

    /**
     * Diese Methode wird vor jeden Test-Methodenaufruf ausgeführt
     */
    @Before
    public void methodInit() {

    }

    /**
     * Diese Methode wird nach jeder Test-Methode aufgerufen
     */
    @After
    public void methodeDestroy() {

    }

    /**
     * Testmethode
     */
    @Test
    public void theFirstTest() {
        assertTrue("Test nicht OK", true);
        assertEquals("Test", "Test");
    }
}