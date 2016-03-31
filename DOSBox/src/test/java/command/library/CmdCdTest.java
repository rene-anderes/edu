/*
 * Course Agile Software Development
 */ 
package command.library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import filesystem.Directory;

public class CmdCdTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdCd("cd", drive));
	}

	@Test
	public void validChangeDirectory1() {
		String dirName = subDir1.getPath();
		commandInvoker.executeCommand("cd " + dirName, testOutput);
		
		assertEquals(subDir1, drive.getCurrentDirectory());
		assertEquals(drive.getCurrentDirectory().getPath(), dirName);
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void validChangeDirectory2() {
		String dirName = subDir2.getPath();
		commandInvoker.executeCommand("cd " + dirName + "\\",
				testOutput);
		assertSame(subDir2, drive.getCurrentDirectory());
		assertTrue(drive.getCurrentDirectory().getPath()
				.compareTo(dirName) == 0);
		assertTrue(testOutput.toString().length() == 0);

		assertEquals(subDir2, drive.getCurrentDirectory());
		assertEquals(drive.getCurrentDirectory().getPath(), dirName);
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void cdIntoDriveRootDirWithBackslash() {
		commandInvoker.executeCommand("cd \\", testOutput);
		
		assertEquals(rootDir, drive.getCurrentDirectory());
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void cdOneFolderUpWithDotDot() {
		drive.setCurrentDirectory(subDir1);
		commandInvoker.executeCommand("cd ..", testOutput);

		assertEquals(rootDir, drive.getCurrentDirectory());
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void cdOneFolderUpInRootDirStaysInRootDir() {
		drive.setCurrentDirectory(rootDir);
		commandInvoker.executeCommand("cd ..", testOutput);
		
		assertEquals(rootDir, drive.getCurrentDirectory());
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void cdIntoCurrentFolderWithDotStaysInCurrentFolder() {
		drive.setCurrentDirectory(subDir1);
		commandInvoker.executeCommand("cd .", testOutput);
		
		assertEquals(subDir1, drive.getCurrentDirectory());
		assertEquals(0, testOutput.toString().length());
	}

	@Test
	public void cdWithoutArgumentsPrintsCurrentFolder() {
		drive.setCurrentDirectory(subDir1);
		commandInvoker.executeCommand("cd", testOutput);
		
		assertTrue(testOutput.toString().contains(subDir1.getPath()));
		assertEquals(subDir1.getPath(), removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void withNotExistingPath() {
		Directory curDir = drive.getCurrentDirectory();
		commandInvoker.executeCommand("cd c:\\gaga\\gugus",
				testOutput);
	
		assertEquals(curDir, drive.getCurrentDirectory());
		assertEquals("The system cannot find the path specified", removeTrailingNewLine(testOutput.toString()));
    }

	@Test
	public void withFileAsPath() {
		drive.setCurrentDirectory(rootDir);
		commandInvoker.executeCommand("cd " + file1InDir1.getPath(),
				testOutput);

		assertEquals(rootDir, drive.getCurrentDirectory());
		assertEquals("The system cannot find the path specified", removeTrailingNewLine(testOutput.toString()));
	}

	@Test
	public void withRelativePath() {
		drive.setCurrentDirectory(rootDir);
		commandInvoker.executeCommand("cd " + subDir1.getName(),
				testOutput);
		assertEquals(subDir1, drive.getCurrentDirectory());
	}

	@Test
	public void withSingleLetterDir() {
		Directory dir = new Directory("a");
		rootDir.add(dir);
		drive.setCurrentDirectory(rootDir);

		commandInvoker.executeCommand("cd a", testOutput);

		assertEquals(drive.getCurrentDirectory(), dir);
	}
}
