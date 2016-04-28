package org.anderes.edu.iostreams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import org.junit.Test;

public class CharStreams {

    private final String text = "Daten sind die Vermögenswerte des 21ten Jahrhunderts.";
    
    @Test
    public void writeAndReadFile() throws IOException {
        try (FileWriter writer = new FileWriter("target/test-classes/text.txt")) {
            writer.write(text);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("target/test-classes/text.txt"))) {
            final String msg = reader.lines().collect(Collectors.joining());
            assertThat(msg, is(text));
        }
    }
    
    @Test
    public void readFile() throws IOException {
        InputStream inputStream = new FileInputStream("target/test-classes/ISO-8859-1 Text.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")))) {
            final String msg = reader.lines().collect(Collectors.joining());
            assertThat(msg, is("Fix, Schwyz! quäkt Jürgen blöd vom Paß."));
        }
    }
}
