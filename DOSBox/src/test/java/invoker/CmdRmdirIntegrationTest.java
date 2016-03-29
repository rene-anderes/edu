/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import filesystem.Directory;

@Ignore
public class CmdRmdirIntegrationTest extends IntegrationTestBase {
	
	/** Tests whether "rmdir TestDir2" removes this directory since TestDir2 is empty.
	 */
	@Test
	public void removeEmptyDirectory() {
		int numberOfDirs = dirTemp.getNumberOfDirectories();
		
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("rmdir " + dirTestDir2.getName(), testOutput);
		
		assertTrue(testOutput != null);
		assertTrue(testOutput.toString().toLowerCase().contains("not empty") == false);
		assertTrue(dirTestDir2.getParent() == null);
		assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirs-1);
	}

	/** Tests whether "rmdir TestDir1" DOES NOT remove this directory since TestDir1 is not empty.
	 *  "The directory is not empty." must be returned as error string.
	 */
	@Test
	public void removeNotEmptyDirectory() {
		int numberOfDirs = dirTemp.getNumberOfDirectories();
		
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("rmdir " + dirTestDir1.getName(), testOutput);

		assertTrue(testOutput != null);
		assertTrue(testOutput.toString().toLowerCase().contains("not empty"));
		assertTrue(dirTestDir1.getParent() == dirTemp);
		assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirs);
	}

	/** Tests whether "rmdir /s TestDir1" removes this directory
	 *  Prior to delete the directory, the user is asked.
	 */
	@Test
	public void removeNotEmptyDirectoryWithOptionS() {
		int numberOfDirs = dirTemp.getNumberOfDirectories();

		testOutput.setCharacterThatIsRead('y');
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("rmdir /s " + dirTestDir1.getName(), testOutput);

		assertTrue(testOutput != null);
		assertTrue(testOutput.toString().toLowerCase().contains("are you sure"));
		assertTrue(testOutput.toString().toLowerCase().contains("y/n"));
		assertTrue(testOutput.characterWasRead());
		assertTrue(dirTestDir1.getParent() == null);
		assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirs-1);
	}
	
	/** Tests whether "rmdir TestDir1 /s" does not delete the directy when 'N' is passed
	 *  at the safety question.
	 */
	@Test
	public void doNotRemoveNotEmptyDirectoryWithOptionS() {
		int numberOfDirs = dirTemp.getNumberOfDirectories();

		testOutput.setCharacterThatIsRead('n');
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("rmdir /s " + dirTestDir1.getName(), testOutput);

		assertTrue(testOutput != null);
		assertTrue(testOutput.toString().toLowerCase().contains("are you sure"));
		assertTrue(testOutput.toString().toLowerCase().contains("y/n"));
		assertTrue(testOutput.characterWasRead());
		assertTrue(dirTestDir1.getParent() == dirTemp);
		assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirs);
	}

	/** Check whether rmdir cannot remove the current directory itself, e.g. "rmdir ."
	 */
	@Test
	public void removeCurrentDirectory() {
		drive.setCurrentDirectory(dirTestDir2);
		commandInvoker.executeCommand("rmdir /s /q " + dirTestDir2.getPath(), testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("cannot access the file"));
		assertTrue(testOutput.toString().toLowerCase().contains("being used by another process"));
		assertTrue(dirTestDir2.getParent() != null);
	}
	
	@Test
	public void removeParentDirectory() {
		Directory parent = dirTestDir2.getParent();
		drive.setCurrentDirectory(dirTestDir2);
		commandInvoker.executeCommand("rmdir /s /q " + dirTestDir2.getParent().getPath(), testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("cannot access the file"));
		assertTrue(testOutput.toString().toLowerCase().contains("being used by another process"));
		assertTrue(dirTestDir2.getParent() != null);
		assertTrue(parent.getParent() != null);
	}

	/** Tests whether "rmdir TestDir1 /s /q" does not ask the safety question and
	 *  deletes the directory with all its content.
	 */
	@Test
	public void removeWithOptionsSandQ() {
		callAndTestRemoveWithOptionSandQ("rmdir /s /q " + dirTestDir1.getName(), dirTestDir1);
	}

	/** Tests options /s and /q in different positions
	 *  Note: Normally, every option is tested in a separate Test Case. Here, on Test Case is used,
	 *  otherwise, the number of points would get very large.
	 */
	@Test
	public void removeWithOptionOnDifferentPositions() throws Exception {
		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /s /q " + dirTestDir1.getName(), dirTestDir1);

		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /q /s " + dirTestDir1.getName(), dirTestDir1);

		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir " + dirTestDir1.getName() + " /s /q", dirTestDir1);

		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir " + dirTestDir1.getName() + " /q /s", dirTestDir1);

		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /s " + dirTestDir1.getName() + " /q", dirTestDir1);

		setUp();
		callAndTestRemoveWithOptionSandQ("rmdir /q " + dirTestDir1.getName() + " /s", dirTestDir1);
	}

	/** Helper method
	 * @param commandLine
	 * @param testDir
	 */
	private void callAndTestRemoveWithOptionSandQ(String commandLine, Directory testDir) {
		Directory parent = testDir.getParent();
		assertTrue(parent != null);
		int numberOfDirs = parent.getNumberOfDirectories();
		
		drive.setCurrentDirectory(parent);
		testOutput.setCharacterThatIsRead('y');
		commandInvoker.executeCommand(commandLine, testOutput);
		
		assertTrue(testOutput != null);
		assertTrue(testOutput.characterWasRead() == false);
		assertTrue(testOutput.toString().toLowerCase().contains("are you sure") == false);
		assertTrue(testDir.getParent() == null);
		assertTrue(parent.getNumberOfDirectories() == numberOfDirs-1);
	}
}
