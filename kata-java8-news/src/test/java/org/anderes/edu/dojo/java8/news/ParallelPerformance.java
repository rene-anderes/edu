package org.anderes.edu.dojo.java8.news;

import static java.lang.Math.sqrt;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

public class ParallelPerformance {

    private StopWatch watch = new StopWatch();

    @Before
    public void setup() {
        watch.reset();
    }
    
    @Test
    public void filterMapReduce() {
        Stream<String> stringStream = Stream.generate(() -> randomAlphabetic(5)).limit(10000000);
        watch.start();
        Integer sum = stringStream
//                        .parallel()
                        .filter(planet -> !planet.contentEquals("Sonne"))
                        .map(String::length)
                        .reduce(0, Integer::sum);
        watch.stop();
        System.out.println("Summe: " + sum);
        System.out.println("Time: " + watch.getTime());
        assertThat(sum, is(50000000));
    }
    
    @Test
    public void primes() {
        watch.start();
        long result = LongStream.range(1, 10000000)
                        .parallel()
                        .filter(this::isPrime)
                        .count();
        watch.stop();
        System.out.println("Result: " + result);
        System.out.println("Time: " + watch.getTime());
        assertThat(result, is(664579));
    }
    
    private boolean isPrime(long n) {
        return n > 1 && LongStream.rangeClosed(2, (long)sqrt(n)).noneMatch(divisor -> n % divisor == 0);
    }
}
