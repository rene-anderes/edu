package org.anderes.logging;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeasuredValuesImport {
    
    private final static Pattern PATTERN = Pattern.compile("(\\w+)\\s+([-]?\\d+[.]?\\d*)\\s+(\\w+)");

    public static MeasuredValuesImport build() {
        System.out.println("Neuer Importer instanziert.");
        return new MeasuredValuesImport();
    }

    public Map<Integer, MeasuredValue> read(final Path xmlPath) throws IOException {
        if (xmlPath == null) {
            System.err.println("Programmierfehler: Der Parameter 'xmlPath' darf nicht null sein.");
            throw new IllegalArgumentException();
        }
        try(LineNumberReader reader = new LineNumberReader(new FileReader(xmlPath.toFile()))) {
            return reader.lines()
                .filter(line -> PATTERN.matcher(line).matches())
                .map(line -> {
                    Matcher matcher = PATTERN.matcher(line);
                    matcher.find();
                    return new MeasuredValue(reader.getLineNumber(), matcher.group(1), matcher.group(2), matcher.group(3));
                })
                .collect(Collectors.toMap(k -> k.getIndex(), m -> m));
        }
    }

}
