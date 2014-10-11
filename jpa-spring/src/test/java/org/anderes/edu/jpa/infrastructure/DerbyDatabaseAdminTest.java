package org.anderes.edu.jpa.infrastructure;

import java.sql.SQLException;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class DerbyDatabaseAdminTest {

    @Inject
    private DerbyDatabaseAdmin backupService;

    @After
    public void tearDown() throws Exception {
        backupService.shutdown();
    }

    @Test
    public void shouldBeBackup() throws SQLException {
        backupService.backUpDatabase("databaseBackup");
    }
}
