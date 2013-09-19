package org.anderes.edu.gui.domain;
import static org.junit.Assert.*;

import org.anderes.edu.gui.domain.CalcIfc;
import org.anderes.edu.gui.domain.CalcObservable;
import org.junit.Before;
import org.junit.Test;




/**
 * Testklasse f�r die Schnittstelle des Taschenrechners
 * 
 * @author Ren� Anderes
 */
public class CalcTest {

	private CalcIfc calc;
	
	/**
	 * Setup vor jedem Test
	 */
	@Before
	public void setup() {
		calc = new CalcObservable();
	}
	
	/**
	 * Test die Stack-Funkionalit�t
	 */
	@Test
	public void stack() {
		assertNotNull(calc.getStack());
		assertEquals(0, calc.getStack().size());
		calc.pushToStack(1);
		assertEquals(1, calc.getStack().size());
		assertEquals(1D, calc.getStack().get(0), 0);
		calc.pushToStack(2);
		assertEquals(2, calc.getStack().size());
		assertEquals(2D, calc.getStack().get(0), 0);
		assertEquals(1D, calc.getStack().get(1), 0);
	}
	
	/**
	 * Testet die Addition
	 */
	@Test
	public void addition() {
		calc.pushToStack(4D);
		calc.pushToStack(5D);
		calc.addition();
		assertNotNull(calc.getStack());
		assertEquals(1, calc.getStack().size());
		assertEquals(9D, calc.getStack().get(0), 0);
	}
	
	/**
	 * Testet die Subtraktion
	 */
	@Test
	public void subtract() {
		calc.pushToStack(4D);
		calc.pushToStack(5D);
		calc.subtract();
		assertNotNull(calc.getStack());
		assertEquals(1, calc.getStack().size());
		assertEquals(-1D, calc.getStack().get(0), 0);
	}
	
	/**
	 * Testet die Multiplikation
	 */
	@Test
	public void multiply() {
		calc.pushToStack(4D);
		calc.pushToStack(5D);
		calc.multiply();
		assertNotNull(calc.getStack());
		assertEquals(1, calc.getStack().size());
		assertEquals(20D, calc.getStack().get(0), 0);
	}
	
	/**
	 * Testet die Multiplikation
	 */
	@Test
	public void divide() {
		calc.pushToStack(4D);
		calc.pushToStack(5D);
		calc.divide();
		assertNotNull(calc.getStack());
		assertEquals(1, calc.getStack().size());
		assertEquals((4D/5D), calc.getStack().get(0), 0);
	}
}
