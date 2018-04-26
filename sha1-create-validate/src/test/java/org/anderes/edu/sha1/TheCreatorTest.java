package org.anderes.edu.sha1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class TheCreatorTest {
  
    private TheCreator creator;
    private final Path logFile = Paths.get("target", "error.log");
    private final Path csvFile = Paths.get("target", "sha-1.csv");
    
    @Before
    public void setup() {
        creator = TheCreator.build();
        final File log = logFile.toFile();
        if (log.exists()) {
            assertThat(log.delete(), is(true));
        }
        creator.setCsvFilePath(csvFile);
    }
    
    @Test
    public void shouldBeCreateSha1ForDirectory() throws Exception {
        // given
//        final Path theDirectory = Paths.get("e:", "Win7.bak");
//        final Path theDirectory = Paths.get("c:", "Users", "NA247", ".m2");
        final Path theDirectory = Paths.get("target", "test-classes", "testdata");
        
        // when
        final long sha1 = creator.createSha1FromPath(theDirectory);
        
        assertThat(sha1, not(nullValue()));
        assertThat(sha1, is(3L));
        assertThat(logFile.toFile().exists(), is(false));
        assertThat(csvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeHandleOneFile() {
        //given
        final Path theFile = Paths.get("target", "test-classes", "testdata", "Kata-Testdaten.pdf");
        
        // when
        creator.handleFile(theFile);
        
        // then
        assertThat(creator.queueSize(), is(1l));
        assertThat(logFile.toFile().exists(), is(false));
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
        assertThat(logFile.toFile().exists(), is(true));
    }
}
