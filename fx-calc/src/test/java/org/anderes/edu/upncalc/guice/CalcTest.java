package org.anderes.edu.upncalc.guice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.anderes.edu.upncalc.Calc;
import org.anderes.edu.upncalc.PrimeNumberService;
import org.anderes.edu.upncalc.Service;
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
            bind(Service.class).to(PrimeNumberService.class);
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
        assertThat(calc.getStack(), hasItem(BigDecimal.valueOf(10L)));
        
        calc.addToStack(new BigDecimal(22D));
        calc.addToStack(new BigDecimal(33D));
        assertThat(calc.getStack(), hasSize(3));
        assertThat(calc.getStackSize(), is(3));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(33L), BigDecimal.valueOf(22L), BigDecimal.valueOf(10L)));

        assertThat(calc.removeFromStack().isPresent(), is(true));
        assertThat(calc.getStack(), hasSize(2));
        assertThat(calc.getStackSize(), is(2));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(22L), BigDecimal.valueOf(10L)));
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
    
    @Test
    public void shouldBeUndo() {
        
        // given
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(20));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(20L), BigDecimal.valueOf(10L)));
        
        // when
        calc.addition();
        calc.undo();
        
        // then
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(20L), BigDecimal.valueOf(10L)));
    }
    
    @Test
    public void shouldBeNotUndo() {
        
        // given
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(20));
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(20L), BigDecimal.valueOf(10L)));
        
        // when
        calc.undo();
        
        // then
        assertThat(calc.getStack(), contains(BigDecimal.valueOf(20L), BigDecimal.valueOf(10L)));
    }
    
    @Test
    public void shouldBeInverse() {
        // given
        calc.addToStack(new BigDecimal(5));
        
        // when
        final Optional<BigDecimal> inverse = calc.inverse();
        
        // then
        assertThat(inverse, is(notNullValue()));
        assertThat(inverse.isPresent(), is(true));
        assertThat(inverse.get(), is(BigDecimal.valueOf(0.2)));
    }
    
    @Test
    public void shouldBeSquared() {
        // given
        calc.addToStack(new BigDecimal(5));
        
        // when
        final Optional<BigDecimal> squared = calc.squared();
        
        // then
        assertThat(squared, is(notNullValue()));
        assertThat(squared.isPresent(), is(true));
        assertThat(squared.get(), is(BigDecimal.valueOf(25)));
    }
    
    @Test
    public void shouldBeSquaredRoot() {
        // given
        calc.addToStack(new BigDecimal(25));
        
        // when
        final Optional<BigDecimal> squaredRoot = calc.squaredRoot();
        
        // then
        assertThat(squaredRoot, is(notNullValue()));
        assertThat(squaredRoot.isPresent(), is(true));
        assertThat(squaredRoot.get(), is(BigDecimal.valueOf(5)));
    }
    
    @Test(expected = NumberFormatException.class)
    public void shouldBeSquaredRootNaN() {
        // given
        calc.addToStack(new BigDecimal(-9));
        
        // when
        calc.squaredRoot();
    }
    
    @Test
    public void shouldBePiValue() {

        // when
        final BigDecimal pi = calc.pi();
        
        assertThat(pi.toString(), is("3.141592653590"));
    }
}
