/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CmdTreeIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void tree() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("tree .", testOutput);
		
		String output = testOutput.toString();
		checkStandardConditions(output);
	}
	
	@Test
	public void treeWithOptionF() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("tree . /f", testOutput);
		
		String output = testOutput.toString();
		checkStandardConditions(output);
		assertTrue(output.contains(fileGaga.getName()));
		assertTrue(output.contains(fileLog.getName()));
		assertTrue(output.contains(fileMyStuff.getName()));
		assertTrue(output.contains(fileTest1.getName()));
		assertTrue(output.contains(fileTest2.getName()));
		assertTrue(output.contains(fileTest3.getName()));
	}
	
	@Test
	public void treeWithoutPath() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("tree", testOutput);
		
		String output = testOutput.toString();
		checkStandardConditions(output);
	}
	
	@Test
	public void treeWithOtherPath() {
		drive.setCurrentDirectory(dirProgramFiles);
		commandInvoker.executeCommand("tree " + dirTemp.getPath(), testOutput);
		
		String output = testOutput.toString();
		checkStandardConditions(output);
	}

	private void checkStandardConditions(String output) {
		assertTrue(output.contains(dirTemp.getPath()));
		assertTrue(output.contains("+---" + dirTestDir1.getName()));
		assertTrue(output.contains("\\---" + dirTestDir2.getName()));
	}	
}
