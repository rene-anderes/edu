package org.anderes.edu.dojo.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class JConverterTest {
    
    private final String file = "*.txt";
    private final Path dir = Paths.get("target", "test-classes", "converterTestFiles");
    
    @Test
    public void shouldBeConvert() throws IOException {
        
        // given
        String[] args = { file, "-d", dir.toString(), "-convertFrom", "ISO-8859-1", "-convertTo", "UTF-8" };
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        // when
        final JConverter jConverter = new JConverter(args, output);
        
        // then
        assertThat(jConverter.checkArguments(), is(true));
        final List<Path> files = jConverter.getFileList();
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
        jConverter.convert(files.iterator().next());
        assertThat(output.toString(), is("File '" + dir.toString() + "\\" + "ISO-8859-1 Text.txt' konvertiert."));
    }
    
    @Test
    public void shouldBeFileListFromWorkingDir() {
        // given
        String[] args = { "pom.xml", "-convertFrom", "ISO-8859-1", "-convertTo", "UTF-8" };
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        // when
        final JConverter jConverter = new JConverter(args, output);
        final List<Path> files = jConverter.getFileList();
        
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
        
    }
}
