package persistence;

import persistence.DaoFactory.DaoType;

public class HistoryDBViewer {

	/**
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		final HistoryDao dao = DaoFactory.createHistoryDao(DaoType.DERBY);
		int i = 1;
		for(final String cmd : dao.readHistory()) {
			System.out.println(i + ": " + cmd.trim());
			i += 1;
		}
	}
}
