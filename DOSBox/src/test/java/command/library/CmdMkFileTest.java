/*
 * Course Agile Software Development
 */ 
package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;
import filesystem.FileSystemItem;

public class CmdMkFileTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdMkFile("mkfile", drive));
	}

	@Test
	public void simpleContent() {
		String newFileName = "testFile";
		String newFileContent = "sTheContent";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfFilesBeforeTest = drive.getCurrentDirectory().getNumberOfFiles();
		
		commandInvoker.executeCommand("mkfile " + newFileName + " " + newFileContent, testOutput);
		
		assertEquals(testOutput.toString().length(), 0);
		assertEquals(drive.getCurrentDirectory().getNumberOfFiles(), nbOfFilesBeforeTest + 1);
		
		String newFilePath = drive.getCurrentDirectory().getPath() + "\\" + newFileName;
		FileSystemItem newItem = drive.getItemFromPath(newFilePath);
		
		assertNotNull(newItem);
		assertFalse(newItem.isDirectory());
		assertEquals(newItem.getName().compareTo(newFileName), 0);
		assertEquals(((File)newItem).getFileContent().compareTo(newFileContent), 0);
	}
	
	@Test
	public void creationWithNoContent() {
		String newFileName = "testFile";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfFilesBeforeTest = drive.getCurrentDirectory().getNumberOfFiles();
		
		commandInvoker.executeCommand("mkfile " + newFileName, testOutput);
		
		assertEquals(testOutput.toString(), 0, testOutput.toString().length());
		assertEquals(drive.getCurrentDirectory().getNumberOfFiles(), nbOfFilesBeforeTest + 1);
		
		String newFilePath = drive.getCurrentDirectory().getPath() + "\\" + newFileName;
		FileSystemItem newItem = drive.getItemFromPath(newFilePath);
		
		assertNotNull(newItem);
		assertFalse(newItem.isDirectory());
		assertEquals(newItem.getName().compareTo(newFileName), 0);
		assertEquals("", ((File)newItem).getFileContent());
	}
	
	@Test
	public void noParameters() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfFilesBeforeTest = drive.getCurrentDirectory().getNumberOfFiles();
		
		commandInvoker.executeCommand("mkfile", testOutput);
		
		// No file shall be created
		assertEquals(nbOfFilesBeforeTest, drive.getCurrentDirectory().getNumberOfFiles());
		// The following string must be output
		assertEquals("The syntax of the command is incorrect.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
}
