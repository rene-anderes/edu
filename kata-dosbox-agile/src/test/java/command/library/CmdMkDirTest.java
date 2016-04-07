/*
 * Course Agile Software Development
 */ 
package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import filesystem.FileSystemItem;

/**
 * Creates a directory.
 * <p>
 * The command mkdir is the same as the md command; they do the same thing. 
 * You can use either the mkdir command or use the md command which takes 
 * fewer characters to type.
 *
 */
public class CmdMkDirTest extends CmdTest {

	public void setUp() throws Exception {
		super.setUp();

		// Add all commands which are necessary to execute unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdMkDir("mkdir", drive));
	}

	@Test
	public void simpleCreation() {
		String testDirName = "test1";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("mkdir " + testDirName, testOutput);
		
		FileSystemItem item = drive.getItemFromPath(drive.getDriveName() + "\\" + testDirName);
		
		assertNotNull(item != null);
		assertEquals(item.getParent(), drive.getRootDirectory());
		assertEquals(drive.getRootDirectory().getNumberOfDirectories(), nbOfDirsBeforeTest + 1);
		assertEquals(testOutput.toString().length(), 0);
	}
	
	@Test
	public void singleLetterDirectoryCreation() {
		String testDirName = "a";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("mkdir " + testDirName, testOutput);
		
		FileSystemItem item = drive.getItemFromPath(drive.getDriveName() + "\\" + testDirName);
		
		assertNotNull(item);
		assertEquals(item.getParent(), drive.getRootDirectory());
		assertEquals(drive.getRootDirectory().getNumberOfDirectories(), nbOfDirsBeforeTest + 1);
		assertEquals(testOutput.toString().length(), 0);
	}
	
	@Test
	public void creationWithNoParameters() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("mkdir", testOutput);
		
		// No directory created
		assertEquals(drive.getRootDirectory().getNumberOfDirectories(), nbOfDirsBeforeTest);
		// Error message is output
		assertEquals("The syntax of the command is incorrect.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void creationWithManyParameters() {
		String testDirName1 = "test1";
		String testDirName2 = "test2";
		String testDirName3 = "test3";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfDirsBeforeTest = drive.getRootDirectory().getNumberOfDirectories();
		commandInvoker.executeCommand("mkdir " + testDirName1 + " " + testDirName2 + " " + testDirName3, testOutput);
		
		// Test if testDir1 was created
		FileSystemItem item = drive.getItemFromPath(drive.getDriveName() + "\\" + testDirName1);
		assertNotNull(item);
		assertEquals(item.getParent(), drive.getRootDirectory());
		
		// Test if testDir2 was created
		item = drive.getItemFromPath(drive.getDriveName() + "\\" + testDirName2);
		assertNotNull(item);
		assertEquals(item.getParent(), drive.getRootDirectory());
		
		// 3 directories must be created
		assertEquals(drive.getRootDirectory().getNumberOfDirectories(), nbOfDirsBeforeTest + 3);
		
		// no output
		assertEquals(testOutput.toString().length(), 0);
	}
}
