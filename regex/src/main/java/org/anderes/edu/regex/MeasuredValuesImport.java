package org.anderes.edu.regex;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeasuredValuesImport {
    
    private final static Pattern pattern = Pattern.compile("(\\w+?)\\s+([-]?\\d+[.]?\\d*)\\s+(\\w+?)");

    public static MeasuredValuesImport build() {
        return new MeasuredValuesImport();
    }

    public Map<Integer, MeasuredValue> read(final Path xmlPath) throws IOException {
        final TreeMap<Integer, MeasuredValue> map = new TreeMap<>();
        try(LineNumberReader reader = new LineNumberReader(new FileReader(xmlPath.toFile()))) {
            reader.lines().map(line -> {
                final Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    return new MeasuredValue(reader.getLineNumber(), matcher.group(1), matcher.group(2), matcher.group(3));
                }
                return null;
            }).forEach(m -> { if (m != null) { map.put(m.getIndex(), m); }});
        }
        return map;
    }

}
