package org.anderes.edu.jpa.infrastructure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDriver;

public class DerbyDatabaseAdmin {

    @Inject
    private DataSource dataSource;

    public boolean backUpDatabase(String backupdirectory) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            final CallableStatement cs = connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
            cs.setString(1, backupdirectory);
            cs.execute();
            cs.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public void shutdown() {
        try {
            new EmbeddedDriver().connect("jdbc:derby:;shutdown=true", new Properties());
        } catch (SQLException ex) {
            // Error code that indicates successful shutdown
            if ("XJ015".equals(ex.getSQLState())) {
                System.out.println("Shutdown the Derby database");
            } else {
                System.err.println("Could not shutdown the Derby database. Message: " + ex.getMessage());
            }
        }
    }    
}
