package org.anderes.plugin;

import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.operation.DatabaseOperation.*;
import static org.dbunit.IOperationListener.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.anderes.edu.dbunitburner.CustomDataTypeFactory;
import org.anderes.edu.dbunitburner.JsonDataFileLoader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.dbunit.util.fileloader.DataFileLoader;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.dbunit.util.fileloader.XlsDataFileLoader;


@Mojo( name = "execute", defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES )
public class DatabaseDataMojo extends AbstractMojo {
    
    @Parameter( property = "url", required = true )
    private String dbUrl;
    
    @Parameter( property = "username", required = true )
    private String username;
    
    @Parameter( property = "password", required = true )
    private String password;
    
    @Parameter( property = "dataFiles", required = true)
    private List<String> dataFiles;

    private Properties databaseProperties;

    public DatabaseDataMojo() {
        super();
    }
    
    /*package*/ DatabaseDataMojo(final Properties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }
    
    @Override
    public void execute() throws MojoExecutionException {
        if (databaseProperties == null) {
            databaseProperties = createProperties();
        }
        try(final Connection connection = createConnection(databaseProperties)) {
            final IDatabaseTester databaseTester = new DefaultDatabaseTester(new DatabaseConnection(connection));
            processUsingDataSet(getDataFiles() , databaseTester);
        } catch (Exception e) {
           getLog().error(e);
           throw new MojoExecutionException("WOW!!! Exception - " + e.getMessage(), e);
        } 
    }

    private void processUsingDataSet(final List<String> dataSetFiles, final IDatabaseTester databaseTester) throws Exception {
        final CompositeDataSet dataSet = buildDataSet(dataSetFiles);
        final IDatabaseConnection databaseConnection = databaseTester.getConnection();
        final CustomDataTypeFactory dataTypeFactory = new CustomDataTypeFactory(); 
        databaseConnection.getConfig().setProperty(PROPERTY_DATATYPE_FACTORY, dataTypeFactory);
        final IDataSet filteredDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), dataSet);
        databaseTester.setOperationListener(NO_OP_OPERATION_LISTENER);
        databaseTester.setSetUpOperation(CLEAN_INSERT);
        databaseTester.setDataSet(filteredDataSet);
        databaseTester.onSetup();
        databaseTester.setTearDownOperation(NONE);
        databaseTester.onTearDown();
    }
    
    private CompositeDataSet buildDataSet(List<String> dataSetFiles) throws DataSetException {
        final List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetFiles.size());
        for (String dataSetFile : dataSetFiles) {
            DataFileLoader loader = identifyLoader(dataSetFile);
            getLog().debug("Load file: '" + dataSetFile + "'.");
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
        } else if (dataSetFile.endsWith(".json")) {
            loader = new JsonDataFileLoader();
        } else {
            throw new IllegalStateException("dbUnitBurner-plugin only supports XLS, CSV, JSON or Flat XML data sets for the moment");
        }
        return loader;
    }
    
    private Connection createConnection(final Properties databaseProperties) throws SQLException {
        final String url = databaseProperties.getProperty("url");
        final String user = databaseProperties.getProperty("user");
        final String password = databaseProperties.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
    
    private Properties createProperties() {
        final Properties databaseProperties = new Properties();
        databaseProperties.setProperty("url", dbUrl);
        databaseProperties.setProperty("user", username);
        databaseProperties.setProperty("password", password);
        return databaseProperties;
    }

    public void setDataFiles(List<String> dataFiles) {
        this.dataFiles = dataFiles;
    }

    public List<String> getDataFiles() {
        if (dataFiles == null) {
            dataFiles = new ArrayList<>();
        }
        return dataFiles;
    }
}
