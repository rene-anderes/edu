package org.anderes.edu.jpa.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DbUnitRule implements TestRule {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface UsingDataSet {
        String[] value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface ShouldMatchDataSet {
        String[] value();
        String[] excludeColumns() default { };
        String[] orderBy() default { }; 
    }
    
    @Inject
    private IDatabaseTester databaseTester;
    
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before(description);
                try {
                    base.evaluate();
                } finally {
                    after(description);
                }
            }
        };
    }
    
    private void before(final Description description) throws Exception {
        final UsingDataSet annotation = description.getAnnotation(UsingDataSet.class);
        if (annotation == null) {
            return;
        }
        final String[] dataSetFiles = annotation.value();
        final CompositeDataSet dataSet = buildDataSet(dataSetFiles);
        final IDatabaseConnection databaseConnection = databaseTester.getConnection();
        final IDataSet filteredDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), dataSet);
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, filteredDataSet);
    }
    
    private CompositeDataSet buildDataSet(String[] dataSetFiles) throws DataSetException {
        final List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetFiles.length);
        for (String dataSetFile : dataSetFiles) {
            IDataSet dataset;
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
            dataset = loader.load(dataSetFile);
            dataSets.add(dataset);
        }
        return new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
    }
    
    private void after(final Description description) throws Exception {
        final ShouldMatchDataSet annotation = description.getAnnotation(ShouldMatchDataSet.class);
        if (annotation == null) {
            return;
        }
        final String[] dataSetFiles = annotation.value();
        final CompositeDataSet expectedDataSet = buildDataSet(dataSetFiles);
        final IDatabaseConnection databaseConnection = databaseTester.getConnection();
        final IDataSet databaseDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), databaseConnection.createDataSet());
        for (String tablename : expectedDataSet.getTableNames()) {
            final ITable expectedTable = buildFilteredAndSortedTable(expectedDataSet.getTable(tablename), annotation);
            final ITable actualTable = buildFilteredAndSortedTable(databaseDataSet.getTable(tablename), annotation);
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }
    
    /*package*/ Map<String, String[]> buildMapFromStringArray(final String[] excludeColumns) {
        final Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String excludeColumn : excludeColumns) {
            if (StringUtils.isBlank(excludeColumn) || excludeColumn.indexOf(".") < 1) {
                continue;
            }
            final String table = excludeColumn.substring(0, excludeColumn.indexOf("."));
            final String column = excludeColumn.substring(excludeColumn.indexOf(".") + 1, excludeColumn.length());
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
        final String tablename = originalTable.getTableMetaData().getTableName();
        ITable table;
        if (excludeColumns.containsKey(tablename)) {
            table = DefaultColumnFilter.excludedColumnsTable(originalTable, excludeColumns.get(tablename));
        } else {
            table = originalTable;
        }
        if (orderBy.containsKey(tablename)) {
            final SortedTable sortedTable = new SortedTable(table, orderBy.get(tablename));
            sortedTable.setUseComparable(true); 
            table = sortedTable;
        } 
        return table;
    }
}
