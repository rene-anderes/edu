package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import command.framework.Command;

public class CmdDateTest extends CmdTest {

	private CommandFactory commandFactory;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		commandFactory = new CommandFactory(drive);
		commandInvoker.setCommands(commandFactory.getCommandList());
	}
	
	@Test
	public void executeDateOutput() {
		assertNotNull(commandFactory.getCommandList());
		boolean found = false;
		for(Command c: commandFactory.getCommandList()) {
			if (c.compareCmdName("date")) {
				assertEquals(CmdDate.class, c.getClass());
				found = true;
				break;
			}
		}
		assertTrue("Factory does not contain the date-Command", found);
		commandInvoker.executeCommand("date", testOutput);
		
		String result = removeTrailingNewLine(testOutput.toString());
		assertTrue(result.matches("[a-zA-Z ]* [1-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]"));
	}
	
	@Test
	public void executeDateInput() {
		assertNotNull(commandFactory.getCommandList());
		boolean found = false;
		for(Command c: commandFactory.getCommandList()) {
			if (c.compareCmdName("date")) {
				assertEquals(CmdDate.class, c.getClass());
				found = true;
				break;
			}
		}
		assertTrue("Factory does not contain the date-Command", found);
		commandInvoker.executeCommand("date 2048-10-01", testOutput);
		
		String result = removeTrailingNewLine(testOutput.toString());
		assertTrue(result.matches("the current date is 2048-10-01"));
	}
	
	@Test
	public void invalidParameterWrongFormat() {
		CmdDate cmd = new CmdDate("date", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("2100-05:26");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void invalidParameterToManyMonth() {
		CmdDate cmd = new CmdDate("date", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("1999-13-20");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void invalidParameterToManyDays() {
		CmdDate cmd = new CmdDate("date", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("1999-11-55");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}

	@Test
	public void invalidParameterWithoutDays() {
		CmdDate cmd = new CmdDate("date", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("1999-11");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void validParameter() {
		CmdDate cmd = new CmdDate("date", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("0001-01-01");
		cmd.setParameters(parameters);
		assertTrue(cmd.checkParameterValues(testOutput));
		
		parameters.set(0, "9999-12-31");
		assertTrue(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void numberOfParameters() {
		CmdDate cmd = new CmdDate("date", drive);
		assertTrue(cmd.checkNumberOfParameters(0));
		assertTrue(cmd.checkNumberOfParameters(1));
		assertFalse(cmd.checkNumberOfParameters(2));
	}
	
	
}
