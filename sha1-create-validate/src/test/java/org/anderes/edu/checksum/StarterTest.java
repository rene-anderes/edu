package org.anderes.edu.checksum;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class StarterTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    private final Path theDirectory = Paths.get("target", "test-classes", "testdata");
    private final Path csvFile = Paths.get("target", "test-sha1.csv");
    private final Path toValidate = Paths.get("target", "test-classes", "testdata", "toValidate.csv");
    private final Path wrongSha1 = Paths.get("target", "test-classes", "testdata", "wrongSha1.csv");

    @After
    public void shutdown() {
        csvFile.toFile().delete();
    }
    
    @Test
    public void shouldBeExitCode0() {
        // given
        exit.expectSystemExitWithStatus(0);
        final String[] parameter = { "-d", theDirectory.toString() };

        // when
        Starter.main(parameter);
        
        // then
        assertThat(csvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeExitCode0WithBlacklist() {
        // given
        exit.expectSystemExitWithStatus(0);
        final String[] parameter = { "-d", theDirectory.toString(), "-b", Paths.get("target", "test-classes", "blacklist.txt").toString() };

        // when
        Starter.main(parameter);
        
        // then
        assertThat(csvFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeExitCode1() {
        // given
        exit.expectSystemExitWithStatus(1);
        final String[] parameter = { };

        // when
        Starter.main(parameter);
    }
    
    @Test
    public void shouldBeExitCode2() {
        // given
        exit.expectSystemExitWithStatus(2);
        final String[] parameter = { "-d", "wrong-dir" };

        // when
        Starter.main(parameter);
    }
    
    @Test
    public void shouldBeValidate() {
        // given
        exit.expectSystemExitWithStatus(0);
        final String[] parameter = { "-v", toValidate.toString() };
        
        // when
        Starter.main(parameter);
    }

    @Test
    public void shouldBeNotValidate() {
        // given
        exit.expectSystemExitWithStatus(3);
        final String[] parameter = { "-v", wrongSha1.toString() };
        
        // when
        Starter.main(parameter);
    }
}
