package invoker;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Testet den History-Logger (Command-Logger), der für das Pentagon erstellt wurde.
 * 
 * @author fu, mak
 */
public class HistoryLoggerIntegrationTest extends IntegrationTestBase {

	@Test
	public void logItemOrder() {
		commandInvoker.executeCommand("rename gaga.txt gugus.txt", testOutput);
		commandInvoker.executeCommand("dir /s", testOutput);
		commandInvoker.executeCommand("date", testOutput);
		
		List<String> historyItems = histroyDAO.readHistory();
		assertEquals(3, historyItems.size());
		assertEquals("date", historyItems.get(0));
		assertEquals("dir /s", historyItems.get(1));
		assertEquals("rename gaga.txt gugus.txt", historyItems.get(2));
		
		histroyDAO.clearHistory();
		testOutput.reset();
	}
	
	@Test
	public void logItemLength() {
		final String testCommand = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
		commandInvoker.executeCommand(testCommand, testOutput);
		commandInvoker.executeCommand(testCommand + "0", testOutput);
		commandInvoker.executeCommand(testCommand + "0123456789", testOutput);
		
		List<String> historyItems = histroyDAO.readHistory();
		assertEquals(3, historyItems.size());
		assertEquals(testCommand, historyItems.get(0));
		assertEquals(testCommand, historyItems.get(1));
		assertEquals(testCommand, historyItems.get(2));
		
		histroyDAO.clearHistory();
		testOutput.reset();
	}
}
