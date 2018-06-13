package org.anderes.edu.checksum;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TheCreatorTest {
  
    private TheCreator creator;
    private final Path csvFile = Paths.get("target", "test-sha1.csv");
    private final Path defaultCsvFile = Paths.get("checksum.csv");
    
    @Before
    public void setup() {
        creator = TheCreator.build();
    }
    
    @After
    public void shutdown() {
        csvFile.toFile().delete();
        defaultCsvFile.toFile().delete();
    }
    
    @Test
    public void shouldBeCreateSha1ForDirectory() throws Exception {
        // given
//        final Path theDirectory = Paths.get("d:", "Benutzer", "ra", "Dokumente");
        final Path theDirectory = Paths.get("target", "test-classes", "testdata");
        creator.setCsvFilePath(csvFile);
        
        // when
        final long count = creator.createChecksumFromPath(theDirectory);
        
        // then
        assertThat(count, not(nullValue()));
        assertThat(count, is(5L));
        assertThat(csvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeCreateSha1ForDirectoryAndBlacklist() throws Exception {
        // given
        final Path theDirectory = Paths.get("target", "test-classes", "testdata");
        creator.setBlacklist(Paths.get("target", "test-classes", "blacklist.txt"));
        
        // when
        final long sha1 = creator.createChecksumFromPath(theDirectory);
        
        // then
        assertThat(sha1, not(nullValue()));
        assertThat(sha1, is(3L));
        assertThat(defaultCsvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void ShouldBeCheckFileInBlacklist() {
        //given
        final Path theFile = Paths.get("target", "test-classes", "testdata", "other", "Logging Frameworks.pdf");
        creator.setBlacklist(Paths.get("target", "test-classes", "blacklist.txt"));
        
        // when
        final boolean ok = creator.isInBlacklist(theFile);
        
        // then
        assertThat(ok, is(true));
        
    }
    
    @Test
    public void shouldBeHandleOneFile() {
        //given
        final Path theFile = Paths.get("target", "test-classes", "testdata", "Kata-Testdaten.pdf");
        
        // when
        creator.handleFile(theFile);
        
        // then
        assertThat(creator.queueSize(), is(1l));
        assertThat(defaultCsvFile.toFile().exists(), is(false));
    }
    
    @Test
    public void shouldBeWriteErrorLog() {
        //given
        final Path theFile = Paths.get("target", "test-classes", "testdata", "wrong.pdf");
        
        // when
        creator.handleFile(theFile);
        
        // then
        assertThat(creator.queueSize(), is(0l));
    }
}
