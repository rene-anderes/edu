package org.anderes.edu.dojo.csv;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReader8 {

    private final Path csvFile;
    private final Charset encoding = Charset.forName("UTF-8");
    private final String seperator = ";";

    public CsvReader8(final Path csvFile) {
        this.csvFile = csvFile;
    }

    public List<String> readHeader() {
        Stream<String> lines = null; 
        try {
            lines = Files.lines(csvFile, encoding);
            return lines.findFirst()
                    .map(line -> Arrays.asList(line.split(seperator)))
                    .get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            lines.close();
        }
    }

    public List<List<String>> readRecords() {
        Stream<String> lines = null; 
        try {
            lines = Files.lines(csvFile, encoding);
            return lines.skip(1)
                    .map(line -> Arrays.asList(line.split(seperator)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            lines.close();
        }
    }

}
