package org.anderes.edu.jpa.rules;

import static org.apache.commons.lang3.StringUtils.CR;
import static org.apache.commons.lang3.StringUtils.LF;
import static org.apache.commons.lang3.StringUtils.remove;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class SqlHelper {

    public static List<String> extractSqlCommands(final Path sqlFilePath) throws IOException {
        
        final InputStream is = ClassLoader.getSystemResourceAsStream(sqlFilePath.toString());
        if (is == null) {
            final String msg = "Could not find file named=" + sqlFilePath;
            throw new IOException(msg);
        }
        final List<String> commands = new ArrayList<String>();
        final Scanner scanner = new Scanner(is);
        scanner.useDelimiter(";");
        
        while(scanner.hasNext()) {
            commands.add(remove(scanner.next(), CR + LF));
        }
        scanner.close();
        return commands;
    }
    
    public static int[] execute(final Connection connection, final List<String> queries) throws SQLException {
        final Statement stmt = connection.createStatement();
        for(String query : queries) {
            stmt.addBatch(query);
        }
        return stmt.executeBatch();
    }

}
