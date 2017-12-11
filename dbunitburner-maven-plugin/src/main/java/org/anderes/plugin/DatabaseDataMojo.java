package org.anderes.plugin;

import static org.dbunit.IOperationListener.NO_OP_OPERATION_LISTENER;
import static org.dbunit.database.DatabaseConfig.PROPERTY_DATATYPE_FACTORY;
import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.dbunit.operation.DatabaseOperation.NONE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.anderes.edu.dbunitburner.DbUnitBurnerHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.util.fileloader.DataFileLoader;


@Mojo( name = "execute", defaultPhase = LifecyclePhase.GENERATE_TEST_RESOURCES )
public class DatabaseDataMojo extends AbstractMojo {
    
    @Parameter( property = "url", required = true )
    private String url;
    
    @Parameter( property = "driver", required = true )
    private String driver;
    
    @Parameter( property = "username", required = true )
    private String username;
    
    @Parameter( property = "password", required = true )
    private String password;
    
    @Parameter ( defaultValue = "${project.basedir}" )
    private String basedir;
    
    @Parameter ( required = true )
    private FileSet fileset;
    
    @Parameter ( property = "dataTypeFactory", required = false )
    private String dataTypeFactory;

    public DatabaseDataMojo() {
        super();
    }
    
    /*package*/ DatabaseDataMojo(final Properties databaseProperties) {
        url = databaseProperties.getProperty("url");
        username = databaseProperties.getProperty("user");
        password = databaseProperties.getProperty("password");
        driver = databaseProperties.getProperty("driver");
    }
    
    @Override
    public void execute() throws MojoExecutionException {
        try(final Connection connection = createConnection()) {
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
        final IDataTypeFactory resolveDataTypeFactory = DbUnitBurnerHelper.resolveDataTypeFactory(databaseTester.getConnection().getConnection());
        databaseConnection.getConfig().setProperty(PROPERTY_DATATYPE_FACTORY, resolveDataTypeFactory);
        getCustomDataTypeFactory().ifPresent(factory -> databaseConnection.getConfig().setProperty(PROPERTY_DATATYPE_FACTORY, factory));
        final IDataSet filteredDataSet = new FilteredDataSet(new DatabaseSequenceFilter(databaseConnection), dataSet);
        databaseTester.setOperationListener(NO_OP_OPERATION_LISTENER);
        databaseTester.setSetUpOperation(CLEAN_INSERT);
        databaseTester.setDataSet(filteredDataSet);
        databaseTester.onSetup();
        databaseTester.setTearDownOperation(NONE);
        databaseTester.onTearDown();
    }
    
    private Optional<IDataTypeFactory> getCustomDataTypeFactory() {
        if (dataTypeFactory == null || dataTypeFactory.isEmpty()) {
            return Optional.empty();
        }
        try {
            return (Optional<IDataTypeFactory>) Optional.of((IDataTypeFactory) Class.forName(dataTypeFactory).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            getLog().warn(e);
            return Optional.empty();
        }
    }
    
    private CompositeDataSet buildDataSet(List<String> dataSetFiles) throws DataSetException {
        final List<IDataSet> dataSets = new ArrayList<IDataSet>(dataSetFiles.size());
        for (String dataSetFile : dataSetFiles) {
            DataFileLoader loader = identifyLoader(dataSetFile);
            getLog().info("Load file: '" + dataSetFile + "'." + System.lineSeparator());
            IDataSet dataset = loader.load(dataSetFile);
            dataSets.add(dataset);
        }
        return new CompositeDataSet(dataSets.toArray(new IDataSet[dataSets.size()]));
    }
    
    private DataFileLoader identifyLoader(String dataSetFile) {
        DataFileLoader loader;
        if (dataSetFile.endsWith(".json")) {
            loader = new MojoJsonDataFileLoader();
        } else {
            throw new IllegalStateException("dbUnitBurner-plugin only supports JSON data sets for the moment");
        }
        return loader;
    }
    
    private Connection createConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            getLog().error(e); 
        }
        return DriverManager.getConnection(url, username, password);
    }

    /*package*/ void setFileset(final FileSet fileset) {
        this.fileset = fileset;
    }

    /*package*/ void setDataTypeFactory(String dataTypeFactory) {
        this.dataTypeFactory = dataTypeFactory;
    }

    /*package*/ List<String> getDataFiles() {
        if (fileset.getDirectory() == null) {
            fileset.setDirectory(basedir);
        }
        FileSetManager fileSetManager = new FileSetManager();
        
        return Arrays.asList(fileSetManager.getIncludedFiles(fileset)).stream()
                        .map(f -> FilenameUtils.concat(fileset.getDirectory(), f)).collect(Collectors.toList());
    }
}
