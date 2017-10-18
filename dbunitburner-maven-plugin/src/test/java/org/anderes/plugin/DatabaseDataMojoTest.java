package org.anderes.plugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.anderes.edu.dbunitburner.SqlHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
class DatabaseDataMojoTest {

    private Properties databaseProperties;
    
    @BeforeEach
    public void setup() {
        final String dbUrl = Paths.get("derbyEmbeddedDatabase").toAbsolutePath().toString();
        databaseProperties = new Properties();
        databaseProperties.setProperty("url", "jdbc:derby:" + dbUrl + ";create=true");
        databaseProperties.setProperty("user", "APP");
        databaseProperties.setProperty("password", "APP");
        createTables();
    }
    
    private void createTables() {
        try(Connection connection = createConnection(databaseProperties)) {
            final Collection<String> sqlCommands = SqlHelper.extractSqlCommands(Paths.get("createTables.sql"));
            SqlHelper.execute(connection, sqlCommands);
        } catch (SQLException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test @DisplayName("😎")
    public void shouldBeExecute() throws Exception {
        final DatabaseDataMojo mojo = new DatabaseDataMojo(databaseProperties);
        final List<String> dataFiles = new ArrayList<>();
        dataFiles.add("/test.json");
        mojo.setDataFiles(dataFiles);
        mojo.execute();
        assertThat(checkDatabase(), is(true));
    }
    
    private boolean checkDatabase() {
        try (Connection connection = createConnection(databaseProperties)) {
            checkRecipe(connection);
            checkIngredient(connection);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
        return true;
    }

    private void checkIngredient(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM INGREDIENT WHERE RECIPE_ID = ? ORDER BY ID ASC";
        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 100);
        final ResultSet rs = statement.executeQuery();
        assertThat("Der Datensatz existiert nicht.", rs.next(), is(true));
        assertThat(rs.getInt("ID"), is(1000));
        assertThat(rs.getString("DESCRIPTION"), is("Kokosmilch"));
        assertThat("Der Datensatz existiert nicht.", rs.next(), is(true));
        assertThat(rs.getInt("ID"), is(1001));
        assertThat(rs.getString("DESCRIPTION"), is("Spaghetti"));
    }

    private void checkRecipe(Connection connection) throws SQLException {
        final String sql = "SELECT * FROM RECIPE WHERE ID = ?";
        final PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 100);
        final ResultSet rs = statement.executeQuery();
        assertThat("Der Datensatz existiert nicht.", rs.next(), is(true));
        assertThat(rs.getString("TITLE"), is("Arabische Spaghetti"));
        assertThat(rs.getString("PREAMBLE"), is("Da bei diesem Rezept das Scharfe (Curry) mit dem Süssen (Sultaninen) gemischt wird, habe ich diese Rezept \"Arabische Spaghetti\" benannt."));
        final Date expectedDate = Date.valueOf("2014-01-22");
        assertThat(rs.getDate("ADDINGDATE"), is(expectedDate));
    }

    private Connection createConnection(final Properties databaseProperties) throws SQLException {
        final String url = databaseProperties.getProperty("url");
        final String user = databaseProperties.getProperty("user");
        final String password = databaseProperties.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
}
