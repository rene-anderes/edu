package org.anderes.edu.dojo.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class FileFilterTest {

    @Test
    public void shouldBeOneFile() {
        // given
        final String file = "Text.txt";
        final Path path = Paths.get("testfiles");
        FileFilter fileFilter = new FileFilter(path, file);
        
        // when
        List<Path> files = fileFilter.getFileList();
        
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
    }
}
