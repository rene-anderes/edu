package org.anderes.edu.calc;

/**
 * Copyright(c) 2008 Ren� Anderes
 * created date: 31.07.2008
 */

import java.util.Arrays;

/**
 * Primzahlenberechnung nach dem Sieb des Eratosthenes
 *
 * @author Ren� Anderes
 */
public class CalcEratosthenes extends Calc {

    /** {@inheritDoc} */
    public boolean isPrimeNumber(int number) {
        boolean[] primeNoArray = new boolean[number+1];
        int len = primeNoArray.length;
        Arrays.fill(primeNoArray, true);
        if (len > 0) primeNoArray[0] = false;
        if (len > 1) primeNoArray[1] = false;

        for (int i = 4; i < len; i += 2) {
            primeNoArray[i] = false;
        }

        for (int i = 3; i * i <= len; i += 2) {
            if (primeNoArray[i]) {
                for (int j = i << 1; j < len; j += i) {
                    primeNoArray[j] = false;
                }
            }
        }
        return primeNoArray[number];
    }
}
