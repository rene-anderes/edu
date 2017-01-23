package org.anderes.edu.di;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.inject.Inject;

/**
 * Einfacher Taschenrechner mit UPN
 * 
 * @author René Anderes
 */
public class Calc {

    private Service service;
    private Deque<BigDecimal> stack = new ArrayDeque<BigDecimal>();

    @Inject
    void setService(Service service) {
        this.service = service;
    }
    
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
     * @return Liste, LIFO Liste des Stacks
     */
    public Collection<BigDecimal> getStack() {
        return Collections.unmodifiableCollection(stack);
    }
    
    /**
     * Liefert die aktuelle Grösse des Stacks zurück.
     * @return Stackgrösse
     */
    public int getStackSize() {
        return stack.size();
    }

    /**
     * Löscht den obersten Wert des Stacks
     */
    public Optional<BigDecimal> removeFromStack() {
        try {
            return Optional.of(stack.remove());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    /**
     * Addiert zwei Werte
     * 
     * @return Resultat, Optional.empty wenn der Stack keine zwei Werte enthält
     */
    public Optional<BigDecimal> addition() {
        if (stack.size() < 2) {
            return Optional.empty();
        }
        BigDecimal result = stack.pop().add(stack.pop());
        stack.push(result);
        return Optional.of(stack.getFirst());
    }

    /**
     * Subtrahiert zwei Werte
     * 
     * @return Resultat, Optional.empty wenn der Stack keine zwei Werte enthält
     */
    public Optional<BigDecimal> subtract() {
        if (stack.size() < 2) {
            return Optional.empty();
        }
        BigDecimal toSubtract = stack.pop();
        BigDecimal result = stack.pop().subtract(toSubtract);
        stack.push(result);
        return Optional.of(stack.getFirst());
    }

    /**
     * Multipliziert zwei Werte
     * 
     * @return Resultat, Optional.empty wenn der Stack keine zwei Werte enthält
     */
    public Optional<BigDecimal> multiply() {
        if (stack.size() < 2) {
            return Optional.empty();
        }
        BigDecimal result = stack.pop().multiply(stack.pop());
        stack.push(result);
        return Optional.of(stack.getFirst());
    }
      
    /**
     * Dividiert zwei Werte
     * 
     * @return Resultat
     */
    public Optional<BigDecimal> divide() {
        if (stack.size() < 2) {
            return Optional.empty();
        }
        BigDecimal toDivide = stack.pop();
        BigDecimal result = stack.pop().divide(toDivide, MathContext.DECIMAL128);
        stack.push(result);
        return Optional.of(stack.getFirst());
    }

    public Boolean isPrimeNumber() {
        return service.isPrimeNumber(stack.peek().intValue());
    }
}
