package org.anderes.edu.di;

import static java.lang.Math.sqrt;

import java.util.stream.LongStream;

public class PrimeNumberService {

    public Boolean isPrimeNumber(final Integer number) {
        // nicht so effiziente Primzahlberechnung
        return number > 1 && LongStream.rangeClosed(2, (long)sqrt(number)).noneMatch(divisor -> number % divisor == 0);
    }

}
