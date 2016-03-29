/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CmdPushdPopdIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void pushAndPop() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("pushd .", testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getCurrentDirectory() == dirTemp);
		
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("popd", testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getCurrentDirectory() == dirTemp);
	}
	
	@Test
	public void pushAndPopWithDifferentDirectory() {
		drive.setCurrentDirectory(dirTemp);
		commandInvoker.executeCommand("pushd " + dirWindowsSystem32.getPath(), testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getCurrentDirectory() == dirWindowsSystem32);
		
		drive.setCurrentDirectory(dirRoot);
		commandInvoker.executeCommand("popd", testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getCurrentDirectory() == dirWindowsSystem32);
	}
	
	@Test
	public void popdPostCondition() {
		pushAndPop();
		
		assertTrue(drive.getCurrentDirectory() != dirWindows);

		drive.setCurrentDirectory(dirWindows);
		commandInvoker.executeCommand("popd", testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getCurrentDirectory() == dirWindows);
	}
}
