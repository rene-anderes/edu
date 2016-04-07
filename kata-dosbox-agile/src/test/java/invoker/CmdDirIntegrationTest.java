/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CmdDirIntegrationTest extends IntegrationTestBase {

	/**
	 * Tests whether dir /w outputs the files in []
	 */
	@Test
	public void optionWOutput() {
		String output = null;

		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("DIR /W", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.length() != 0);
		assertTrue(output.contains("[temp]"));
		assertTrue(output.contains("[windows]"));
		assertTrue(output.contains("[programfiles]"));
		assertTrue(output.contains("3 dir(s)"));
	}

	/**
	 * Tests whether the output of dir /w is smaller than the output of a normal
	 * dir command.
	 */
	@Test
	public void optionWForShortOutput() {
		String outputWithOptionW = null;
		String outputWithoutOption = null;

		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("DIR /W", testOutput);
		outputWithOptionW = testOutput.toString().toLowerCase();

		testOutput.reset();
		commandInvoker.executeCommand("DIR", testOutput);
		outputWithoutOption = testOutput.toString().toLowerCase();

		assertTrue(outputWithOptionW.length() < outputWithoutOption.length());
	}

	/**
	 * Tests whether dir /s outputs directories and subdirectories
	 */
	@Test
	public void optionSForTempDirCheckListedDirectories() {
		String output = null;

		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("DIR /S", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.length() != 0);
		assertTrue(output.contains("directory of c:\\temp"));
		assertTrue(output.contains("directory of c:\\temp\\testdir1"));
		assertTrue(output.contains("directory of c:\\temp\\testdir2"));
		assertTrue(output.contains("total files listed:\n\t6 file(s) 53 bytes\n\t3 dir(s)"));

	}

	/**
	 * Tests whether dir /s outputs the files contained in the directory and the
	 * subdirectories
	 */
	@Test
	public void optionSForTempDirCheckListedFiles() {
		String output = null;

		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("DIR /S", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.length() != 0);
		assertTrue(output.contains(fileTest1.getName().toLowerCase()));
		assertTrue(output.contains(fileGaga.getName().toLowerCase()));
	}

	/**
	 * Tests whether dir /s outputs a summary at the end like<br>
	 * <br>
	 * Total Files Listed:<br>
	 * 6 File(s) XX bytes<br>
	 * 3 Dir(s)
	 */
	@Test
	public void optionSForTempDirCheckSummaryAtEnd() {
		String output = null;

		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("DIR /S", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.length() != 0);
		assertTrue(output.contains("total files listed"));
		// Test number of total files in \temp and subdirs
		assertTrue(output.contains("6 file(s)"));
	}

	/**
	 * Tests whether dir /w also outputs correct format when executed on a
	 * directory without any subdirectories.
	 */
	@Test
	public void optionSForAnEmptyDirectory() {
		String output = null;

		drive.setCurrentDirectory(dirTestDir2);
		commandInvoker.executeCommand("DIR /S", testOutput);
		output = testOutput.toString().toLowerCase();
		assertTrue(output.length() != 0);
		assertTrue(output.contains("0 file"));
	}
	 /**
	  * Test f�r die Kombination der Optionen /s und /w
	  */
	@Test
	public void optionSW() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("DIR /W /S", testOutput);
		String outputWithOption = testOutput.toString().toLowerCase();
		assertTrue(outputWithOption.contains("[windows]"));
		assertTrue(outputWithOption.contains("[programfiles]"));
		assertTrue(outputWithOption.contains("microsoft.net"));
	}
}
