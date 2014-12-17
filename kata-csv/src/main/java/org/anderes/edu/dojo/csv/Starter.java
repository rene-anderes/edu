package org.anderes.edu.dojo.csv;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.anderes.edu.dojo.csv.CommandLineInterface.Command;

public class Starter {

    public static void main(String[] args) {
        final Path csvFile = Paths.get("target/classes", "persons.csv");
        final CsvReader7 csvReader = new CsvReader7(csvFile);
        final List<String> header = csvReader.readHeader();
        final Paging paging = new Paging(csvReader.readRecords());
        Command command = show(header, paging.firstPage(), System.out);
        do {
            List<List<String>> records = new ArrayList<List<String>>(0);
            switch (command) {
                case NEXT:
                    records = paging.nextPage();
                    break;
                case PREV:
                    records = paging.previousPage();
                    break;
                case FIRST:
                    records = paging.firstPage();
                    break;
                case LAST:
                    records = paging.lastPage();
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                default:
                    break;
            }
            command = show(header, records, System.out);
        } while(true);
    }

    private static Command show(List<String> header, List<List<String>> records, OutputStream outputStream) {
        final Viewer view = new Viewer(header, records);
        view.show(System.out);
        final CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
        return cli.showAndWait();
    }
}
