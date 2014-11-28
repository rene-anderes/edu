package org.anderes.edu.dojo.csv;

import java.util.Collections;
import java.util.List;

public class Paging {

    private final int pagesize = 3;
    private List<List<String>> records;
    private int index = 0;

    public Paging(final List<List<String>> records) {
        this.records = Collections.unmodifiableList(records);
    }

    public List<List<String>> firstPage() {
        index = pagesize;
        return records.subList(0, pagesize);
    }

    public List<List<String>> lastPage() {
        int toIndex = records.size();
        int fromIndex = 0;
        if (toIndex >= pagesize) {
            fromIndex = toIndex - pagesize;
        }
        index = toIndex;
        return records.subList(fromIndex, toIndex);
    }

    public List<List<String>> nextPage() {
        int fromIndex = index;
        int toIndex = index + pagesize;
        if (toIndex > records.size()) {
            return lastPage();
        }
        index = toIndex;
        return records.subList(fromIndex, toIndex);
    }

    public List<List<String>> previousPage() {
        int toIndex = index - pagesize;
        int fromIndex = toIndex - pagesize;
        if (fromIndex < 0) {
            return firstPage();
        }
        index = toIndex;
        return records.subList(fromIndex, toIndex);
    }

}
