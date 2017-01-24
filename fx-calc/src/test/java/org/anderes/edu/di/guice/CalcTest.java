package org.anderes.edu.di.guice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.anderes.edu.di.Calc;
import org.anderes.edu.di.PrimeNumberClassicService;
import org.anderes.edu.di.Service;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * Testklasse welche das Verhalten eines 
 * Taschenrechners mit UPN testet.
 * 
 * @author René Anderes
 */
public class CalcTest {
    
    private Calc calc;
    private final Injector injector = Guice.createInjector(new AbstractModule() {
        
        @Override
        protected void configure() {
            bind(Service.class).to(PrimeNumberClassicService.class);
        }
    });
    
    @Before
    public void setup() {
        calc = injector.getInstance(Calc.class);
    }

    @Test
    public void addAndRemoveStackValue() {
        calc.addToStack(new BigDecimal(10));
        
        assertThat(calc.getStack(), hasSize(1));
        assertThat(calc.getStackSize(), is(1));
        assertThat(calc.getStack(), hasItem(BigDecimal.valueOf(10)));
        
        calc.addToStack(new BigDecimal(22D));
        calc.addToStack(new BigDecimal(33D));
        assertThat(calc.getStack(), hasSize(3));
        assertThat(calc.getStackSize(), is(3));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(33), BigDecimal.valueOf(22), BigDecimal.valueOf(10)));

        assertThat(calc.removeFromStack().isPresent(), is(true));
        assertThat(calc.getStack(), hasSize(2));
        assertThat(calc.getStackSize(), is(2));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(22), BigDecimal.valueOf(10)));
    }
    
    @Test
    public void shouldBeAdd() {
        
        // given
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        final BigDecimal expectedValue = new BigDecimal(20);
        
        // when
        Optional<BigDecimal> calculatedValue = calc.addition();
        
        // then
        assertThat(calculatedValue.isPresent(), is(true));
        assertEquals(expectedValue, calculatedValue.get());
        assertNotSame(expectedValue, calculatedValue.get());
        assertThat(calc.getStack(), hasSize(1));
        assertThat(calc.getStack(), hasItem(BigDecimal.valueOf(20)));
    }
    
    @Test
    public void shouldBeSubtract() {
        
        // given
        calc.addToStack(new BigDecimal(30));
        calc.addToStack(new BigDecimal(10));
        final BigDecimal expectedValue = new BigDecimal(20);
        
        // when
        Optional<BigDecimal> calculatedValue = calc.subtract();
        
        // then
        assertThat(calculatedValue.isPresent(), is(true));
        assertEquals(expectedValue, calculatedValue.get());
        assertThat(calc.getStack(), hasSize(1));
        assertThat(calc.getStack(), hasItem(BigDecimal.valueOf(20)));
    }
    
    @Test
    public void shouldBeMultiply() {
        
        // given
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        final BigDecimal expectedValue = new BigDecimal(100);
        
        // when
        Optional<BigDecimal> calculatedValue = calc.multiply();
        
        // then
        assertThat(calculatedValue.isPresent(), is(true));
        assertEquals(expectedValue, calculatedValue.get());
        assertThat(calc.getStack(), hasSize(1));
        assertThat(calc.getStack(), hasItem(BigDecimal.valueOf(100)));
    }
       
    @Test
    public void shouldBeDivide() {
        
        // given
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(2.45));
        final BigDecimal expectedValue = new BigDecimal("4.081632653061");
        
        // when
        Optional<BigDecimal> calculatedValue = calc.divide();
        
        // then
        assertThat(calculatedValue.isPresent(), is(true));
        assertEquals(expectedValue, calculatedValue.get());
        assertThat(calc.getStack(), hasSize(1));
        assertThat(calc.getStack(), hasItem(expectedValue));
    }
    
    @Test(expected = ArithmeticException.class)
    public void shouldBeDivisionByZero() {
        calc.addToStack(new BigDecimal(40));
        calc.addToStack(new BigDecimal(0));
        calc.divide();
    }
    
    @Test
    public void shouldBeCheckPrimeNumber() {
        calc.addToStack(new BigDecimal(29));
        calc.addToStack(new BigDecimal(42));
        assertThat(calc.isPrimeNumber(), is(false));
        calc.removeFromStack();
        assertThat(calc.isPrimeNumber(), is(true));
    }
}
