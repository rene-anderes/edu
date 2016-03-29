/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.*;
import filesystem.Drive;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import persistence.DaoFactory;
import persistence.DaoFactory.DaoType;

import command.framework.TestOutput;

public class CommandInvokerTest {

	private CommandInvoker commandInvoker;
	private TestOutput output;
	private TestCommand testcmd;

	@Before
	public void setUp() throws Exception {
		Drive drive = new Drive("C");
		commandInvoker = new CommandInvoker(DaoFactory.createHistoryDao(DaoType.FAKE));
		testcmd = new TestCommand("dIR", drive);
		commandInvoker.addCommand(testcmd);
		
		output = new TestOutput();
	}
	
	@Test
	public void parseCommandName() {
		assertEquals(commandInvoker.parseCommandName(""), "");
		assertEquals(commandInvoker.parseCommandName("dir"), "dir");
		assertEquals(commandInvoker.parseCommandName("DIR"), "dir");
		assertEquals(commandInvoker.parseCommandName("dir param1"), "dir");
		assertEquals(commandInvoker.parseCommandName("dir,param1, param2"), "dir");
		assertEquals(commandInvoker.parseCommandName("dir,"), "dir");
		assertEquals(commandInvoker.parseCommandName("dir   "), "dir");
		assertEquals(commandInvoker.parseCommandName("dir o"), "dir");
	}
	
	@Test
	public void parseParameters() {
		ArrayList<String> params;
		
		params = commandInvoker.parseCommandParameters("dir");
		assertTrue(params.isEmpty());
		
		params = commandInvoker.parseCommandParameters("dir /p");
		assertEquals(1, params.size());
		assertEquals("/p", params.get(0));
		
		params = commandInvoker.parseCommandParameters("dir /p param2");
		assertEquals(2, params.size());
		assertEquals("/p", params.get(0));
		assertEquals("param2", params.get(1));
		
		params = commandInvoker.parseCommandParameters("dir    /p");
		assertEquals(1, params.size());
		assertEquals("/p", params.get(0));
		
		params = commandInvoker.parseCommandParameters("dir  param1  param2   ");
		assertEquals(2, params.size());
		assertEquals("param1", params.get(0));
		assertEquals("param2", params.get(1));

		params = commandInvoker.parseCommandParameters("d 1 2");
		assertEquals(2, params.size());
		assertEquals("1", params.get(0));
		assertEquals("2", params.get(1));
	}
	
	@Test
	public void commandExecuteSimple() {
		commandInvoker.executeCommand("DIR", output);
		
		assertTrue(testcmd.executed);
	}
	
	@Test
	public void commandExecuteWithLeadingSpace() {
		commandInvoker.executeCommand("   DIR", output);
		
		assertTrue(testcmd.executed);
	}
	
	@Test
	public void commandExecuteWithEndingSpace() {
		commandInvoker.executeCommand("DIR   ", output);
		
		assertTrue(testcmd.executed);
	}
	
	@Test
	public void commandExecuteWIthDifferentCase() {
		commandInvoker.executeCommand("dir", output);
		
		assertTrue(testcmd.executed);
	}
	
	@Test
	public void commandExecuteWithParameters()
	{
		commandInvoker.executeCommand("dir param1 param2", output);
		
		assertTrue(testcmd.executed);
		assertEquals(2, testcmd.getParams().size());
		assertEquals(2, testcmd.numberOfParamsPassed);
	}
	
	@Test
	public void commandExecuteWithWrongParameters1() {
		testcmd.checkNumberOfParametersReturnValue = false;
		commandInvoker.executeCommand("dir param1 param2", output);
		
		assertFalse(testcmd.executed);
		assertEquals(2, testcmd.getParams().size());
		assertEquals(2, testcmd.numberOfParamsPassed);
		assertEquals("the syntax of the command is incorrect.\nwrong parameter entered\n", output.toString().toLowerCase());
	}
}
