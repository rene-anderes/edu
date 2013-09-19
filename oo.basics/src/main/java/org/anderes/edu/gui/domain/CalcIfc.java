package org.anderes.edu.gui.domain;
import java.util.List;

/**
 * Schnittstelle für einen Taschenrechner mit UPN
 *
 * @author René Anderes
 */
public interface CalcIfc {
	
    /**
     * Multipliziiert die ersten beiden Werte vom Stack.<br>
     * Die ersten beiden Werte werden vom Stack entfernt und 
     * der berechnete Wert wird im Stack abgelegt.
     */
    public void multiply();
    
    /**
     * Dividiert die ersten beiden Werte vom Stack.<br>
     * Die ersten beiden Werte werden vom Stack entfernt und 
     * der berechnete Wert wird im Stack abgelegt.
     */
    public void divide();
    	
    /**
     * Addiert die ersten beiden Werte vom Stack.<br>
     * Die ersten beiden Werte werden vom Stack entfernt und 
     * der berechnete Wert wird im Stack abgelegt.
     */
    public void addition();
    
    /**
     * Subtrahiert die ersten beiden Werte vom Stack.<br>
     * Die ersten beiden Werte werden vom Stack entfernt und 
     * der berechnete Wert wird im Stack abgelegt.
     */
    public void subtract();
    
    /**
     * Liefert den kompletten Stack als Liste zurück.<br>
     * Der erste Werte entspricht dem zuletzt auf den Stack
     * gelegten Wert.
     * 
     * @return Liste von Werten
     */
    public List<Double> getStack();
    
    /**
     * Entfernt den obersten Wert vom Stack (LIFO).
     * 
     * @return Wert vom Stack
     */
    public void removeFromStack();
    
    /**
     * Legt einen Wert im Stack (LIFO) des Taschenrechners ab.
     * 
     * @param value Wert für den Stack
     */
    public void pushToStack(double value);
}
