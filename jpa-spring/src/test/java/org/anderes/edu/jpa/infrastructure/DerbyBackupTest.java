package org.anderes.edu.jpa.infrastructure;

import java.sql.SQLException;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class DerbyBackupTest {

    @Inject
    private DerbyDatabaseBackup backupService;
    
    @Test
    public void shouldBeBackup() throws SQLException {
       backupService.backUpDatabase("databaseBackup"); 
    }
}
