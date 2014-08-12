package org.anderes.edu.calc;

/**
 * Copyright(c) 2008 René Anderes
 * created date: 31.07.2008
 */

/**
 * Primzahlenberechnung nach der Brute-Force Methode
 *
 * @author René Anderes
 */
public class CalcBruteForce extends Calc {

    public boolean isPrimeNumber(int number) {
        if (number < 2) {
            return false;
        }
        else if (number == 2) {
            return true;
        }
        else if (number % 2 == 0) {
            return false;
        } else {
            for (int i = 3; i * i <= number; i += 2) {
                if (number % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
