package org.anderes.edu.dbc.deletefile;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testfile für Design by Contract
 * 
 * @author René Anderes
 */
public class DeleteFileTest {

    private File tempFile;

    @Before
    public void setupBefore() {
        try {
            tempFile = File.createTempFile("test", ".tmp");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void shutdown() {
        if (tempFile.exists()) {
            assertTrue(tempFile.delete());
        }
    }

    @Test
    public void shouldBeDeleteTempFile() {
        assertTrue(deleteTempFile(tempFile.getPath()));
    }

    @Test
    public void cannotDeleteTempFile() {
        assertFalse(deleteTempFile("File_Not_Exists.tmp"));
    }

    /**
     * Löschen von temporärer Datei.
     * 
     * @param file
     *            Ein File mit der Endung tmp (Format: *.tmp)
     * @return {@code true}, wenn das File gelöscht werden konnte.
     */
    public boolean deleteTempFile(final String file) {
        File delFile = new File(file);
        return delFile.delete();
    }

}
