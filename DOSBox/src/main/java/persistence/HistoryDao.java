/*
 * Course Agile Software Development
 */
package persistence;

import java.util.LinkedList;

/**
 * This is the interface for any Data Access Object accessing the command
 * history persistence.
 * 
 * DAO pattern: Data Access Object interface
 * 
 */
public interface HistoryDao {

    public static final int MAX_COMMAND_LENGTH = 100;

    /**
     * Read the whole history that has been stored earlier. The list returned by
     * this method may be very large.
     * 
     * @return A list of command strings, ordered from youngest to oldest.
     */
    public abstract LinkedList<String> readHistory();

    /**
     * Store one command string in the history persistence.
     * 
     * @param historyItem
     *            The command string to be stored.
     */
    public abstract void appendHistoryItem(String historyItem);

    /**
     * Clear the whole history. calling readHistory() afterwards returns an
     * empty list.
     */
    public abstract void clearHistory();

}