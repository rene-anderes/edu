package org.anderes.logging;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MeasuredValuesImportTest {
    
    final private Path TXTPATH = Paths.get("target", "classes", "data", "measured_values.txt");
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void shouldBeCorrectValues() throws IOException {
        
        // when
        final Map<Integer, MeasuredValue> values = MeasuredValuesImport.build().read(TXTPATH);
        
        // then
        assertThat(values, is(notNullValue()));
        assertThat(values.size(), is(9));
        assertThat(values.get(1).getIndex(), is(1));
        assertThat(values.get(1).getValue(), is("25"));
        assertThat(values.get(1).getUnit(), is("Grad"));
        assertThat(values.get(1).getDescription(), is("Temperatur"));
        assertThat(values.get(2).getIndex(), is(2));
        assertThat(values.get(2).getValue(), is("200"));
        assertThat(values.get(2).getUnit(), is("mg"));
        assertThat(values.get(2).getDescription(), is("Gewicht"));
        assertThat(values.get(3).getIndex(), is(3));
        assertThat(values.get(3).getValue(), is("150"));
        assertThat(values.get(3).getUnit(), is("ml"));
        assertThat(values.get(3).getDescription(), is("Wasser"));
        assertThat(values.get(4).getIndex(), is(4));
        assertThat(values.get(4).getValue(), is("24.5"));
        assertThat(values.get(4).getUnit(), is("Grad"));
        assertThat(values.get(4).getDescription(), is("Temperatur"));
        assertThat(values.get(5).getIndex(), is(5));
        assertThat(values.get(5).getValue(), is("1.01"));
        assertThat(values.get(5).getUnit(), is("kg"));
        assertThat(values.get(5).getDescription(), is("Gewicht"));
        assertThat(values.get(6).getIndex(), is(6));
        assertThat(values.get(6).getValue(), is("1.2"));
        assertThat(values.get(6).getUnit(), is("l"));
        assertThat(values.get(6).getDescription(), is("Wasser"));
        assertThat(values.get(7).getIndex(), is(7));
        assertThat(values.get(7).getValue(), is("-1.3"));
        assertThat(values.get(7).getUnit(), is("Grad"));
        assertThat(values.get(7).getDescription(), is("Temperatur"));
        assertThat(values.get(8).getIndex(), is(8));
        assertThat(values.get(8).getValue(), is("2.5"));
        assertThat(values.get(8).getUnit(), is("g"));
        assertThat(values.get(8).getDescription(), is("Gewicht"));
        assertThat(values.get(9).getIndex(), is(9));
        assertThat(values.get(9).getValue(), is("50"));
        assertThat(values.get(9).getUnit(), is("ml"));
        assertThat(values.get(9).getDescription(), is("Wasser"));
    }

    @Test
    public void shouldBeFileNotFoundException() throws IOException {
        thrown.expect(FileNotFoundException.class);
        thrown.expectMessage("wrong_path (Das System kann die angegebene Datei nicht finden)");
        
        MeasuredValuesImport.build().read(Paths.get("wrong_path"));
    }
    
    @Test
    public void shouldBeIllegalArgumentException() throws IOException {
        thrown.expect(IllegalArgumentException.class);
        
        MeasuredValuesImport.build().read(null);
    }
}
