package org.anderes.edu.dojo.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Viewer {

    private final List<String> header;
    private final List<List<String>> records;

    public Viewer(final List<String> header, final List<List<String>> records) {
        super();
        this.header = header;
        this.records = records;
    }

    public void show(final OutputStream outputStream) {
        final OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        try {
            for (String text : header) {
                writer.append(text);
            }
            writer.append('\n');
            for (List<String> record : records) {
                for (String text : record) {
                    writer.append(text);
                }
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
