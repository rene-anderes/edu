package command.framework;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test für den speziellen Wrapper für den Zeilenumbruch
 * 
 * @author ra, hum
 */
public class TestWrapper {

	/**
	 * Zeilenumbruch nach 60 Zeichen
	 */
	@Test
	public void wrapperPrint() {
		TestOutput outputter = new TestOutput();
		Wrapper wrapper = new Wrapper(outputter, 60);
		for (int i = 0; i < 6; i++) {
			wrapper.print("1234567890");
			wrapper.print(" ");
		}
		String expected = "1234567890" + " "
			+ "1234567890" + " "
			+ "1234567890" + " "
			+ "1234567890" + " "
			+ "1234567890" + " "
			+ "\n"
			+ "1234567890" + " ";
		assertEquals(expected, outputter.toString());
	}
	
	/**
	 * Langer Dateiname am Anfang
	 */
	@Test
	public void wrapperPrintForBigFileNameAtBegin() {
		TestOutput outputter = new TestOutput();
		Wrapper wrapper = new Wrapper(outputter, 60);
		String text = "";
		for (int i = 0; i < 7; i++) {
			text += "1234567890";
		}
		wrapper.print(text);
		
		String expected = "1234567890"
			+ "1234567890"
			+ "1234567890"
			+ "1234567890"
			+ "1234567890"
			+ "1234567...\n";
		assertEquals(expected, outputter.toString());
	}
	
	/**
	 * Langer Dateiname NICHT am Anfang
	 */
	@Test
	public void wrapperPrintForBigFileName() {
		TestOutput outputter = new TestOutput();
		Wrapper wrapper = new Wrapper(outputter, 60);
		for (int i = 0; i < 4; i++) {
			wrapper.print("1234567890");
			wrapper.print(" ");
		}
		String text = "";
		for (int i = 0; i < 7; i++) {
			text += "1234567890";
		}
		wrapper.print(text);
		String expected = "1234567890" + " " 
			+ "1234567890" + " "
			+ "1234567890" + " " 
			+ "1234567890" + " "
			+ "\n" 
			+ "1234567890"
			+ "1234567890" 
			+ "1234567890" 
			+ "1234567890" 
			+ "1234567890"
			+ "1234567..." + "\n";
		assertEquals(expected, outputter.toString());
	}
	
	/**
	 * Ersetzen der Tab durch <space>
	 */
	@Test
	public void replaceTab() {
		TestOutput outputter = new TestOutput();
		Wrapper wrapper = new Wrapper(outputter, 60);
		String text = "1234\t1234";
		wrapper.print(text);
		String expected = "1234    1234";
		assertEquals(expected, outputter.toString());
	}
}
