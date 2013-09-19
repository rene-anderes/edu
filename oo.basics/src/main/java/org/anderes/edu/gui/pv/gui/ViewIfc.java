package org.anderes.edu.gui.pv.gui;

import java.util.List;

/**
 * View Interface für das MVP-Pattern
 * 
 * @author René Anderes
 */
public interface ViewIfc {

	/**
	 * Aktualisiert die Anzeige des Stacks
	 * 
	 * @param values Werte des Stacks
	 */
	public void setStack(final List<Double> values);
	
	/**
	 * Setzt einen text für das Eingabefeld
	 * 
	 * @param text Text
	 */
	public void setTextForInputField(final String text);
	
	/**
	 * Liefert den aktuellen Wert des Eingabenfelds zurück.
	 * 
	 * @return Text des Eingabefelds
	 */
	public String getTextFromInputField();
	
	/**
	 * Anmelden eines Listeners für das Event-Handling
	 * 
	 * @param listener Listener
	 */
	public void addEventListener(final EventListener listener);

	/**
	 * Löscht die Anzeige des Eingabefeldes
	 */
	public void clearInputField();
	
	/**
	 * Wird eine ungültige Eingabe gemacht, wird das Eingabefeld gekennzeichnet
	 */
	public void unknownCommand();
}
