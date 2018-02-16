package org.anderes.logging.solution;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.anderes.logging.MeasuredValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        if (args == null || args.length != 1 || args[0] == null) {
            logger.warn("Es wurde kein Argument angegeben");
            System.exit(1);
            return;
        }
        final Path path = Paths.get(args[0]);
        logger.debug("Es wird versucht das File '{}' einzulesen.....", path.toString());
        try {
            final Map<Integer, MeasuredValue> values = MeasuredValuesImport.build().read(path);
            logger.debug("Das File konnte eingelesen werden.");
            save(values);
        } catch (IOException e) {
            logger.error(e);
            System.exit(2);
            return;
        }
        System.exit(0);
    }

    private static void save(Map<Integer, MeasuredValue> values) {

        // dummy methode
        
    }

}
