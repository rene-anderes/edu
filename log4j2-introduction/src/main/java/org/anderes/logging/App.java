package org.anderes.logging;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        if (args == null || args.length != 1 || args[0] == null) {
            System.err.println("Es wurde kein Argument angegeben");
            System.exit(1);
            return;
        }
        final Path path = Paths.get(args[0]);
        System.out.printf("Es wird versucht das File '%s' einzulesen..... %n", path.toString());
        try {
            final Map<Integer, MeasuredValue> values = MeasuredValuesImport.build().read(path);
            System.out.println("Das File konnte eingelesen werden.");
            save(values);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
            return;
        }
        System.exit(0);
    }

    private static void save(Map<Integer, MeasuredValue> values) {

        // dummy methode
        
    }

}
