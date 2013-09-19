/**
 * 
 */
package org.anderes.edu.gui.pm.gui;

import static org.junit.Assert.*;

import org.anderes.edu.gui.domain.Calc;
import org.anderes.edu.gui.domain.CalcIfc;
import org.anderes.edu.gui.pm.gui.PresentationModel;
import org.junit.Test;


/**
 * Test f�r das Presentation-Model
 * 
 * @author Ren� Anderes
 */
public class PresentationModelTest {

	/**
	 * Testet die Klasse {@link PresentationModel}
	 */
	@Test
	public void presentationModelTest() {
		CalcIfc calc = new Calc();
		PresentationModel presentationModel = new PresentationModel(calc);
		assertFalse(presentationModel.isCommandEnabled());
		presentationModel.input("10089");
		assertEquals(1, presentationModel.getStack().size());
		assertEquals(calc.getStack().get(0), 10089d, 0d);
		assertFalse(presentationModel.isCommandEnabled());
		presentationModel.input("100");
		assertTrue(presentationModel.isCommandEnabled());
		presentationModel.command("+");
		assertEquals(1, presentationModel.getStack().size());
		assertFalse(presentationModel.isCommandEnabled());
		assertEquals(calc.getStack().get(0), 10189d, 0d);
		presentationModel.input("10");
		assertTrue(presentationModel.isCommandEnabled());
		presentationModel.command("*");
		assertEquals(1, presentationModel.getStack().size());
		assertFalse(presentationModel.isCommandEnabled());
		assertEquals(calc.getStack().get(0), 101890d, 0d);
	}
}
