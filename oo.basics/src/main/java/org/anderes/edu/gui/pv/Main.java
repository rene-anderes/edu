package org.anderes.edu.gui.pv;

import org.anderes.edu.gui.domain.Calc;
import org.anderes.edu.gui.domain.CalcIfc;
import org.anderes.edu.gui.pv.gui.Presenter;
import org.anderes.edu.gui.pv.gui.View;
import org.anderes.edu.gui.pv.gui.ViewIfc;

/**
 * Architektur: Passive View
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
		ViewIfc view = new View();
		CalcIfc calc = new Calc();
		new Presenter(view, calc);
	}

}
