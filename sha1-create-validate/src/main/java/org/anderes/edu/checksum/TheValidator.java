package org.anderes.edu.checksum;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.google.common.base.Preconditions.*;

public class TheValidator {

    private TheCalculator calculator = new TheSHA1Calculator();
    private final Logger loggerFile = LogManager.getLogger("FileWithError");
    
    public static TheValidator build() {
        return new TheValidator();
    }
    
    private TheValidator() {}

    public void validateAlt(final Path csvFile) throws IOException {
        checkNotNull(csvFile);
        
        try(LineNumberReader reader = new LineNumberReader(new FileReader(csvFile.toFile()))) {
            String line = reader.readLine();
            while(line != null) {
                final String[] split = line.split(";");
                if (split.length == 2) {
                    final Path path = Paths.get(split[0]);
                    final ResultData data = calculator.eval(path);
                    if (!data.getValue().equalsIgnoreCase(split[1])) {
                        loggerFile.warn("The checksum is wrong for the file '{}'.", path);
                    } 
                } else {
                    throw new IOException("wrong csv-file");
                }
                line = reader.readLine();
            }
        }
        
    }
    
    public void validate(final Path csvFile) throws IOException {
        checkNotNull(csvFile);
        
        Files.lines(csvFile).map(l -> l.split(";"))
                .parallel()
                .filter(values -> {
                    final Path path = Paths.get(values[0]);
                    try {
                        final ResultData data = calculator.eval(path);
                        return !data.getValue().equalsIgnoreCase(values[1]);
                    } catch (IOException e) {
                        return false;
                    }
                })
                .forEach(values -> loggerFile.warn("The checksum is wrong for the file {}.", values[0]));
    }
    
    
}
