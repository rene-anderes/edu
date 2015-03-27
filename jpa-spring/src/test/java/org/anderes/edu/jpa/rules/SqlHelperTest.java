package org.anderes.edu.jpa.rules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SqlHelperTest {
    
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeExtractCommands() throws IOException {
        Path sqlFile = Paths.get("sql", "DeleteTableContentScript.sql");
        List<String> commands = SqlHelper.extractSqlCommands(sqlFile);
        
        assertThat(commands, is(notNullValue()));
        assertThat(commands.size(), is(3));
        assertThat(commands.get(0), is("delete from TAGS"));
        assertThat(commands.get(1), is("delete from INGREDIENT"));
        assertThat(commands.get(2), is("delete from RECIPE"));
    }
    
    @Test(expected = IOException.class)
    public void shouldBeWrongPath() throws IOException {
        Path sqlFile = Paths.get("notValid", "sql", "DeleteTableContentScript.sql");
        SqlHelper.extractSqlCommands(sqlFile);
    }

    @Test
    public void shouldBeExecuteBatchCommands() throws SQLException {
        // given
        List<String> commands = new ArrayList<String>(3);
        commands.add("delete from INGREDIENT");
        commands.add("delete from TAGS");
        commands.add("delete from RECIPE");
        Connection connection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        int[] batchResult = { 2, 4, 1 };
        when(mockStatement.executeBatch()).thenReturn(batchResult);
        when(connection.createStatement()).thenReturn(mockStatement);
        // when
        int[] values = SqlHelper.execute(connection, commands);
        // then
        assertThat(values.length, is(3));
        verify(mockStatement).addBatch("delete from INGREDIENT");
        verify(mockStatement).addBatch("delete from TAGS");
        verify(mockStatement).addBatch("delete from RECIPE");
        verify(mockStatement).executeBatch();
    }
}
