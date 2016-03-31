/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CmdDateTimeIntegrationTest extends IntegrationTestBase {

	@Test
	public void getTime() {
		commandInvoker.executeCommand("Time", testOutput);

		assertTrue(testOutput.toString().toLowerCase().contains(
				"the current time is"));
	}

	@Test
	public void setTime() {
		String time = "21:30:10";

		commandInvoker.executeCommand("Time " + time, testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains( time));

		commandInvoker.executeCommand("time"  + time, testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains( time));
	}

	@Test
	public void setTimeSpecialFormat() {

		// Fails
		testOutput.reset();
		commandInvoker.executeCommand("Time gaga", testOutput);
		assertTrue(testOutput.toString().contains(
				"Wrong time format. It must be HH:MM:SS"));
		
		testOutput.reset();
		commandInvoker.executeCommand("Time 25", testOutput);
		assertTrue(testOutput.toString().contains(
				"Wrong time format. It must be HH:MM:SS"));

		testOutput.reset();
		commandInvoker.executeCommand("Time 12:70", testOutput);
		assertTrue(testOutput.toString().contains(
				"Wrong time format. It must be HH:MM:SS"));
	}

	@Test
	public void getDate() {
		commandInvoker.executeCommand("Date", testOutput);

		assertTrue(testOutput.toString().toLowerCase().contains(
				"the current date is"));
	}

	@Test
	public void setDate() {
		String date = "2007-03-11";

		commandInvoker.executeCommand("Date " + date, testOutput);
		assertTrue(testOutput.toString().contains(date));
	}

	@Test
	public void setDateSpecialFormat() {

		// Fails
		testOutput.reset();
		commandInvoker.executeCommand("Date gaga", testOutput);
		assertTrue(testOutput.toString().contains(
				"Wrong date format. It must be YYYY-MM-DD."));

		testOutput.reset();
		commandInvoker.executeCommand("Date 2007-02-29", testOutput);
		assertTrue(testOutput.toString().contains(
				"Invalid date input."));

		testOutput.reset();
		commandInvoker.executeCommand("Date 50", testOutput);
		assertTrue(testOutput.toString().contains(
				"Wrong date format. It must be YYYY-MM-DD."));
	}
	
	@Test
	public void midnight() {
		commandInvoker.executeCommand("date 2000-01-01", testOutput);
		assertTrue(testOutput.toString().contains("the current date is 2000-01-01"));
		testOutput.reset();
		
		String time = "23:59:59";
		commandInvoker.executeCommand("time " + time, testOutput);
		testOutput.reset();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			fail();
		}

		commandInvoker.executeCommand("date", testOutput);
		assertTrue(testOutput.toString().contains("the current date is 2000-01-02"));
		testOutput.reset();
	}
}
