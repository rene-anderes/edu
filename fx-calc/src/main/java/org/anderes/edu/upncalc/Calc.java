package org.anderes.edu.upncalc;

import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;
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

    private final static Integer MAX_SCALE = 12;
    private final Service service;
    private final Deque<BigDecimal> stack = new ArrayDeque<>();
    private final Deque<BigDecimal> undoStack = new ArrayDeque<>();

    @Inject
    public Calc(Service service) {
        super();
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
        undoStack.clear();
        undoStack.addAll(stack);
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
        return function((d1, d2) -> d2.add(d1, DECIMAL128));
    }

    /**
     * Subtrahiert zwei Werte
     * 
     * @return Resultat, Optional.empty wenn der Stack keine zwei Werte enthält
     */
    public Optional<BigDecimal> subtract() {
        return function((d1, d2) -> d2.subtract(d1, DECIMAL128));
    }

    /**
     * Multipliziert zwei Werte
     * 
     * @return Resultat, Optional.empty wenn der Stack keine zwei Werte enthält
     */
    public Optional<BigDecimal> multiply() {
        return function((d1, d2) -> d2.multiply(d1, DECIMAL128));
    }
    

    public Optional<BigDecimal> inverse() {
        return function(d -> BigDecimal.ONE.divide(d, DECIMAL128));
    }
    
    /**
     * Dividiert zwei Werte
     * 
     * @return Resultat
     */
    public Optional<BigDecimal> divide() {
        return function((divisor, dividend) -> dividend.divide(divisor, DECIMAL128));
    }
      
    @FunctionalInterface
    interface BiCalcFunction<T, U> {

        BigDecimal eval(T t, U u);
    }
    
    @FunctionalInterface
    interface CalcFunction<T> {

        BigDecimal eval(T t);
    }

    private Optional<BigDecimal> function(BiCalcFunction<BigDecimal, BigDecimal> c) {
        if (stack.size() < 2) {
            return Optional.empty();
        }
        undoStack.clear();
        undoStack.addAll(stack);
        BigDecimal result = c.eval(stack.pop(), stack.pop());
        if (result.scale() > MAX_SCALE) {
            result = result.setScale(MAX_SCALE, HALF_EVEN);
        }
        stack.push(result);
        return Optional.of(stack.getFirst());
    }
    
    private Optional<BigDecimal> function(CalcFunction<BigDecimal> c) {
        if (stack.size() < 1) {
            return Optional.empty();
        }
        undoStack.clear();
        undoStack.addAll(stack);
        BigDecimal result = c.eval(stack.pop());
        if (result.scale() > MAX_SCALE) {
            result = result.setScale(MAX_SCALE, HALF_EVEN);
        }
        stack.push(result);
        return Optional.of(stack.getFirst());
    }
    
    public Boolean isPrimeNumber() {
        return service.isPrimeNumber(stack.peek().intValue());
        
    }

    public void undo() {
        stack.clear();
        stack.addAll(undoStack);
    }
}
