package org.anderes.edu.jpa.infrastructure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class DerbyDatabaseBackup {

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
    
}
