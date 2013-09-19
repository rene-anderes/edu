package org.anderes.edu.gui.pm.gui;

import java.util.List;

import org.anderes.edu.gui.domain.CalcIfc;

/**
 * Presetation-Model-Pattern für das GUI eines Taschenrechners
 * 
 * @author René Anderes
 */
public class PresentationModel {
	
	private final CalcIfc calc;

	/**
	 * Konstruktor
	 * 
	 * @param calc Domain (Rechner)
	 */
	public PresentationModel(final CalcIfc calc) {
		this.calc = calc;
	}
	
	/**
	 * Eingabe für den Stack
	 * 
	 * @param value Wert (Zahl)
	 */
	public void input(final String value) {
		if (value.matches("\\d+")) {
			calc.pushToStack(Double.parseDouble(value));
		}
	}
	
	/**
	 * Führt eine Funktion (Multiplizieren, Addition etc.) aus.<br>
	 * Dazu wird der übergebene Text analysiert.
	 * 
	 * @param command Kommando als Text
	 */
	public void command(final String command) {
		if (isMultiply(command)) {
			calc.multiply();
		} else if (isAddition(command)) {
			calc.addition();
		}
	}
	
	/**
	 * Gibt an, ob eine Funktion (Multiplizieren, Addition etc.)
	 * ausgeführt werden kann.
	 * 
	 * @return {@code true}, wenn min. zwei Werte auf dem Stack liegen.
	 */
	public boolean isCommandEnabled() {
		return calc.getStack().size() >= 2;
	}
	
	/**
	 * Gibt den Inhalt des Stacks des Taschenrechners wieder.
	 * 
	 * @return Inhalt des Stacks
	 */
	public List<Double> getStack() {
		return calc.getStack();
	}
	
	/**
	 * Check, ob es sich um eine Multiplikation handelt.
	 * 
	 * @param text Eingabetext
	 * @return {@code true}, wenn es ein Kommando für die Multiplikation ist
	 */
	private boolean isMultiply(final String text) {
		return text.matches("[*]");
	}
	
	/**
	 * Check, ob es sich um eine Addition handelt.
	 * 
	 * @param text Eingabetext
	 * @return {@code true}, wenn es ein Kommando für die Addition ist
	 */
	private boolean isAddition(final String text) {
		return text.matches("[+]");
	}

}
