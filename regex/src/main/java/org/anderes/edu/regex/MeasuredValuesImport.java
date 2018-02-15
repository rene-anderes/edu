package org.anderes.edu.regex;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeasuredValuesImport {
    
    private final static Pattern pattern = Pattern.compile("(\\w+)\\s+([-]?\\d+[.]?\\d*)\\s+(\\w+)");

    public static MeasuredValuesImport build() {
        return new MeasuredValuesImport();
    }

    public Map<Integer, MeasuredValue> read(final Path xmlPath) throws IOException {
        try(LineNumberReader reader = new LineNumberReader(new FileReader(xmlPath.toFile()))) {
            return reader.lines()
                .filter(line -> pattern.matcher(line).matches())
                .map(line -> {
                    Matcher matcher = pattern.matcher(line);
                    matcher.find();
                    return new MeasuredValue(reader.getLineNumber(), matcher.group(1), matcher.group(2), matcher.group(3));
                })
                .collect(Collectors.toMap(k -> k.getIndex(), m -> m));
        }
    }

}
