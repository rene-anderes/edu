package org.anderes.logging.solution;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.anderes.logging.MeasuredValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MeasuredValuesImport {
    
    private final static Pattern PATTERN = Pattern.compile("(\\w+)\\s+([-]?\\d+[.]?\\d*)\\s+(\\w+)");
    private static Logger logger = LogManager.getLogger();
    
    public static MeasuredValuesImport build() {
        logger.trace("Neuer Importer instanziert.");
        return new MeasuredValuesImport();
    }

    public Map<Integer, MeasuredValue> read(final Path xmlPath) throws IOException {
        if (xmlPath == null) {
            logger.error("Programmierfehler: Der Parameter 'xmlPath' darf nicht null sein.");
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
