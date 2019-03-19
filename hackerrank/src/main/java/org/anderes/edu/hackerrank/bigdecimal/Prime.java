package org.anderes.edu.hackerrank.bigdecimal;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prime {

    public boolean checkPrime(int... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Paremeter ist null");
        }
        final List<Integer> primes = new ArrayList<>();
        for (int i = 0; i < values.length; i++ ) {
            if (BigInteger.valueOf(values[i]).isProbablePrime(10)) {
                primes.add(values[i]);
            }
        }
        dumpList(primes);
        
        return primes.size() == values.length;        
    }
    
    private void dumpList(final List<Integer> primes) {
        for(Integer p : primes) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
    
    public boolean checkPrime1(int... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Paremeter ist null");
        }
        final long count = Arrays.stream(values)
                .mapToObj(v -> BigInteger.valueOf(v))
                .filter(b -> b.isProbablePrime(10))
                .count();
        return count == values.length;        
    }
}
