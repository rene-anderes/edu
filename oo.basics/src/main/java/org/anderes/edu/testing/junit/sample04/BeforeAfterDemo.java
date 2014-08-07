package org.anderes.edu.testing.junit.sample04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Einfache Demonstration der JUnit-Annotation {@code @Before} & {@code @After}
 * 
 * @author René Anderes
 */
public class BeforeAfterDemo {

    /** Temporäres File für den oder die Test's */
    private File tempFile;

    /**
     * Wird vor jeder Testmethode ausgeführt.
     * 
     * @throws Exception
     *             Wenn ein Fehler auftritt beim erstellen des temorären Files
     */
    @Before
    public void setUp() throws Exception {
        tempFile = File.createTempFile("test", "txt");
        assertTrue(tempFile.canWrite());
    }

    /**
     * Wird nach jeder Testmethode ausgeführt.
     * 
     * @throws Exception
     *             Wenn ein fehler beim löschen des Files auftritt
     */
    @After
    public void tearDown() throws Exception {
        tempFile.delete();
    }

    /**
     * Die eigentliche Testmethode.<br>
     * Diese schreibt einen Text in das File und überprüft die Länge des Files.
     */
    @Test
    public void writeFile() {
        writeFile("Software-Engineering");
        assertEquals(20, tempFile.length());
    }

    /**
     * Diese Methode schreibt den übergebenen Text in das temporäre File.
     * 
     * @param message
     *            Text
     */
    private void writeFile(final String message) {
        try {
            Writer writer = new FileWriter(tempFile);
            writer.write(message);
            writer.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
