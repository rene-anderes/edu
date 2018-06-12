package org.anderes.edu.sha1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class TheCreatorTest {
  
    private TheCreator creator;
    private final Path csvFile = Paths.get("target", "sha-1.csv");
    
    @Before
    public void setup() {
        creator = TheCreator.build();
        creator.setCsvFilePath(csvFile);
    }
    
    @Test
    public void shouldBeCreateSha1ForDirectory() throws Exception {
        // given
//        final Path theDirectory = Paths.get("d:", "Benutzer", "ra", "Dokumente");
        final Path theDirectory = Paths.get("target", "test-classes", "testdata");
        
        // when
        final long sha1 = creator.createSha1FromPath(theDirectory);
        
        // then
        assertThat(sha1, not(nullValue()));
//        assertThat(sha1, is(5L));
        assertThat(csvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeCreateSha1ForDirectoryAndBlacklist() throws Exception {
        // given
        final Path theDirectory = Paths.get("target", "test-classes", "testdata");
        creator.setBlacklist(Paths.get("target", "test-classes", "blacklist.txt"));
        
        // when
        final long sha1 = creator.createSha1FromPath(theDirectory);
        
        // then
        assertThat(sha1, not(nullValue()));
        assertThat(sha1, is(3L));
        assertThat(csvFile.toFile().exists(), is(true));
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
        assertThat(csvFile.toFile().exists(), is(true));
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
