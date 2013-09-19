import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * Einfacher Taschenrechner mit UPN
 * 
 * @author René Anderes
 */
public class Calc {

    private Deque<BigDecimal> stack = new ArrayDeque<BigDecimal>();
    
    /**
     * Fügt die Zahl zuoberts auf den Stack hinzu.
     * @param bigDecimal Zahl
     */
    public void addToStack(BigDecimal bigDecimal) {
        stack.push(bigDecimal);
    }

    /**
     * Liefert eine Liste mit allen Werten des Stacks
     * @return Liste
     */
    public Collection<BigDecimal> getStack() {
        return stack;
    }

    /**
     * Löscht den obersten Wert des Stacks
     */
    public void removeFromStack() {
        stack.remove();
    }

    /**
     * Addiert zwei Werte
     * @return Resultat
     */
    public BigDecimal addition() {
        if (stack.size() >= 2) {
            BigDecimal result = stack.pop().add(stack.pop());
            stack.push(result);
            return stack.getFirst();
        }
        return null;
    }

    /**
     * Subtrahiert zwei Werte
     * @return Resultat
     */
    public BigDecimal subtract() {
        if (stack.size() >= 2) {
            BigDecimal toSubtract = stack.pop();
            BigDecimal result = stack.pop().subtract(toSubtract);
            stack.push(result);
            return stack.getFirst();
        }
        return null;
    }

    /**
     * Multipliziert zwei Werte
     * @return Result
     */
    public BigDecimal multply() {
        if (stack.size() >= 2) {
            BigDecimal result = stack.pop().multiply(stack.pop());
            stack.push(result);
            return stack.getFirst();
        }
        return null;
    }

    /**
     * Dividiert zwei Werte
     * @return Resultat
     */
    public BigDecimal divide() {
        if (stack.size() >= 2) {
            BigDecimal toDivide = stack.pop();
            BigDecimal result = stack.pop().divide(toDivide);
            stack.push(result);
            return stack.getFirst();
        }
        return null;
    }
}
