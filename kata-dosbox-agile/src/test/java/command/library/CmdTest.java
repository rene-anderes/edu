/*
 * Course Agile Software Development
 */ 
package command.library;

import org.junit.Before;

import command.framework.TestOutput;
import filesystem.Directory;
import filesystem.Drive;
import filesystem.File;
import invoker.CommandInvoker;

/**
 * 
 * Sets up a directory structure as follows:
 * C:\
 * |---FileInRoot1
 * |---FileInRoot2
 * |---subDir1
 * |   |---File1InDir1
 * |   |---File2InDir1
 * |---subdir2
 * 
 */
public abstract class CmdTest  {

	protected CommandInvoker commandInvoker;
	protected TestOutput testOutput;
	protected Drive drive;
	protected Directory rootDir;
	protected File fileInRoot1;
	protected File fileInRoot2;
	protected Directory subDir1;
	protected File file1InDir1;
	protected File file2InDir1;
	protected Directory subDir2;

	@Before
	public void setUp() throws Exception {
		drive = new Drive("C");
		rootDir = drive.getRootDirectory();
		fileInRoot1 = new File("FileInRoot1", "an entry");
		rootDir.add(fileInRoot1);
		fileInRoot2 = new File("FileInRoot2", "a long entry in a file");
		rootDir.add(fileInRoot2);
		
		subDir1 = new Directory("subDir1");
		rootDir.add(subDir1);
		file1InDir1 = new File("File1InDir1", "");
		subDir1.add(file1InDir1);
		file2InDir1 = new File("File2InDir1", "");
		subDir1.add(file2InDir1);
		
		subDir2 = new Directory("subDir2");
		rootDir.add(subDir2);
		
		commandInvoker = new CommandInvoker();		
		testOutput = new TestOutput();
	}
	
	protected String removeTrailingNewLine(String line) {
		return line.substring(0, line.lastIndexOf("\n") != -1 ? line.lastIndexOf("\n") : line.length());
	}
}