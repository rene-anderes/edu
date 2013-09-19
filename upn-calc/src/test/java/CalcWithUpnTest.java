import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Testklasse welche das Verhalten eines 
 * Taschenrechners mit UPN testet.
 * 
 * @author René Anderes
 */
public class CalcWithUpnTest {

    @Test
    public void addAndRemoveStackValue() {
        Calc calc = new Calc();
        calc.addToStack(new BigDecimal(10));
        assertEquals(1, calc.getStack().size());
        calc.removeFromStack();
        assertEquals(0, calc.getStack().size());
    }
    
    @Test
    public void shouldBeAdd() {
        Calc calc = new Calc();
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(20);
        BigDecimal calculatedValue = calc.addition();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test
    public void shouldBeSubtract() {
        Calc calc = new Calc();
        calc.addToStack(new BigDecimal(30));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(20);
        BigDecimal calculatedValue = calc.subtract();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test
    public void shouldBeMultiply() {
        Calc calc = new Calc();
        calc.addToStack(new BigDecimal(10));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(100);
        BigDecimal calculatedValue = calc.multply();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
    
    @Test
    public void shouldBeDivide() {
        Calc calc = new Calc();
        calc.addToStack(new BigDecimal(40));
        calc.addToStack(new BigDecimal(10));
        BigDecimal expectedValue = new BigDecimal(4);
        BigDecimal calculatedValue = calc.divide();
        assertNotNull(calculatedValue);
        assertEquals(expectedValue, calculatedValue);
    }
}
