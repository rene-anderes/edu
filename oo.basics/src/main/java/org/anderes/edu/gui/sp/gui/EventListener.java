package org.anderes.edu.gui.sp.gui;

/**
 * Listener-Interface
 */
public interface EventListener {
	/**
	 * Wird aufgerufen, wenn der User im Eingabefeld etwas eingegeben hat.
	 */
	public void UserInput();

	/**
	 * NULL-Object-Pattern
	 */
	public static EventListener NULL = new EventListener() {
		@Override
		public void UserInput() {
			// Nothing to do....
		}
	};
}