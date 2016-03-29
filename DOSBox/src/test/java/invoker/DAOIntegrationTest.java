package invoker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static persistence.DaoFactory.DaoType.DERBY;

import java.util.List;

import org.junit.Test;

import persistence.DaoFactory;
import persistence.HistoryDao;

/**
 * Testet die Apache Derby DAO
 * 
 * @author fu, mak
 */
public class DAOIntegrationTest extends IntegrationTestBase {

	@Test
	public void clearHistory() {
		HistoryDao dao = DaoFactory.createHistoryDao(DERBY);
		dao.appendHistoryItem("rename gaga.txt gugus.txt");
		
		dao.clearHistory();
		List<String> historyItems = dao.readHistory();
		assertTrue(historyItems.isEmpty());
	}
	
	@Test
	public void historyItemsOrder() {
		HistoryDao dao = DaoFactory.createHistoryDao(DERBY);
		dao.appendHistoryItem("rename gaga.txt gugus.txt");
		dao.appendHistoryItem("dir /s");
		
		List<String> historyItems = dao.readHistory();
		assertEquals("dir /s", historyItems.get(0));
		assertEquals("rename gaga.txt gugus.txt", historyItems.get(1));
		
		dao.clearHistory();
	}
	
	@Test
	public void logItemLength() {
		final String testCommand = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
		
		HistoryDao dao = DaoFactory.createHistoryDao(DERBY);
		dao.appendHistoryItem(testCommand);
		dao.appendHistoryItem(testCommand + "0");
		dao.appendHistoryItem(testCommand + "0123456789");
		
		List<String> historyItems = dao.readHistory();
		assertEquals(3, historyItems.size());
		assertEquals(testCommand, historyItems.get(0));
		assertEquals(testCommand, historyItems.get(1));
		assertEquals(testCommand, historyItems.get(2));
		
		dao.clearHistory();
	}
}
