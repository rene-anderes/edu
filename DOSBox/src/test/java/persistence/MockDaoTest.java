package persistence;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MockDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Hinzufügen eines HistoryItem.
	 */
	@Test
	public void appendHistoryItem() {
		HistoryDao dao = new MockHistoryDao();
		dao.appendHistoryItem("dir /w");
		List<String> history = dao.readHistory();
		assertEquals(1, history.size());
		assertTrue(history.contains("dir /w"));
	}
	
	/**
	 * Richtige Reihenfolge der HistoryItem.
	 */
	@Test
	public void appendHistoryItemOrder() {
		HistoryDao dao = new MockHistoryDao();
		dao.appendHistoryItem("dir /w");
		dao.appendHistoryItem("makedir test");
		List<String> history = dao.readHistory();
		assertEquals(2, history.size());
		assertTrue(history.get(0).equalsIgnoreCase("makedir test"));
		assertTrue(history.get(1).equalsIgnoreCase("dir /w"));
	}
	
	/**
	 * Löschen der HistoryItems.
	 */
	@Test
	public void clearHistoryItem() {
		HistoryDao dao = new MockHistoryDao();
		dao.appendHistoryItem("dir /w");
		List<String> history = dao.readHistory();
		assertEquals(1, history.size());
		dao.clearHistory();
		history = dao.readHistory();
		assertEquals(0, history.size());
	}
	
}
