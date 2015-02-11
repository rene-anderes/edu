import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;


/**
 * Testklasse welche das Verhalten eines 
 * Taschenrechners mit UPN testet.
 * 
 * @author René Anderes
 */
public class CalcWithUpnTest {
    
    private Calc calc;
    
    @Before
    public void setup() {
        calc = new Calc();
    }

    @Test
    public void addAndRemoveStackValue() {
        calc.addToStack(new BigDecimal(10));
        assertEquals(1, calc.getStackSize());
        assertThat(calc.getStack().size(), is(1));
        calc.removeFromStack();
        assertEquals(0, calc.getStackSize());
        assertThat(calc.getStack().size(), is(0));
    }
    
    @Test
    public void shouldBeAdd() {
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(20);
        BigDecimal calculatedValue = calc.addition();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test
    public void shouldBeSubtract() {
        calc.addToStack(new BigDecimal(30));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(20);
        BigDecimal calculatedValue = calc.subtract();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test
    public void shouldBeMultiply() {
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(100);
        BigDecimal calculatedValue = calc.multiply();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
       
    @Test
    public void shouldBeDivide() {
        calc.addToStack(new BigDecimal(40));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(4);
        BigDecimal calculatedValue = calc.divide();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test(expected = ArithmeticException.class)
    public void shouldBeDivisionByZero() {
        calc.addToStack(new BigDecimal(40));
        calc.addToStack(new BigDecimal(0));
        calc.divide();
    }
}
