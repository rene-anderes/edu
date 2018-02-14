package org.anderes.edu.iostreams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Zeichenorientierte Verarbeitung mittels {@code Reader} und {@code Writer}
 * 
 * @author René Anderes
 *
 */
public class CharStreamsTest {

    private final String text = "Daten sind die Vermögenswerte des 21ten Jahrhunderts.";
    
    /**
     * Schreibe den obigen Text ({@code text}) mittels einem geeigneten Writer in eine
     * Datei "target/test-classes/text.txt" und überprüfe mittels Asserts ob das File existiert
     * und lese die Datei mittels geeignetem {@code Reader} wieder ein und überprüfe
     * ob der Inhalt korrekt ist.
     * 
     * @throws IOException 
     */
    @Test
    public void writeAndReadFile() throws IOException {
        final File txtFile = Paths.get("target", "test-classes", "text.txt").toFile();
        try (FileWriter writer = new FileWriter(txtFile)) {
            writer.write(text);
        }
        assertThat(txtFile.exists(), is(true));
        
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            final String msg = reader.lines().collect(Collectors.joining());
            assertThat(msg, is(text));
        }
    }
    
    /**
     * Lese das File "ISO-8859-1 Text.txt" aus dem Pfad "target/text-classes/" ein
     * und überprüfe den Inhalt. Korrekt ist: "Fix, Schwyz! quäkt Jürgen blöd vom Paß."
     * Beachte, dass das File im Encoding ISO-8859-1 vorliegt.
     * 
     * @throws IOException
     */
    @Test
    public void readFile() throws IOException {
        final File txtFile = Paths.get("target", "test-classes", "ISO-8859-1 Text.txt").toFile();
        try (InputStream inputStream = new FileInputStream(txtFile);
                        Reader inputReader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
                        BufferedReader reader = new BufferedReader(inputReader)) {
            final String msg = reader.lines().collect(Collectors.joining());
            assertThat(msg, is("Fix, Schwyz! quäkt Jürgen blöd vom Paß."));
        }
    }
    
    @Test
    public void readFileSecondVersion() throws IOException {
        final Path txtFile = Paths.get("target", "test-classes", "ISO-8859-1 Text.txt");
        try (BufferedReader reader = Files.newBufferedReader(txtFile, Charset.forName("ISO-8859-1"))) {
            final String msg = reader.lines().collect(Collectors.joining());
            assertThat(msg, is("Fix, Schwyz! quäkt Jürgen blöd vom Paß."));
        }
    }
}
