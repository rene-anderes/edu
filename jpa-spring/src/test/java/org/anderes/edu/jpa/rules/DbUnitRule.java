package org.anderes.edu.jpa.rules;

import static org.apache.commons.lang3.StringUtils.containsNone;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.junit.Assert.fail;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Diese JUnit-Rule bietet die Möglichkeit mittels Annotations die für den
 * Test notwendigen DB-Files (in den von DBUnit unterstützten Formaten)
 * anzugeben:<br>
 * <p><code>@UsingDataSet(value = { "/prepaire.xls" })</code></p>
 * Es können auch mehrere Files angegeben werden:
 * <p><code>@UsingDataSet(value = { "/Person.xls", "Offer.xls" })</code></p>
 * </p>
 * Ebenfalls können das oder die Files angegeben werden mittels denen DBUnit
 * die Datenbank nach der Test-Methode überprüft:
 * <p><code>@ShouldMatchDataSet(<br>
 * &nbsp;&nbsp; value = { "/expectedAfterDelete.xls" },<br>
 * &nbsp;&nbsp; excludeColumns = { "INGREDIENT.ID" },<br>
 * &nbsp;&nbsp; orderBy = { "RECIPE.UUID", "INGREDIENT.ID" })</code>
 * </p>
 * Wie das Beispiel zeigt können Sortierung und Ausnahmen für den Vergleich
 * definiert werden. Format: [Tabellenname].[Spaltennamen]
 * <p>
 * Wenn gwünscht, kann auch ein SQL-Script angegeben werden, mittels dem die Tabelle(n)
 * vor der Test-Methode gelöscht werden.
 * <p><code>@CleanupUsingScript(value = { "/sql/DeleteTableContentScript.sql" })</code></p>
 * <p>
 * Es wird im Klassenpfad nach den entsprechenden Files gesucht.<br>
 * Als Basis für diese JUnit-Rule dient DBUnit ({@link siehe http://dbunit.sourceforge.net/})
 * <p>
 * Die Annotierungen <code>@UsingDataSet</code> und <code>@CleanupUsingScript</code> können
 * sowohl für eine Methode als auch für eine Klasse gesetzt werden. Werden die Annotierungen
 * auf der Klasse angebracht, so wird für jede Test-Methode die entsprechenden Files verwendet.
 * 
 * @author René Anderes
 *
 */
public class DbUnitRule implements TestRule {

    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface UsingDataSet {
        String[] value();
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    public static @interface ShouldMatchDataSet {
        String[] value();
        String[] excludeColumns() default { };
        String[] orderBy() default { }; 
    }
    
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface CleanupUsingScript {
        String[] value();
    }
    
    private IDatabaseTester databaseTester;
    
    /*package*/ DbUnitRule() {
        super();
    }
    
    public DbUnitRule(Connection connection) {
        Validate.notNull(connection, "connection darf nicht null sein");
        try {
            databaseTester = new DefaultDatabaseTester(new DatabaseConnection(connection));
        } catch (DatabaseUnitException e) {
            fail(e.getMessage());
        }
    }
    
    public DbUnitRule(final Connection connection, final IDataTypeFactory dataTypeFactory) {
        Validate.notNull(dataTypeFactory, "dataTypeFactory darf nicht null sein");
        Validate.notNull(connection, "connection darf nicht null sein");
        try {
            final DatabaseConnection databaseConnection = new DatabaseConnection(connection); 
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
            databaseTester = new DefaultDatabaseTester(databaseConnection);
        } catch (DatabaseUnitException e) {
            fail(e.getMessage());
        }
    }
    
    public DbUnitRule(final IDatabaseTester databaseTester) {
        Validate.notNull(databaseTester, "DatabaseTester darf nicht null sein");
        this.databaseTester = databaseTester;
    }
    
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before(description);
                base.evaluate();
                after(description);
            }
        };
    }
    
    private void before(final Description description) throws Exception {
        final UsingDataSet usingDataSet = extractUsingDataSet(description);
        final CleanupUsingScript cleanupUsingScript = extractCleanupUsingScript(description);
        DatabaseOperation databaseOperation = DatabaseOperation.CLEAN_INSERT;
        if (cleanupUsingScript != null) {
            processCleanupScripts(cleanupUsingScript);
        }
        if (usingDataSet != null) {
            processUsingDataSet(usingDataSet, databaseOperation);
        }
    }

    private CleanupUsingScript extractCleanupUsingScript(final Description description) {
        CleanupUsingScript cleanupUsingScript = description.getAnnotation(CleanupUsingScript.class);
        if (cleanupUsingScript == null) {
            cleanupUsingScript = description.getTestClass().getAnnotation(CleanupUsingScript.class);
        }
        return cleanupUsingScript;
    }

    private UsingDataSet extractUsingDataSet(final Description description) {
        UsingDataSet usingDataSet = description.getAnnotation(UsingDataSet.class);
        if (usingDataSet == null) {
            usingDataSet = description.getTestClass().getAnnotation(UsingDataSet.class);
        }
        return usingDataSet;
    }

    private void processUsingDataSet(final UsingDataSet usingDataSet, final DatabaseOperation databaseOperation) throws DataSetException, Exception, SQLException {
        final String[] dataSetFiles = usingDataSet.value();
        final CompositeDataSet dataSet = buildDataSet(dataSetFiles);
        final IDatabaseConnection databaseConnection = databaseTester.getConnection();
        final IDataSet filteredDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), dataSet);
        databaseTester.setOperationListener(IOperationListener.NO_OP_OPERATION_LISTENER);
        databaseTester.setSetUpOperation(databaseOperation);
        databaseTester.setDataSet(filteredDataSet);
        databaseTester.onSetup();
    }
    
    private void processCleanupScripts(final CleanupUsingScript cleanupUsingScript) throws Exception {
        final String[] cleanupFiles = cleanupUsingScript.value();
        for (String cleanupFile : cleanupFiles) {
            final List<String> commands = SqlHelper.extractSqlCommands(Paths.get(cleanupFile));
            int[] values = SqlHelper.execute(databaseTester.getConnection().getConnection(), commands);
            for (int index = 0 ; index < commands.size(); index++) {
                System.out.println(commands.get(index) + ", Result: " + values[index]);
            }
        }
    }

    /*package*/ CompositeDataSet buildDataSet(String[] dataSetFiles) throws DataSetException {
        final List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetFiles.length);
        for (String dataSetFile : dataSetFiles) {
            DataFileLoader loader = identifyLoader(dataSetFile);
            IDataSet dataset = loader.load(dataSetFile);
            dataSets.add(dataset);
        }
        return new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
    }

    private DataFileLoader identifyLoader(String dataSetFile) {
        DataFileLoader loader;
        if (dataSetFile.endsWith(".xml")) {
            loader = new FlatXmlDataFileLoader();
        } else if (dataSetFile.endsWith(".csv")) {
            loader = new CsvDataFileLoader();
        } else if (dataSetFile.endsWith(".xls")) {
            loader = new XlsDataFileLoader();
        } else {
            throw new IllegalStateException("DbUnitRule only supports XLS, CSV or Flat XML data sets for the moment");
        }
        return loader;
    }
    
    private void after(final Description description) throws Exception {
        final ShouldMatchDataSet annotation = description.getAnnotation(ShouldMatchDataSet.class);
        if (annotation == null) {
            return;
        }
        compareDatabase(annotation);
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
    }

    private void compareDatabase(final ShouldMatchDataSet annotation) throws Exception {
        final String[] dataSetFiles = annotation.value();
        final CompositeDataSet expectedDataSet = buildDataSet(dataSetFiles);
        final IDatabaseConnection databaseConnection = databaseTester.getConnection();
        final IDataSet databaseDataSet = databaseConnection.createDataSet();
      
        for (String tablename : expectedDataSet.getTableNames()) {
            final ITable expectedTable = buildFilteredAndSortedTable(expectedDataSet.getTable(tablename), annotation);
            final ITable actualTable = buildFilteredAndSortedTable(databaseDataSet.getTable(tablename), annotation);
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }
    
    /*package*/ Map<String, String[]> buildMapFromStringArray(final String[] array) {
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String value : array) {
            if (containsNone(value, ".")) {
                continue;
            }
            final String table = substringBefore(value, ".").toUpperCase();
            final String column = substringAfter(value, ".").toUpperCase();
            if (map.containsKey(table)) {
                map.get(table).add(column);
            } else {
                List<String> list = new ArrayList<String>();
                list.add(column);
                map.put(table, list);
            }
        }
        final Map<String, String[]> returnValue = new HashMap<String, String[]>(map.size());
        for (String tablename : map.keySet()) {
            returnValue.put(tablename, map.get(tablename).toArray(new String[0]));
        }
        return returnValue;
    }
    
    private ITable buildFilteredAndSortedTable(final ITable originalTable, final ShouldMatchDataSet annotation) throws DataSetException {
        final Map<String, String[]> excludeColumns = buildMapFromStringArray(annotation.excludeColumns());
        final Map<String, String[]> orderBy = buildMapFromStringArray(annotation.orderBy());
        final String tablename = originalTable.getTableMetaData().getTableName().toUpperCase();
        ITable table = sortTable(orderBy, tablename, originalTable); 
        table = filterTable(table, excludeColumns, tablename);
        return table;
    }

    private ITable sortTable(final Map<String, String[]> orderBy, final String tablename, ITable table) throws DataSetException {
        if (orderBy.containsKey(tablename)) {
            final SortedTable sortedTable = new SortedTable(table, orderBy.get(tablename));
            sortedTable.setUseComparable(true); 
            return sortedTable;
        }
        return table;
    }

    private ITable filterTable(final ITable originalTable, final Map<String, String[]> excludeColumns, final String tablename) throws DataSetException {
        ITable table;
        if (excludeColumns.containsKey(tablename)) {
            table = DefaultColumnFilter.excludedColumnsTable(originalTable, excludeColumns.get(tablename));
        } else {
            table = originalTable;
        }
        return table;
    }
}
