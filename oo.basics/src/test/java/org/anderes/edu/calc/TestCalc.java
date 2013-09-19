package org.anderes.edu.calc;

/**
 * Copyright(c) 2008 Ren� Anderes
 * created date: 31.07.2008
 */

import org.anderes.edu.calc.CalcBruteForce;
import org.anderes.edu.calc.CalcEratosthenes;
import org.anderes.edu.calc.CalcIfc;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


/**
 * Testklasse f�r den Taschenrechner
 *
 * @author Ren� Anderes
 */
public class TestCalc {
    /** Liste mit Primnzahlen */
    private List<Integer> primeNumberList;

    /**
     * Konstruktor
     */
    public TestCalc() {
        // Nothing to do....
    }

    /**
     * Einmaliges Aufsetzen der Testumgebung
     */
    @BeforeClass
    public static void classInit() {

    }

    /**
     * Aufr�umen am Schluss aller Tests
     */
    @AfterClass
    public static void classDestroy() {

    }

    /**
     * Diese Methode wird vor jeden Test-Methodenaufruf ausgeführt
     */
    @Before
    public void methodInit() {
        primeNumberList = new ArrayList<Integer>();
        primeNumberList.add(7);
        primeNumberList.add(3307);
        primeNumberList.add(33301);
        primeNumberList.add(133283);
        primeNumberList.add(1833269);
    }

    /**
     * Diese Methode wird nach jeder Test-Methode aufgerufen
     */
    @After
    public void methodeDestroy() {

    }
    
    /**
     * Test des {@link CalcEratosthenes}
     */
    @Test
    public void testCalcEratosthenes() {
        CalcIfc calc = new CalcEratosthenes();
        assertEquals(25D, calc.multiply(5, 5), 0);
        assertEquals(10, calc.incremental(5, 5));
        testAllPrimeNumber(calc);
    }

    /**
     * Test des {@link CalcBruteForce}
     */
    @Test
    public void testCalcBruteForce() {
        CalcIfc calc = new CalcBruteForce();
        assertEquals(36D, calc.multiply(6, 6), 0);
        assertEquals(11, calc.incremental(5, 6));
        testAllPrimeNumber(calc);
    }

    /**
     * Iteriert �ber alle vorgegebenen Primzahlen
     *
     * @param calc Der Taschenrechner
     */
    private void testAllPrimeNumber(CalcIfc calc) {
        for (Integer primeNumber : primeNumberList) {
            assertTrue("Primzahl? " + primeNumber , calc.isPrimeNumber(primeNumber));
        }
    }
}