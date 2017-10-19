package org.anderes.plugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.shared.model.fileset.FileSet;
import org.junit.jupiter.api.Test;

public class DataFileTest {

    @Test
    void shouldBeCorrectDataFiles() {
        // given
        final DatabaseDataMojo databaseDataMojo = new DatabaseDataMojo();
        databaseDataMojo.setFileset(createFileSet());
        // when
        final List<String> files = databaseDataMojo.getDataFiles();
        // then
        assertThat(files.size(), is(1));
    }

    private FileSet createFileSet() {
        FileSet fileSet = new FileSet();
        fileSet.setDirectory("src/test/resources");
        List<String> includes = new ArrayList<>();
        includes.add("*.json");
        fileSet.setIncludes(includes);
        return fileSet;
    }
}
