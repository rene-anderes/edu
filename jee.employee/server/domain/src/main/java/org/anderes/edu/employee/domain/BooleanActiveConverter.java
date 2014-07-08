package org.anderes.edu.employee.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import static java.lang.Boolean.*;

/**
 * Konvertiert den {@code BOOLEAN} in ein 'active' bzw. 'inactive' für die Datenbank.
 * </p>
 * Beachte: Die Klasse muss dem Persistence Context bekannt sein; Eintragen in das XML persistence.xml
 */
@Converter
public class BooleanActiveConverter implements AttributeConverter<Boolean, String> {

    private enum MapValue {
        active, inactive
    };

    @Override
    public String convertToDatabaseColumn(final Boolean attribute) {
        if (attribute) {
            return MapValue.active.name();
        }
        return MapValue.inactive.name();
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        final MapValue data = MapValue.valueOf(dbData);
        switch (data) {
        case active:
            return TRUE;
        case inactive:
            return FALSE;
        default:
            return FALSE;
        }
    }

}
