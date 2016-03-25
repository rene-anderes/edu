/*
 * Course Agile Software Development
 */ 
package persistence;

/**
 * Factory class that creates Data Access Objects.
 * 
 * DAO pattern: Data Access Object factory
 *
 */
public class DaoFactory {
	
	public enum DaoType { FAKE, DERBY, ORACLE }
	
	/**
	 * Creates a DAO to access the command history persistence.
	 */
	public static HistoryDao createHistoryDao(DaoType daoType) {
		if (daoType == DaoType.FAKE) {
			return new MockHistoryDao();
		} else if (daoType == DaoType.DERBY) {
			return new DerbyHistoryDao();
		} else {
			return null;
		}
	}
	
}
