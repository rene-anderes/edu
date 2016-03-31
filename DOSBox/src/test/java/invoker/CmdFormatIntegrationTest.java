/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CmdFormatIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void formatYDefault() {
		drive.setCurrentDirectory(dirRoot);
		testOutput.setCharacterThatIsRead('Y');
		commandInvoker.executeCommand("format", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getRootDirectory().getNumberOfDirectories() == 0);
		assertTrue(drive.getRootDirectory().getNumberOfFiles() == 0);
	}
	
	@Test
	public void formatY() {
		drive.setCurrentDirectory(dirRoot);
		testOutput.setCharacterThatIsRead('Y');
		commandInvoker.executeCommand("format c:", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getRootDirectory().getNumberOfDirectories() == 0);
		assertTrue(drive.getRootDirectory().getNumberOfFiles() == 0);
	}
	
	@Test
	public void formatN() {
		drive.setCurrentDirectory(dirTemp);
		testOutput.setCharacterThatIsRead('N');
		int nbOfDirs = drive.getCurrentDirectory().getNumberOfDirectories();
		int nbOfFiles = drive.getCurrentDirectory().getNumberOfFiles();

		commandInvoker.executeCommand("format c:", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getCurrentDirectory() == dirTemp);
		assertTrue(drive.getCurrentDirectory()
				.getNumberOfDirectories() == nbOfDirs);
		assertTrue(drive.getCurrentDirectory().getNumberOfFiles() == nbOfFiles);
	}
	
	@Test
	public void formatForce() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("format /f C:", testOutput);

		assertTrue(!testOutput.characterWasRead());
		assertTrue(drive.getRootDirectory()
				.getNumberOfDirectories() == 0);
		assertTrue(drive.getRootDirectory().getNumberOfFiles() == 0);
	}

	@Test
	public void formatForceDefault() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("format /F", testOutput);

		assertTrue(!testOutput.characterWasRead());
		assertTrue(drive.getRootDirectory()
				.getNumberOfDirectories() == 0);
		assertTrue(drive.getRootDirectory().getNumberOfFiles() == 0);
		assertTrue(drive.getCurrentDirectory().getPath()
				.toLowerCase().compareTo("c:") == 0);
	}
	
	@Test
	public void formatWrongDriveLetter() {
		drive.setCurrentDirectory(dirTemp);
		int nbOfDirs = drive.getCurrentDirectory()
				.getNumberOfDirectories();
		int nbOfFiles = drive.getCurrentDirectory().getNumberOfFiles();
		commandInvoker.executeCommand("format z:", testOutput);

		assertTrue(testOutput.toString().toLowerCase().contains(
				"specified drive does not exist"));
		assertTrue(drive.getCurrentDirectory() == dirTemp);
		assertTrue(drive.getCurrentDirectory()
				.getNumberOfDirectories() == nbOfDirs);
		assertTrue(drive.getCurrentDirectory().getNumberOfFiles() == nbOfFiles);
	}

	@Test
	public void formatResetOfCurrrentDirectory() {
		drive.setCurrentDirectory(dirTemp);
		testOutput.setCharacterThatIsRead('Y');
		commandInvoker.executeCommand("format c:", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getCurrentDirectory().getPath()
				.toLowerCase().compareTo("c:") == 0);
	}
}
