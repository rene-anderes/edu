/*
 * Course Agile Software Development
 */ 
package command.library;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import filesystem.FileSystemItem;

public class CmdDirTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdDir("dir", drive));
	}

	@Test
	public void withNoParameters() {
		drive.setCurrentDirectory(rootDir);
		
		commandInvoker.executeCommand("dir", testOutput);
		
		assertEquals("Directory of " + rootDir.getPath() + "\n\n" +
				"<DIR>\t" + subDir1.getName() + "\n" +
				"<DIR>\t" + subDir2.getName() + "\n" +
				fileInRoot1.getSize() + "\t" + fileInRoot1.getName() + "\n" +
				fileInRoot2.getSize() + "\t" + fileInRoot2.getName() + "\n" +
				"\t" + rootDir.getNumberOfFiles() + " File(s)" + "\n" +
				"\t" + rootDir.getNumberOfDirectories() + " Dir(s)" + "\n", testOutput.toString());
	}

	@Test
	public void withPathAsParameter() {
		drive.setCurrentDirectory(rootDir);

		commandInvoker.executeCommand("dir c:\\subDir1", testOutput);
		
		assertEquals("Directory of " + subDir1.getPath() + "\n\n" +
				file1InDir1.getSize() + "\t" + file1InDir1.getName() + "\n" +
				file2InDir1.getSize() + "\t" + file2InDir1.getName() + "\n" +
				"\t" + subDir1.getNumberOfFiles() + " File(s)" + "\n" +
				"\t" + subDir1.getNumberOfDirectories() + " Dir(s)" + "\n", testOutput.toString());
	}
	
	@Test
	public void withCompactParameter() {
		String expected = "Directory of " + rootDir.getPath() + "\n\n"
				+ "[" + subDir1.getName() + "]" + "    " 
				+ "[" + subDir2.getName() + "]" + "    "
				+ fileInRoot1.getName() + "    " 
				+ fileInRoot2.getName() 
				+ "\n" 
				+ "\t" + rootDir.getNumberOfFiles() + " File(s)" + "\n" 
				+ "\t" + rootDir.getNumberOfDirectories() + " Dir(s)" + "\n";
		drive.setCurrentDirectory(rootDir);

		commandInvoker.executeCommand("dir /w", testOutput);
		assertEquals(expected, testOutput.toString());
		
		testOutput.empty();
		commandInvoker.executeCommand("dir /W", testOutput);
		assertEquals(expected, testOutput.toString());
		
	}
	
	@Test
	public void withCompactDirectoryParameter() {
		String expected = "Directory of " + subDir1.getPath() + "\n\n"
				+ file1InDir1.getName() + "    " + file2InDir1.getName() + "\n"
				+ "\t" + subDir1.getNumberOfFiles() + " File(s)" + "\n" 
				+ "\t" + subDir1.getNumberOfDirectories() + " Dir(s)" + "\n";
		drive.setCurrentDirectory(rootDir);

		commandInvoker.executeCommand("dir /w c:\\subDir1", testOutput);
		assertEquals(expected, testOutput.toString());
		
		testOutput.empty();
		commandInvoker.executeCommand("dir c:\\subDir1 /W", testOutput);
		assertEquals(expected, testOutput.toString());
	}
	
	@Test
	public void withDirectoryListParameter() {
		String expected = "Directory of " + rootDir.getPath() + "\n\n"
				+ "<DIR>\t" + subDir1.getName() + "\n" 
				+ "<DIR>\t" + subDir2.getName() + "\n" 
				+ fileInRoot1.getSize() + "\t" + fileInRoot1.getName() + "\n"
				+ fileInRoot2.getSize() + "\t" + fileInRoot2.getName() + "\n"
				+ "\t" + rootDir.getNumberOfFiles() + " File(s)" + "\n\n";
		expected += "Directory of " + subDir1.getPath() + "\n\n"
				+ file1InDir1.getSize() + "\t" + file1InDir1.getName() + "\n"
				+ file2InDir1.getSize() + "\t" + file2InDir1.getName() + "\n"
				+ "\t" + subDir1.getNumberOfFiles() + " File(s)" + "\n\n";
		expected += "Directory of " + subDir2.getPath() + "\n\n"
				+ "\t" + subDir2.getNumberOfFiles() + " File(s)" + "\n\n";
		expected += "Total Files Listed:" + "\n"
				+ "\t" + "4 File(s) 30 bytes" + "\n"
				+ "\t" + "2 Dir(s)" + "\n";
		
		commandInvoker.executeCommand("dir /s", testOutput);
		assertEquals(expected, testOutput.toString());
		
		testOutput.empty();
		commandInvoker.executeCommand("dir /S", testOutput);
		assertEquals(expected, testOutput.toString());
	}
	
	@Test
	public void comparator() {
		Comparator<FileSystemItem> c = new CmdDir.FileDirComparator();
		assertEquals(-1, c.compare(fileInRoot1, fileInRoot2));
		assertEquals(-1, c.compare(subDir1, fileInRoot1));
		assertEquals(-1, c.compare(subDir1, subDir2));
		
	}
}
