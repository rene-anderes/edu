package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class CmdRenameTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdRename("ren", drive));
		commandInvoker.addCommand(new CmdRename("rename", drive));
	}

	@Test
	public void missingParameters0Ren() {
		missingParameters("ren");
	}

	@Test
	public void missingParameters0Rename() {
		missingParameters("rename");
	}
	
	@Test
	public void missingParameters1Ren() {
		missingParameters("ren foo");
	}
	
	@Test
	public void missingParameters1Rename() {
		missingParameters("rename foo");
	}

	@Test
	public void sourceNotExistent() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("ren foo bar" , testOutput);
		assertTrue(removeTrailingNewLine(testOutput.toString()).contains("Source not found"));
	}
	
	@Test
	public void destinationFileExists() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("ren FileInRoot1 FileInRoot2" , testOutput);
		assertTrue(removeTrailingNewLine(testOutput.toString()).contains("Destination already exists"));
	}
	
	@Test
	public void destinationDirectoryExists() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("ren subDir1 subDir2" , testOutput);
		assertTrue(removeTrailingNewLine(testOutput.toString()).contains("Destination already exists"));
	}
	
	@Test
	public void renameFile() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		final int nbOfFilesBeforeTest = drive.getRootDirectory().getNumberOfFiles();
		final int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("rename FileInRoot1 xxx" , testOutput);

		assertEquals(nbOfFilesBeforeTest, drive.getRootDirectory().getNumberOfFiles());
		assertEquals(nbOfDirsBeforeTest, drive.getRootDirectory().getNumberOfDirectories());
		
		assertEquals(drive.getItemFromPath("C:\\FileInRoot1"), null);
		assertTrue(drive.getItemFromPath("C:\\xxx") != null);
	}
	
	@Test
	public void renameDirectory() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		final int nbOfFilesBeforeTest = drive.getRootDirectory().getNumberOfFiles();
		final int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("rename subDir1 yyy" , testOutput);

		assertEquals(nbOfFilesBeforeTest, drive.getRootDirectory().getNumberOfFiles());
		assertEquals(nbOfDirsBeforeTest, drive.getRootDirectory().getNumberOfDirectories());
		
		assertEquals(drive.getItemFromPath("C:\\subDir1"), null);
		assertTrue(drive.getItemFromPath("C:\\yyy") != null);
	}
	
	private void missingParameters(final String command) {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand(command , testOutput);
		assertEquals("The syntax of the command is incorrect.\nWrong parameter entered",
					 removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void illegalDestinationNameEnd() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("ren FileInRoot1 bar\\\\" , testOutput);
		assertEquals("Invalid Destination.\nWrong parameter entered",
				 removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void destinationNameEndsWithBackslash() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("ren FileInRoot1 bar\\" , testOutput);
		assertFalse(removeTrailingNewLine(testOutput.toString()).contains("Invalid Destination"));
	}
}
