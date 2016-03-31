/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import filesystem.File;
import filesystem.FileSystemItem;

import org.junit.Ignore;
import org.junit.Test;

/**Tests command move
 * 
 *	Missing tests:
 *	- Move a file to a location where a file with that name already exists, but pass a N for No
 *	- move /Y C:\WinWord.exe C:\ProgramFiles --> upper case Y
 */
@Ignore
public class CmdMoveIntegrationTest extends IntegrationTestBase {
	
	/** Tests whether move C:\ProgramFiles\WinWord.exe C:\Temp works
	 */
	@Test
	public void simpleMoveWithAbsolutePaths() {
		int numberOfFilesSource = dirProgramFiles.getNumberOfFiles();
		int numberOfFilesDest   = dirTemp.getNumberOfFiles();
		
		commandInvoker.executeCommand("move " + fileWinWord.getPath() + " " + dirTemp.getPath(), testOutput);
		
		assertTrue(testOutput != null);
		assertSame(fileWinWord.getParent(), dirTemp);
		assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFilesSource - 1);
		assertTrue(dirTemp.getNumberOfFiles() == numberOfFilesDest + 1);
	}

	/** Tests whether move WinWord.exe ..\Temp works.
	 *  Current directory is C:\ProgramFiles
	 */
	@Test
	public void simpleMoveWithRelativePaths() {
		int numberOfFilesSource = dirProgramFiles.getNumberOfFiles();
		int numberOfFilesDest   = dirTemp.getNumberOfFiles();
	
		drive.setCurrentDirectory(dirProgramFiles);
		commandInvoker.executeCommand("move " + fileWinWord.getName() + " ..\\Temp\\", testOutput);
		
		assertTrue(testOutput != null);
		assertSame(fileWinWord.getParent(), dirTemp);
		assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFilesSource - 1);
		assertTrue(dirTemp.getNumberOfFiles() == numberOfFilesDest + 1);
		assertSame(drive.getCurrentDirectory(), dirProgramFiles);
	}

	/** Tests whether move C:\Temp\TestDir2 C:\Windows works.
	 */
	@Test
	public void moveDirectory() {
		int numberOfDirSource = dirTemp.getNumberOfDirectories();
		int numberOfDirDest   = dirWindows.getNumberOfDirectories();
	
		commandInvoker.executeCommand("move " + dirTestDir2.getPath() + " " + dirWindows.getPath(), testOutput);
		
		assertTrue(testOutput != null);
		assertSame(dirTestDir2.getParent(), dirWindows);
		assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirSource - 1);
		assertTrue(dirWindows.getNumberOfDirectories() == numberOfDirDest + 1);
		assertSame(drive.getCurrentDirectory(), dirProgramFiles);
	}
	
	/** Tests whether
	 * - move WinWord.exe
	 * - move C:\Temp\NotExistingFile.txt C:\
	 * - move WinWord.exe SkiChallenge.exe C:\Temp
	 * - move
	 * - move *.* C:\Temp
	 * return errors
	 */
	@Test
	public void movewithWrongParameters() {
		drive.setCurrentDirectory(dirProgramFiles);
		
		testOutput.reset();
		commandInvoker.executeCommand("move WinWord.exe", testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("error"));
		
		testOutput.reset();
		commandInvoker.executeCommand("move C:\\Temp\\NotExistingFile.txt C:\\", testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("error"));
		
		testOutput.reset();
		commandInvoker.executeCommand(" move WinWord.exe SkiChallenge.exe C:\\Temp", testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));
		
		testOutput.reset();
		commandInvoker.executeCommand(" move", testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));
		
		testOutput.reset();
		commandInvoker.executeCommand("move *.* C:\\Temp", testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("error"));
	}

	/** Moves a file to a location where a file with the same name already exists.
	 *  Tests whether move /y C:\WinWord.exe C:\ProgramFiles\
	 *  moves the file without asking to overwrite
	 */
	@Test
	public void moveWithOptionY() {
		String newFileContent = "is the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		dirRoot.add(newFile);
		int filesAtSourceBeforeMove = dirRoot.getNumberOfFiles();
		int filesAtDestBeforeMove   = dirProgramFiles.getNumberOfFiles();
		
		commandInvoker.executeCommand("move /y C:\\WinWord.exe C:\\ProgramFiles\\", testOutput);
		
		FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		assertTrue(fi != null);
		assertTrue(fi.isDirectory() == false);
		File movedFile = (File)fi;
		assertSame(newFile, movedFile);
		assertTrue(movedFile.getFileContent().compareTo(newFileContent) == 0);
		assertSame(movedFile.getParent(), dirProgramFiles);
		assertTrue(fileWinWord.getParent() == null);
		assertTrue(dirRoot.getNumberOfFiles() == filesAtSourceBeforeMove - 1);
		assertTrue(dirProgramFiles.getNumberOfFiles() == filesAtDestBeforeMove);  // A file was overwritten
	}

	/** Moves a file to a location where a file with the same name already exists.
	 *  Tests whether move C:\WinWord.exe C:\ProgramFiles\
	 *  moves the file after asking to overwrite
	 */
	@Test
	public void moveWithOverwrite() {
		String newFileContent = "is this the new file";
		File newFile = new File("WinWord.exe", newFileContent);
		dirRoot.add(newFile);
		int filesAtSourceBeforeMove = dirRoot.getNumberOfFiles();
		int filesAtDestBeforeMove   = dirProgramFiles.getNumberOfFiles();
		testOutput.setCharacterThatIsRead('Y');
		
		commandInvoker.executeCommand("move C:\\WinWord.exe C:\\ProgramFiles\\", testOutput);

		assertTrue(testOutput.toString().toLowerCase().contains("overwrite"));
		assertTrue(testOutput.toString().toLowerCase().contains("yes/no"));
		assertTrue(testOutput.characterWasRead());
		FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
		assertTrue(fi != null);
		assertTrue(fi.isDirectory() == false);
		File movedFile = (File)fi;
		assertSame(newFile, movedFile);
		assertTrue(movedFile.getFileContent().compareTo(newFileContent) == 0);
		assertSame(movedFile.getParent(), dirProgramFiles);
		assertTrue(fileWinWord.getParent() == null);
		assertTrue(dirRoot.getNumberOfFiles() == filesAtSourceBeforeMove - 1);
		assertTrue(dirProgramFiles.getNumberOfFiles() == filesAtDestBeforeMove);  // A file was overwritten
	}
}
