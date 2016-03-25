package command.library;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import filesystem.File;

public class CmdTypeTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdType("type", drive));
		commandInvoker.addCommand(new CmdType("cat", drive));
	}

	@Test
	public void noParameter() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("cat", testOutput);
		assertEquals("The syntax of the command is incorrect.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void wrongRelativeFile() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("type foobar", testOutput);
		assertEquals("Cannot find the file specified.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void wrongAbsoluteFile() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("cat subDir1\\foobar", testOutput);
		assertEquals("Cannot find the file specified.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}

	@Test
	public void wrongAbsoluteFileWithDriveletter() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("type C:\\subDir1\\foobar", testOutput);
		assertEquals("Cannot find the file specified.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}

	@Test
	public void wrongFileType() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("type C:\\subDir1\\File1InDir1", testOutput);
		assertEquals("Invalid filetype.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void catExistingFileAbsolute() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		final String content = "this is a little test\nfoo bar";
		subDir1.add(new File("foo.txt", content));
		commandInvoker.executeCommand("type C:\\subDir1\\foo.txt", testOutput);
		assertEquals(content, removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void catExistingFileRelative() {
		final String content = "this is a little test2\nfoo bar";
		subDir1.add(new File("foo.txt", content));
		drive.setCurrentDirectory(subDir1);
		commandInvoker.executeCommand("type foo.txt", testOutput);
		assertEquals(content, removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void catDirectory() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("type subDir1", testOutput);
		assertEquals("Cannot cat a directory.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
}
