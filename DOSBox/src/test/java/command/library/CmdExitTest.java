package command.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import command.framework.Command;

public class CmdExitTest extends CmdTest {

	private CommandFactory commandFactory;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		commandFactory = new CommandFactory(drive);
		commandInvoker.setCommands(commandFactory.getCommandList());
	}
	
	@Test
	public void executeExit() {
		assertNotNull(commandFactory.getCommandList());
		boolean found = false;
		for(Command c: commandFactory.getCommandList()) {
			if (c.compareCmdName("exit")) {
				assertEquals(CmdExit.class, c.getClass());
				found = true;
				break;
			}
		}
		assertTrue("Factory does not contain the exit-Command", found);
		commandInvoker.executeCommand("exit", testOutput);
		assertTrue(removeTrailingNewLine(testOutput.toString()).isEmpty());
	}
}
