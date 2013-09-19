package org.anderes.edu.calc;

/**
 * Copyright(c) 2008 Ren� Anderes
 * created date: 31.07.2008
 */

import org.anderes.edu.calc.Calc;
import org.anderes.edu.calc.CalcBruteForce;
import org.anderes.edu.calc.CalcEratosthenes;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.reflect.Constructor;

/**
 * Testklasse f�r den Taschenrechner
 *
 * @author Ren� Anderes
 */
@RunWith(Parameterized.class)
public class TestCalcParameter {
    /** Liste mit Primnzahlen */
    private List<Integer> primeNumberList;
    /** Liste mit allen zu testenden Klassen */
    private Class<Calc> clazz;

    /**
     * Konstruktor
     *
     * @param testcalc Testklasse
     */
    public TestCalcParameter(Class<Calc> testcalc){
        clazz = testcalc;
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
    public void testCalc() {
        Calc calc = createInstance(clazz);
        testPrimeNumber(calc);
    }

    /**
     * Gibt die Instanz der �bergebenen Klasse zur�ck.
     *
     * @param clazz Klasse
     * @return Instanz der Klasse oder {@code null}, wenn ein Fehler die Instanzierung verhindert hat.
     */
    private <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[0]);
            return constructor.newInstance();
        } catch (Exception e) {
            System.out.println("InvocationTargetException: " + e.getMessage());
        }
        return null;
    }

    /**
     * Iteriert �ber alle vorgegebenen Primzahlen
     *
     * @param calc Der Taschenrechner
     */
    private void testPrimeNumber(Calc calc) {
        for (Integer primeNumber : primeNumberList) {
            assertTrue("Primzahl? " + primeNumber , calc.isPrimeNumber(primeNumber));
        }
    }

    /**
     * Parameter-Klasse
     * @return Liste mit Parametern
     */
    @Parameters
    public static Collection<Object> configure() {
        List<Object> output = new ArrayList<Object>(2);
        output.add(new Object[] { CalcBruteForce.class });
        output.add(new Class[] { CalcEratosthenes.class });
        return output;
    }
}