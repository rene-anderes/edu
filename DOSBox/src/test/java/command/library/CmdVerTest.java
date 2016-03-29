package command.library;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CmdVerTest extends CmdTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// Add all commands which are necessary to execute this unit test
		// Important: Other commands are not available unless added here.
		commandInvoker.addCommand(new CmdVer("ver", drive));
	}
	
	final String EXPECTED_FULL = CmdVer.APPNAME + "\n" +
							CmdVer.AUTHOR_LABEL + CmdVer.AUTHORS + "\n" +
							CmdVer.VERSION_LABEL + CmdVer.VERSION;
	
	final String EXPECTED_COMPACT = CmdVer.VERSION;
	
	@Test
	public void testNonCompact() {
		commandInvoker.executeCommand("ver", testOutput);

		assertEquals(EXPECTED_FULL, removeTrailingNewLine(testOutput.toString()));
	}

	@Test
	public void testCompact() {
		commandInvoker.executeCommand("ver /w", testOutput);
		assertEquals(EXPECTED_COMPACT, removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void testWrongParameter() {
		commandInvoker.executeCommand("ver /X", testOutput);
		assertEquals("Wrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
	
	@Test
	public void testWrongParameters() {
		commandInvoker.executeCommand("ver /w /X", testOutput);
		assertEquals("The syntax of the command is incorrect.\nWrong parameter entered", removeTrailingNewLine(testOutput.toString()));
	}
}
