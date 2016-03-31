package command.library;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import command.framework.Command;
import filesystem.Drive;

/**
 * Test für die Command-Factory
 * 
 * @author ra, hum
 */
public class CommandFactoryTest {

	private static CommandFactory factory;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Drive drive = new Drive("C");
		factory = new CommandFactory(drive);
	}

	@Test
	public void commandList() {
		List<Command> commands = factory.getCommandList();
		assertNotNull(commands);
		assertTrue(commands.size() > 1);
	}
	
	@Test
	public void helpText() {
		List<Command> commands = factory.getCommandList();
		for (Command command : commands) {
			assertNotNull(factory.getHelpForCommand(command.toString()));
			assertTrue(factory.getHelpForCommand(command.toString()).length() > 5);
		}
	}
}
