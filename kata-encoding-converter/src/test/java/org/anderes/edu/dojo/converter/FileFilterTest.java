package org.anderes.edu.dojo.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class FileFilterTest {

    private final Path path = Paths.get("target", "test-classes", "filterTestFiles");
    
    @Test
    public void shouldBeOneFile() {
        
        // given
        final String file = "pom.xml";
        final FileFilter fileFilter = new FileFilter(path, file, false);
        
        // when
        final List<Path> files = fileFilter.getFileList();
        
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
    }
    
    @Test
    public void shouldBeOneFileWithPattern() {
        // given
        final String file = "*.xml";
        final FileFilter fileFilter = new FileFilter(path, file, false);
        
        // when
        final List<Path> files = fileFilter.getFileList();
        
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(1));
    }
    
    @Test
    public void shouldBeTwoFiles() {
        // given
        final String file = "pom.xml";
        final FileFilter fileFilter = new FileFilter(path, file, true);
        
        // when
        final List<Path> files = fileFilter.getFileList();
        
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(2));
    }
    
    @Test
    public void shouldBeTwoFilesWithPattern() {
        
        // given
        final String file = "pom.{xml,xsd}";
        final FileFilter fileFilter = new FileFilter(path, file, true);
        
        // when
        final List<Path> files = fileFilter.getFileList();
     
        // then
        assertThat(files, is(notNullValue()));
        assertThat(files.size(), is(2));
    }
}
