package org.anderes.edu.di;

import static java.lang.Math.sqrt;

import java.util.stream.LongStream;

import javax.inject.Singleton;

@Singleton
public class PrimeNumberService implements Service {

    @Override
    public Boolean isPrimeNumber(final Integer number) {
        return number > 1 && LongStream.rangeClosed(2, (long)sqrt(number)).noneMatch(divisor -> number % divisor == 0);
    }

}
