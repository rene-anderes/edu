package org.anderes.edu.gui.sp;

import java.util.Observable;

import org.anderes.edu.gui.domain.CalcIfc;
import org.anderes.edu.gui.domain.CalcObservable;
import org.anderes.edu.gui.sp.gui.Presenter;
import org.anderes.edu.gui.sp.gui.View;
import org.anderes.edu.gui.sp.gui.ViewIfc;

/**
 * Architektur: Supervising Presenter
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
	CalcIfc calc = new CalcObservable();
	ViewIfc view = new View(calc, (Observable)calc);
	new Presenter(view, calc);
    }

}
