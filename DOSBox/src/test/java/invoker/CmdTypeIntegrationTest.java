/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CmdTypeIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void output() {
		commandInvoker.executeCommand("type " + fileTest1.getPath(),
				testOutput);

		assertTrue(testOutput.toString().contains(
				fileTest1.getFileContent()));
	}

	@Test
	public void notExistingFile() {
		commandInvoker.executeCommand("type NotExistingFile.gugus",
				testOutput);

		assertTrue(testOutput.toString().toLowerCase().contains(
				"cannot find the file specified"));
	}
}
