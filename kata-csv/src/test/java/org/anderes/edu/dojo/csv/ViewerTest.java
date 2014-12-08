package org.anderes.edu.dojo.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ViewerTest {

    private List<List<String>> records;
    private List<String> header;
    
    @Before
    public void setUp() throws Exception {
        header = new ArrayList<String>(1);
        header.add("Name");
        header.add("Age");
        header.add("City");
        records = new ArrayList<List<String>>(7);
        records.add(Arrays.asList("Peter", "42", "New York"));
        records.add(Arrays.asList("Paul", "57", "London"));
        
    }

    @Test
    public void shouldBeShow() throws UnsupportedEncodingException {
        final Viewer viewer = new Viewer(header, records);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        viewer.show(outputStream);
        assertThat(outputStream.toString("UTF-8"), is(expectedString()));
    }

    private String expectedString() {
        String text = "Name |Age|City    |\n";
        text += "-----+---+--------+\n";
        text += "Peter|42 |New York|\n";
        text += "Paul |57 |London  |\n";
        text += "-----+---+--------+\n";
        return text;
    }
}
