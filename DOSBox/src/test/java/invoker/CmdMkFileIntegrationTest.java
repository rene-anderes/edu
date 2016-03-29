/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import filesystem.File;
import filesystem.FileSystemItem;


public class CmdMkFileIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void creationWithNoContent() {
		String newFileName = "testFile";
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfFilesBeforeTest = drive.getCurrentDirectory().getNumberOfFiles();
		
		commandInvoker.executeCommand("mkfile " + newFileName, testOutput);
		assertTrue(testOutput.getOutput().length() == 0);
		assertTrue(drive.getCurrentDirectory().getNumberOfFiles() == nbOfFilesBeforeTest+1);
		String newFilePath = drive.getCurrentDirectory().getPath() + "\\" + newFileName;
		FileSystemItem newItem = drive.getItemFromPath(newFilePath);
		assertTrue(newItem != null);
		assertTrue(newItem.isDirectory() == false);
		assertTrue(newItem.getName().compareTo(newFileName) == 0);
		assertTrue(((File)newItem).getFileContent().compareTo("") == 0);
	}
	
	@Test
	public void wrongParameters1() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		int nbOfFilesBeforeTest = drive.getCurrentDirectory().getNumberOfFiles();
		
		commandInvoker.executeCommand("mkfile", testOutput);
		
		// No file shall be created
		assertTrue(drive.getCurrentDirectory().getNumberOfFiles() == nbOfFilesBeforeTest);
		// The following string must be output
		assertTrue(testOutput.getOutput().contains("syntax of the command is incorrect"));
	}
}
