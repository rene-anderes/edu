/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CmdVerIntegrationTest extends IntegrationTestBase {

	@Test
	public void verNoOptions() {
		String output = null;
		String[] test = null;

		commandInvoker.executeCommand("VER", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.contains("dosbox"));
		assertTrue(output.contains("\n"));
		assertTrue(output.contains("invalid") == false);
		assertTrue(output.contains("incorrect") == false);
		test = output.split("\n");
		assertTrue(test.length>1);
		assertTrue(test[0].trim().length()>0);

	}
	
	@Test
	public void verOptionWAllUpperCase() {
		runAndTestVerWithOptionW("VER /W");
	}
	
	@Test
	public void verOptionWOptionLowerCase() {
		runAndTestVerWithOptionW("VER /w");
	}
	
	@Test
	public void verOptionWAllOptionUpperCase() {
		runAndTestVerWithOptionW("ver /W");
	}
	
	@Test
	public void verOptionWAllLowerCase() {
		runAndTestVerWithOptionW("ver /w");
	}
	
	@Test
	public void verForPoint6()
	{
		verNoOptions();
	}
	
	private void runAndTestVerWithOptionW(String cmd)
	{
		String output = null;

		commandInvoker.executeCommand(cmd, testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.matches("[0-9]{1,3}\\.[0-9]{1,3}\\s*"));
	}
}
