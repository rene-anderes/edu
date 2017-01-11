package org.anderes.edu.di;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PrimeNumberServiceTest {

    private Service service;
    
    @Before
    public void setup() {
        service = new PrimeNumberService();
    }
    
    @Test
    public void shouldBeCheckIsPrimeNumber() {
        final Boolean isPrimeNumber = service.isPrimeNumber(Integer.valueOf(41));
        assertThat(isPrimeNumber, is(true));
    }
    
    @Test
    public void shouldBeCheckIsNotPrimeNumber() {
        final Boolean isPrimeNumber = service.isPrimeNumber(Integer.valueOf(42));
        assertThat(isPrimeNumber, is(false));
    }
}
