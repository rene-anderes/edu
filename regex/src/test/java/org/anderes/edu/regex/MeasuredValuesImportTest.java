package org.anderes.edu.regex;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Test;

public class MeasuredValuesImportTest {
    
    final private Path txtPath = Paths.get(".\\", "target", "classes", "org", "anderes", "edu", "regex", "measured_values.txt");
    
    @Test
    public void shouldBeCorrectValues() throws IOException {
        final MeasuredValuesImport valuesImport = MeasuredValuesImport.build();
        final Map<Integer, MeasuredValue> values = valuesImport.read(txtPath);
        assertThat(values, is(notNullValue()));
        assertThat(values.size(), is(9));
    }

}
