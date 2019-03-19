package org.anderes.edu.hackerrank.bigdecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Scanner;
import static java.lang.System.in;
import org.junit.jupiter.api.Test;


public class PrimeTest {

    public static void main(String []args) {
        //Input
        Scanner sc= new Scanner(System.in);
        final BigInteger n = sc.nextBigInteger();
        sc.close();

        if (n.isProbablePrime(10)) {
            System.out.println("prime");
        } else {
            System.out.println("not prime");
        }
    }
    
    @Test
    void checkIsPrime() {
        final Prime prime = new Prime();
        assertThat (prime.checkPrime(2,1,3,4,5), is(false));
//        assertThat (prime.checkPrime(2,3,5,7,13), is(true));
    }
    
    @Test
    void checkInputStream() {
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        assertThat(br, is(not(nullValue())));
    }
    
}
