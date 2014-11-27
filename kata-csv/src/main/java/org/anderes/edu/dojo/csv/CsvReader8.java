package org.anderes.edu.dojo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader8 {

    private final Path csvFile;
    private final Charset encoding = Charset.forName("UTF-8");
    private final String seperator = ";";

    public CsvReader8(final Path csvFile) {
        this.csvFile = csvFile;
    }

    public List<String> readHeader() {
        try {
            final Reader source = Files.newBufferedReader(csvFile, encoding);
            final BufferedReader reader = new BufferedReader(source); 
            return reader.lines()
                    .findFirst()
                    .map(line -> Arrays.asList(line.split(seperator)))
                    .get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<List<String>> readRecords() {
        try {
            final Reader source = Files.newBufferedReader(csvFile, encoding);
            final BufferedReader reader = new BufferedReader(source);
            return reader.lines()
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(seperator)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
