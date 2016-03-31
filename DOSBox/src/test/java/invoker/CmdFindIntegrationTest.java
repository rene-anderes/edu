/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import filesystem.File;

@Ignore
public class CmdFindIntegrationTest extends IntegrationTestBase {
	
	private File searchFile;
	private String searchFileText;
	private File dirSearchFile1;
	private File dirSearchFile2;
	private File dirSearchFile3;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		searchFileText = "René Descartes\nAntoinette Deshoulières\nPhilisophen AG\nPhilosophenstrasse 1\n1000 Lausanne";
		
		searchFile = new File("SearchFile.txt", searchFileText);
		dirRoot.add(searchFile);
		dirSearchFile1 = new File("dirSearchFile1.txt", searchFileText);
		dirTemp.add(dirSearchFile1);
		dirSearchFile2 = new File("dirSearchFile2.txt", searchFileText);
		dirTemp.add(dirSearchFile2);
		dirSearchFile3 = new File("dirSearchFile3.txt", searchFileText);
		dirProgramFiles.add(dirSearchFile3);
	}
	
	@Test
	public void findInAFile() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("Find \"René\" " + searchFile.getName(), testOutput);
		
		assertTrue(testOutput.toString().contains("-- " + searchFile.getName()));
		assertTrue(testOutput.toString().toLowerCase().contains("René Descartes"));
	}
	
	@Test
	public void findInAFileOptionI() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("Find /i \"Antoinette\" " + searchFile.getName(), testOutput);
		
		assertTrue(testOutput.toString().contains("-- " + searchFile.getName()));
		assertTrue(testOutput.toString().toLowerCase().contains("Antoinette Deshoulières"));
	}
	
	@Test
	public void findNotInAFile() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("Find \"Peter\" " + searchFile.getName(), testOutput);
		assertTrue(testOutput.toString().contains("-- " + searchFile.getName()));
		assertTrue(testOutput.toString().toLowerCase().contains("Antoinette Deshoulières") == false);

		testOutput.reset();
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("Find \"René\" " + searchFile.getName(), testOutput);
		assertTrue(testOutput.toString().toLowerCase().contains("-- " + searchFile.getName().toLowerCase()));
		assertTrue(testOutput.toString().toLowerCase().contains("peter") == false);
	}
	
	@Test
	public void findInDirectory() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("Find \"René\" .", testOutput);
		
		assertTrue(testOutput.toString().contains("-- " + dirSearchFile1.getName()));
		assertTrue(testOutput.toString().contains("-- " + dirSearchFile2.getName()));
	}
	
	@Test
	public void findMultipleInSubdirectory() {
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("Find \"Antoinette\" .", testOutput);
		
		assertTrue(testOutput.toString().contains("-- " + searchFile.getName()));
		assertTrue(testOutput.toString().contains("-- " + dirSearchFile1.getName()));
		assertTrue(testOutput.toString().contains("-- " + dirSearchFile2.getName()));
		assertTrue(testOutput.toString().contains("-- " + dirSearchFile3.getName()));
	}
}
