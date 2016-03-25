/*
 * Course Agile Software Development
 */
package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * This DAO provides data access to the command history stored in an Apache
 * Derby DB. The command objects are represented as strings here. Note that
 * there is no limitation in the number of stored commands: If clearHistory() is
 * never called, the history will grow larger and larger.
 * 
 * DAO pattern: Concrete Data Access Object
 * 
 */
class DerbyHistoryDao implements HistoryDao {

    private static final String DB_NAME = "historyDB";
    private static final String TABLE_NAME = "history";

    private Connection conn;
    private PreparedStatement selectAll;
    private PreparedStatement append;
    private PreparedStatement clear;

    /**
     * Creates a history DAO object which holds a connection to the Derby DB. If
     * the database doesn't exist it is created on the fly. Note that the
     * connection is never shut down. This is not a problem. It just means that
     * the next connection establishment will take a little longer. See
     * http://db.apache.org/derby/papers/DerbyTut/embedded_intro.html#shutdown
     * for more information about shutting down Derby DBs.
     */
    public DerbyHistoryDao() {

        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            Class.forName(driver).newInstance(); // Load driver
            String connectStr = "jdbc:derby:" + DB_NAME + ";create=true";

            // System.out.println("Connecting to: " + connectStr);

            conn = DriverManager.getConnection(connectStr);

            ensureTableExists();

            selectAll = conn.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC");
            append = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(command) VALUES (?)");
            clear = conn.prepareStatement("DELETE FROM " + TABLE_NAME);

        } catch (SQLException e) {
            System.err.println("DerbyHistoryDao - Unable to open history DB");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("DerbyHistoryDao - Something bad went wrong");
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see persistence.HistoryDao#readHistory()
     */
    public LinkedList<String> readHistory() {
        LinkedList<String> history = new LinkedList<String>();

        try {
            ResultSet rs = selectAll.executeQuery();
            while (rs.next()) {
                history.add(rs.getString("command"));
            }

        } catch (SQLException e) {
            System.err.println("Unable to read from history DB");
            e.printStackTrace();
        }

        return history;
    }

    /*
     * (non-Javadoc)
     * 
     * @see persistence.HistoryDao#appendHistoryItem(java.lang.String)
     */
    public void appendHistoryItem(String historyItem) {
        try {
            final int cutLength = Math.min(historyItem.length(), MAX_COMMAND_LENGTH);
            append.setString(1, historyItem.substring(0, cutLength));
            append.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Unable to append to history DB");
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see persistence.HistoryDao#clearHistory()
     */
    public void clearHistory() {
        try {
            clear.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Unable to clear history DB");
            e.printStackTrace();
        }
    }

    /**
     * This method tries to create a table for the command history. If the table
     * already exists, an exception is thrown (which is just ignored). Thus,
     * this method effectively makes sure that the table exists when it returns.
     */
    private void ensureTableExists() {

        try {
            conn.createStatement().executeUpdate("CREATE TABLE " + TABLE_NAME + "(id INT NOT NULL GENERATED ALWAYS AS IDENTITY, command VARCHAR(" + MAX_COMMAND_LENGTH + ") NOT NULL)");
        } catch (SQLException e) {
            // Intentionally ignored (the table exists already)
            // System.out.println("Table " + TABLE_NAME + " already existing.");
        }

    }

}
