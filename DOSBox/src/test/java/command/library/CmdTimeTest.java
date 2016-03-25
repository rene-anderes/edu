package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import command.framework.Command;

public class CmdTimeTest extends CmdTest {

	private CommandFactory commandFactory;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		commandFactory = new CommandFactory(drive);
		commandInvoker.setCommands(commandFactory.getCommandList());
	}
	
	@Test
	public void executeTimeOutput() {
		assertNotNull(commandFactory.getCommandList());
		boolean found = false;
		for(Command c: commandFactory.getCommandList()) {
			if (c.compareCmdName("time")) {
				assertEquals(CmdTime.class, c.getClass());
				found = true;
				break;
			}
		}
		assertTrue("Factory does not contain the time-Command", found);
		commandInvoker.executeCommand("time", testOutput);
		
		String result = removeTrailingNewLine(testOutput.toString());
		assertTrue(result.matches("[a-zA-Z ]* [0-2][0-9]:[0-6][0-9]:[0-6][0-9]"));
	}
	
	@Test
	public void executeTimeInput() {
		assertNotNull(commandFactory.getCommandList());
		boolean found = false;
		for(Command c: commandFactory.getCommandList()) {
			if (c.compareCmdName("time")) {
				assertEquals(CmdTime.class, c.getClass());
				found = true;
				break;
			}
		}
		assertTrue("Factory does not contain the time-Command", found);
		commandInvoker.executeCommand("time 12:48:10", testOutput);
		
		String result = removeTrailingNewLine(testOutput.toString());
		assertTrue(result.matches("the current time is 12:48:10"));
	}
	
	@Test
	public void invalidParameterToManyHours() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("24:05:59");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void invalidParameterWrongFormat() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("21-05:59");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void invalidParameterToManyMinutes() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("19:60:59");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void invalidParameterToManySeconds() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("19:59:60");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}

	@Test
	public void invalidParameterWithoutSeconds() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("19:59");
		cmd.setParameters(parameters);
		assertFalse(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void validParameter() {
		CmdTime cmd = new CmdTime("time", drive);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("00:00:00");
		cmd.setParameters(parameters);
		assertTrue(cmd.checkParameterValues(testOutput));
		
		parameters.set(0, "23:59:59");
		assertTrue(cmd.checkParameterValues(testOutput));
	}
	
	@Test
	public void numberOfParameters() {
		CmdTime cmd = new CmdTime("time", drive);
		assertTrue(cmd.checkNumberOfParameters(0));
		assertTrue(cmd.checkNumberOfParameters(1));
		assertFalse(cmd.checkNumberOfParameters(2));
	}
	
	
}
