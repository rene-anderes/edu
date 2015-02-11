import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Einfacher Taschenrechner mit UPN
 * 
 * @author René Anderes
 */
public class Calc {

    private Deque<BigDecimal> stack = new ArrayDeque<BigDecimal>();

    /**
     * Fügt die Zahl zuoberts auf den Stack hinzu.
     * 
     * @param bigDecimal
     *            Zahl
     */
    public void addToStack(BigDecimal bigDecimal) {
        stack.push(bigDecimal);
    }

    /**
     * Liefert eine Liste mit allen Werten des Stacks
     * 
     * @return Liste
     */
    public Collection<Double> getStack() {
        List<Double> values = stack.stream().map(e -> e.doubleValue()).collect(Collectors.toList());
        return Collections.unmodifiableCollection(values);
    }
    
    /**
     * Liefert die aktuelle Grösses des Stacks zurück.
     * @return Stackgrösse
     */
    public int getStackSize() {
        return stack.size();
    }

    /**
     * Löscht den obersten Wert des Stacks
     */
    public void removeFromStack() {
        stack.remove();
    }

    /**
     * Addiert zwei Werte
     * 
     * @return Resultat
     */
    public BigDecimal addition() {
        if (stack.size() < 2) {
            return null;
        }
        BigDecimal result = stack.pop().add(stack.pop());
        stack.push(result);
        return stack.getFirst();
    }

    /**
     * Subtrahiert zwei Werte
     * 
     * @return Resultat
     */
    public BigDecimal subtract() {
        if (stack.size() < 2) {
            return null;
        }
        BigDecimal toSubtract = stack.pop();
        BigDecimal result = stack.pop().subtract(toSubtract);
        stack.push(result);
        return stack.getFirst();
    }

    /**
     * Multipliziert zwei Werte
     * 
     * @return Result
     */
    public BigDecimal multiply() {
        if (stack.size() < 2) {
            return null;
        }
        BigDecimal result = stack.pop().multiply(stack.pop());
        stack.push(result);
        return stack.getFirst();
    }
      
    /**
     * Dividiert zwei Werte
     * 
     * @return Resultat
     */
    public BigDecimal divide() {
        if (stack.size() < 2) {
            return null;
        }
        BigDecimal toDivide = stack.pop();
        BigDecimal result = stack.pop().divide(toDivide);
        stack.push(result);
        return stack.getFirst();
    }
}
