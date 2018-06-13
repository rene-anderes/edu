package org.anderes.edu.checksum;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheValidator {

    private TheCalculator calculator = new TheSHA1Calculator();
    private final Logger loggerFile = LogManager.getLogger("FileWithError");
    private final Logger logger = LogManager.getLogger();
    
    public static TheValidator build() {
        return new TheValidator();
    }
    
    private TheValidator() {}
    
    /**
     * Validiert anhand des übergebenen CSV-Files das Filesystem.
     * 
     * @param csvFile CSV-File mit Pfanangaben und Checksumme
     * @return {@code true}, wenn alle Files valide sind, sonst {@code false}
     * @throws IOException
     */
    public boolean validate(final Path csvFile) throws IOException {
        checkNotNull(csvFile);
        
        logger.info("Validate the file '{}'", csvFile.toAbsolutePath().toString());
        final long count = Files.lines(csvFile).map(l -> l.split(";"))
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
                    .peek(values -> loggerFile.warn("The checksum is wrong for the file {}.", values[0]))
                    .collect(Collectors.counting());
        return count == 0;
    }
    
    
}
