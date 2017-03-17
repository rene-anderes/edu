package org.anderes.edu.security;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class FileSecurityTest {

    @Test
    public void shouldBeFileEncryption() throws Exception {
        // given
        final Path outputFile = Paths.get("target", "test-classes", "LoremIpsum.sec");
        final Path inputFile = Paths.get("target", "test-classes", "LoremIpsum.txt");
        final String password = "loremipsum";
        // when
        final SecretObject secretObject = FileSecurity.encrypt(password , inputFile, outputFile);
        // then
        assertThat(secretObject, is(not(nullValue())));
        assertThat(secretObject.getInitializationVector().length, is(16));
        assertThat(secretObject.getSalt().length, is(8));
        assertThat(outputFile.toFile().exists(), is(true));
    }
    
    @Test
    public void shouldBeFileDecryption() throws Exception {
        // given
        final Path securityFile = Paths.get("target", "test-classes", "LoremIpsum.sec");
        final Path inputFile = Paths.get("target", "test-classes", "LoremIpsum.txt");
        final String password = "loremipsum";
        final SecretObject secretObject = FileSecurity.encrypt(password , inputFile, securityFile);
        // when
        final String text = FileSecurity.decrypt(password, secretObject, securityFile);
        // then
        assertThat(text, is(expectedValue(inputFile)));
    }
    
    private String expectedValue(Path file) throws IOException {
        return Files.lines(file, StandardCharsets.UTF_8).findAny().orElseThrow(() -> new IOException());
    }
}
