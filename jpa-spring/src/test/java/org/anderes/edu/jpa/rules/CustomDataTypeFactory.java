package org.anderes.edu.jpa.rules;

import java.sql.Types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

public class CustomDataTypeFactory extends DefaultDataTypeFactory {
    
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        System.out.println("--------------------------" + sqlTypeName);
        if (sqlType == Types.TIMESTAMP) {
            return new CustomTimestampDataType();
        } else {
            return super.createDataType(sqlType, sqlTypeName);
        }
    }
}
