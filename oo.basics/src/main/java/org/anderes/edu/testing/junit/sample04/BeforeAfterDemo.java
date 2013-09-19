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
 * Einfache Demonstration der JUnit-Annotation
 * {@code @Before} & {@code @After}
 * 
 * @author Ren� Anderes
 */
public class BeforeAfterDemo {

    /** Tempor�res File f�r den oder die Test's */
    private File tempFile;

    /**
     * Wird vor jeder Testmethode ausgef�hrt.
     * @throws Exception Wenn ein Fehler auftritt 
     * 		beim erstellen des temor�ren Files
     */
    @Before
    public void setUp() throws Exception {
	tempFile = File.createTempFile("test", "txt");
	assertTrue(tempFile.canWrite());
    }

    /**
     * Wird nach jeder Testmethode ausgef�hrt.
     * @throws Exception Wenn ein fehler beim l�schen
     * 		des Files auftritt
     */
    @After
    public void tearDown() throws Exception {
	tempFile.delete();
    }

    /**
     * Die eigentliche Testmethode.<br>
     * Diese schreibt einen Text in das
     * File und �berpr�ft die L�nge des Files.
     */
    @Test
    public void writeFile() {
	writeFile("Software-Engineering");
	assertEquals(20, tempFile.length());
    }
 
    /**
     * Diese Methode schreibt den �bergebenen Text
     * in das tempor�re File.
     * @param message Text
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
