package org.anderes.edu.dojo.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Viewer {

    private final List<String> header;
    private final List<List<String>> records;
    private final List<Integer> maxWidthPerColumn;

    public Viewer(final List<String> header, final List<List<String>> records) {
        super();
        this.header = header;
        this.records = records;
        maxWidthPerColumn = calcMaxWidthForColumns();
    }

    public void show(final OutputStream outputStream) {
        final OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        try {
            writeHeader(writer);
            writeRecords(writer);
            appendHorizontalRule(writer).flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void writeRecords(final OutputStreamWriter writer) throws IOException {
        for (List<String> record : records) {
            writeRecord(writer, record).append('\n');
        }
    }

    private Writer writeRecord(final OutputStreamWriter writer, List<String> record) throws IOException {
        for (int column = 0; column < record.size(); column++) {
            final String recordText = record.get(column);
            writer.append(recordText);
            fillUpWithSpaces(writer, recordText, maxWidthPerColumn.get(column));
            writer.append('|');
        }
        return writer;
    }

    private void fillUpWithSpaces(final OutputStreamWriter writer, final String recordText, int maxWidth) throws IOException {
        for (int spacesize = recordText.length(); spacesize < maxWidth; spacesize++) {
            writer.append(' ');
        }
    }

    private Writer writeHeader(final OutputStreamWriter writer) throws IOException {
        for (int i = 0; i < header.size(); i++) {
            final String headerText = header.get(i);
            writer.append(headerText);
            int maxWidth = maxWidthPerColumn.get(i);
            fillUpWithSpaces(writer, headerText, maxWidth);
            writer.append('|');
        }
        writer.append('\n');
        appendHorizontalRule(writer);
        return writer;
    }
    
    private Writer appendHorizontalRule(final OutputStreamWriter writer) throws IOException {
        for (int maxWidth : maxWidthPerColumn) {
            for (int spacesize = 0; spacesize < maxWidth; spacesize++) {
                writer.append('-');
            }
            writer.append('+');
        }
        writer.append('\n');
        return writer;
    }
    
    private List<Integer> calcMaxWidthForColumns() {
        final List<Integer> maxWidths = new ArrayList<Integer>();
        for (int i = 0; i < header.size(); i++) {
            maxWidths.add(i, header.get(i).length());
            for(List<String> record : records) {
                if (record.get(i).length() > maxWidths.get(i)) {
                    maxWidths.remove(i);
                    maxWidths.add(i, record.get(i).length());
                }
            }
        }
        return maxWidths;
    }
}
