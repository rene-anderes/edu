package org.anderes.edu.jpa.rules;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dbunit.dataset.ITable;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.TypeCastException;

public class CustomTimestampDataType extends AbstractDataType {

    public CustomTimestampDataType() {
        super("TIMESTAMP", Types.TIMESTAMP, Timestamp.class, false);
    }

    @Override
    public Object typeCast(Object value) throws TypeCastException {
        if (value == null || value == ITable.NO_VALUE) {
            return null;
        }
        if (value instanceof java.sql.Timestamp) {
            return value;
        }
        if (value instanceof java.util.Date) {
            java.util.Date date = (java.util.Date) value;
            return new java.sql.Timestamp(date.getTime());
        }

        if (value instanceof Long) {
            Long date = (Long) value;
            return new java.sql.Timestamp(date.longValue());
        }
        if (value instanceof String) {
            String stringValue = (String) value;
            // Probably a java.sql.Date, try it just in case!
            if (stringValue.length() == 10) {
                try {
                    long time = java.sql.Date.valueOf(stringValue).getTime();
                    return new java.sql.Timestamp(time);
                } catch (IllegalArgumentException e) {
                    // Was not a java.sql.Date, let Timestamp handle this value
                }
            }
            try {
                String formats[] = { "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm a", "yyyy-MM-dd HH:mm:ss.fffffffff" };
                Timestamp ts = null;
                for (int i = 0; i < formats.length; i++) {
                    SimpleDateFormat sdf = new SimpleDateFormat(formats[i]);
                    try {
                        Date date = sdf.parse(stringValue);
                        ts = new Timestamp(date.getTime());
                        return ts;
                    } catch (ParseException e) {
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new TypeCastException(value, this, e);
            }
        }
        throw new TypeCastException(value, this);
    }

    @Override
    public boolean isDateTime() {
        return true;
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException, TypeCastException {
        Timestamp value = resultSet.getTimestamp(column);
        if (value == null || resultSet.wasNull()) {
            return null;
        }
        return value;
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement) throws SQLException, TypeCastException {
        statement.setTimestamp(column, (java.sql.Timestamp) typeCast(value));
    }

}
