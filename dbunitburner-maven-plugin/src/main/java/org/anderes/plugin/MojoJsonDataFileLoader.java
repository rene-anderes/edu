package org.anderes.plugin;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.anderes.edu.dbunitburner.JsonDataFileLoader;
import org.dbunit.DatabaseUnitRuntimeException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.IDataSet;

public class MojoJsonDataFileLoader extends JsonDataFileLoader {

    @Override
    public IDataSet load(String filename) throws DatabaseUnitRuntimeException {
        IDataSet dataSet = new DefaultDataSet();
        final Path jsonFile = Paths.get(filename);
        try {
            final URL url = jsonFile.toUri().toURL();
            dataSet = loadDataSet(url);
            dataSet = processReplacementTokens(dataSet);
        } catch (DataSetException | IOException e) {
            final String msg = String.format("DataSetException occurred loading data set file name='%s', msg='%s'", filename, e.getLocalizedMessage());
            throw new DatabaseUnitRuntimeException(msg, e);
        } 
        return dataSet;
    }

}
