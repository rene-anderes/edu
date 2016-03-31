/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import filesystem.Directory;

public class CmdRenameIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void ren() {
		rename("Ren");
	}

	@Test
	public void rename() {
		rename("Rename");
	}
	
	private void rename(final String commandName) {
		final String newFileName = "anotherName.tst";
		Directory previousParent = fileBullet.getParent();
		drive.setCurrentDirectory(previousParent);
		commandInvoker.executeCommand(commandName + " "
				+ fileBullet.getName() + " " + newFileName,
				testOutput);

		assertTrue(fileBullet.getName().compareTo(newFileName) == 0);
		assertTrue(fileBullet.getParent() == previousParent);
		assertTrue(testOutput.toString().length() < 2);
	}

	@Ignore // comes as new story
	@Test
	public void renWithFullPath() {
		renameTestWithFullPath("Ren");
	}

	@Ignore // comes as new story
	@Test
	public void renameWithFullPath() {
		renameTestWithFullPath("Rename");
	}

	private void renameTestWithFullPath(String commandName) {
		String newFileName = "anotherName.tst";
		Directory previousParent = fileBullet.getParent();
		commandInvoker.executeCommand(commandName + " "
				+ fileBullet.getPath() + " " + newFileName,
				testOutput);

		assertTrue(fileBullet.getName().compareTo(newFileName) == 0);
		assertTrue(fileBullet.getParent() == previousParent);
		assertTrue(testOutput.toString().length() < 2);
	}

	@Test
	public void renameDirectory() {
		String newDirName = "MyTemp";
		Directory previousParent = dirTemp.getParent();
		drive.setCurrentDirectory(previousParent);
		commandInvoker.executeCommand("ren " + dirTemp.getName()
				+ " " + newDirName, testOutput);

		assertTrue(dirTemp.getName().compareTo(newDirName) == 0);
		assertTrue(dirTemp.getParent() == previousParent);
		assertTrue(testOutput.toString().length() < 2);
	}
	
	@Ignore // comes as new story
	@Test
	public void renameDirectoryWithFullPath() {
		String newDirName = "MyTemp";
		Directory previousParent = dirTemp.getParent();
		commandInvoker.executeCommand("ren " + dirTemp.getPath()
				+ " " + newDirName, testOutput);

		assertTrue(dirTemp.getName().compareTo(newDirName) == 0);
		assertTrue(dirTemp.getParent() == previousParent);
		assertTrue(testOutput.toString().length() < 2);
	}

	@Test
	public void renameWhenNewFileAlreadyExists() {
		String newFileName = fileWinWord.getName();
		String previousFileName = fileExcel.getName();
		drive.setCurrentDirectory(dirProgramFiles);
		commandInvoker.executeCommand("ren " + fileExcel.getName()
				+ " " + newFileName, testOutput);

		assertTrue(fileExcel.getName()
				.compareTo(previousFileName) == 0);
		assertTrue(testOutput.toString().toLowerCase().contains(
				"destination already exists"));
	}

	@Test
	public void renameWithInvalidNewFileName() {
		renameWithInvalildNewFileName("Files with spaces are invalid");
		renameWithInvalildNewFileName("Backslash\\AreNotAllowed");
		renameWithInvalildNewFileName("Forwardslash/AreNotAllowed");
		renameWithInvalildNewFileName("Commas,AreNotAllowed");
	}

	private void renameWithInvalildNewFileName(String newFileName) {
		String previousFileName = fileBullet.getName();
		commandInvoker.executeCommand("ren " + fileBullet.getName()
				+ " " + newFileName, testOutput);

		assertTrue(fileBullet.getName().compareTo(
				previousFileName) == 0);
		assertTrue(testOutput.toString().toLowerCase().contains("syntax"));
		assertTrue(testOutput.toString().toLowerCase().contains("incorrect"));
	}
}
