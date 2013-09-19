package org.anderes.edu.gui.pv.gui;

import org.anderes.edu.gui.domain.CalcIfc;

/**
 * Presenter-Klasse für das MVP-Pattern
 * 
 * @author René Anderes
 */
public class Presenter implements EventListener {

	private ViewIfc view;
	private CalcIfc calc;
	
	/**
	 * Konstruktor (MVP-Pattern)
	 * 
	 * @param view View
	 * @param calc Model
	 */
	public Presenter(final ViewIfc view, final CalcIfc calc) {
		super();
		this.view = view;
		this.calc = calc;
		view.addEventListener(this);
	}

	@Override
	public void UserInput() {
		String text = view.getTextFromInputField();
		int stackSize = calc.getStack().size();
		if (isDigit(text)) {
			view.clearInputField();
			calc.pushToStack(Double.parseDouble(text));
		} else if (isMultiply(text) && stackSize >= 2) {
			view.clearInputField();
			calc.multiply();
		}  else if (isAddition(text) && stackSize >= 2) {
			view.clearInputField();
			calc.addition();
		} else {
			view.unknownCommand();
		}
		view.setStack(calc.getStack());
	}
	
	/**
	 * Check, ob es eine Zahl ist.
	 * 
	 * @param text Eingabetext
	 * @return <code>true</code>, wenn es eine Zahl ist.
	 */
	private boolean isDigit(final String text) {
		return text.matches("\\d+");
	}
	
	/**
	 * Check, ob es sich um eine Multiplikation handelt.
	 * 
	 * @param text Eingabetext
	 * @return <code>true</code>, wenn es ein Kommando für die Multiplikation ist
	 */
	private boolean isMultiply(final String text) {
		return text.matches("[*]");
	}
	
	/**
	 * Check, ob es sich um eine Addition handelt.
	 * 
	 * @param text Eingabetext
	 * @return <code>true</code>, wenn es ein Kommando für die Addition ist
	 */
	private boolean isAddition(final String text) {
		return text.matches("[+]");
	}

}
