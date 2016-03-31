/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CmdHelpIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void withoutArgument() {
		commandInvoker.executeCommand("help", testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("dir"));
		assertTrue(testOutput.toString().toLowerCase().contains("mkdir"));
		assertTrue(testOutput.toString().toLowerCase().contains("mkfile"));
	}
	
	@Test
	public void withArgument() {
		commandInvoker.executeCommand("help dir", testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("directories"));
	}
}
