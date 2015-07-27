package org.anderes.edu.dojo.testdata;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class CsvFileWriter {
    
    interface Writer {
        void toFile(final Path path) throws IOException;
        void toOutputStream(OutputStream outputStream) throws IOException;
    }

    private static CsvFileWriter instance;
    private final Writer writer;
    private final List<List<?>> data;
    

    public CsvFileWriter(List<List<?>> data) {
        this.data = data;
        writer = new CsvWriter();
    }

    public static Writer write(List<List<?>> data) {
        getInstance(data);
        return instance.writer;
    }

    private static CsvFileWriter getInstance(List<List<?>> data) {
        if (instance == null) {
            instance = new CsvFileWriter(data);
        }
        return instance;
    }

    private class CsvWriter implements Writer {

        @Override
        public void toFile(Path path) throws IOException {
            try (FileOutputStream outputStream = new FileOutputStream(path.toFile())) {
                toOutputStream(outputStream);   
            } 
        }

        @Override
        public void toOutputStream(OutputStream outputStream) throws IOException {
            final Function<Object,String> handleCell = cell -> {
                if (cell instanceof Number) {
                    return cell.toString();
                }
                return "\"" + cell.toString() + "\""; 
            };
            final Function<List<?>,String> handleRow = row -> {
                return row.stream().map(handleCell).collect(joining(","));
            };
            final String table = data.stream().map(handleRow).collect(joining("\r\n"));
            outputStream.write(table.getBytes(UTF_8));
        }
    }
}
