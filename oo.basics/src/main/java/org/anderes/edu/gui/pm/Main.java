package org.anderes.edu.gui.pm;

import org.anderes.edu.gui.domain.Calc;
import org.anderes.edu.gui.domain.CalcIfc;
import org.anderes.edu.gui.pm.gui.PresentationModel;
import org.anderes.edu.gui.pm.gui.View;

/**
 * Architektur: Presentation Model
 *
 * Starter für die Applikation
 *
 * @author René Anderes
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CalcIfc calc = new Calc();
		PresentationModel presentationModel = new PresentationModel(calc);
		new View(presentationModel);
	}
}
