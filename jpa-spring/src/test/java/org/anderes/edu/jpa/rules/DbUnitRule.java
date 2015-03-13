package org.anderes.edu.jpa.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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
    protected void before(final Description description) throws Exception {
        final UsingDataSet annotation = description.getAnnotation(UsingDataSet.class);
        if (annotation == null) {
            return;
        }
        final String[] dataSetFiles = annotation.value();
        CompositeDataSet dataSet = buildDataSet(dataSetFiles);
        IDatabaseConnection databaseConnection = databaseTester.getConnection();
        IDataSet filteredDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), dataSet);
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
            } if (dataSetFile.endsWith(".xls")) {
                loader = new XlsDataFileLoader();
            }else {
                throw new IllegalStateException("DbUnitRule only supports XLS, CSV or Flat XML data sets for the moment");
            }
            dataset = loader.load(dataSetFile);
            dataSets.add(dataset);
        }
        return new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
    }
    
    protected void after(final Description description) throws Exception {
        final ShouldMatchDataSet annotation = description.getAnnotation(ShouldMatchDataSet.class);
        if (annotation != null) {
            final String[] dataSetFiles = annotation.value();
            final CompositeDataSet expectedDataSet = buildDataSet(dataSetFiles);
            final IDatabaseConnection databaseConnection = databaseTester.getConnection();
            final IDataSet databaseDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), expectedDataSet);
            for (String tablename : expectedDataSet.getTableNames()) {
                final ITable expectedTable = expectedDataSet.getTable(tablename);
                final ITable actualTable = databaseDataSet.getTable(tablename);
                Assertion.assertEquals(expectedTable, actualTable);
            }
        }
    }

}
