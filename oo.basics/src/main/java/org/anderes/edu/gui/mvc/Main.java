package org.anderes.edu.gui.mvc;

import org.anderes.edu.gui.domain.CalcObservable;
import org.anderes.edu.gui.mvc.gui.View;
import org.anderes.edu.gui.mvc.gui.ViewAlternative;

/**
 * Architektur: Model View Controller (MVC)
 * 
 * Starter für die Applikation
 * 
 * @author René Anderes
 */
public class Main {

    /**
     * Main-Methode
     * 
     * @param args
     */
    public static void main(String[] args) {
	// Model instanzieren
	CalcObservable calc = new CalcObservable();
	// Main View instanzieren
	View view = new View();
	view.initialize(calc);
	if (args != null && args.length > 0) {
	    // Alternative View instanzieren
	    ViewAlternative viewAlternative = new ViewAlternative();
	    viewAlternative.initialize(calc);
	}
    }
}
