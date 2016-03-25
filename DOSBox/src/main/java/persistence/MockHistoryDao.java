package persistence;

import java.util.LinkedList;

/**
 * Mockobjekt für Dao der HistoryItem.
 * 
 * @author ra, hum
 *
 */
public class MockHistoryDao implements HistoryDao {

    private final LinkedList<String> historyItems = new LinkedList<String>();

    @Override
    public void appendHistoryItem(final String historyItem) {
        if (historyItem == null) {
            throw new IllegalArgumentException();
        }
        final int cutLength = Math.min(historyItem.length(), MAX_COMMAND_LENGTH);
        this.historyItems.addFirst(historyItem.substring(0, cutLength));
    }

    @Override
    public void clearHistory() {
        this.historyItems.clear();
    }

    @Override
    public LinkedList<String> readHistory() {
        return this.historyItems;
    }

}
