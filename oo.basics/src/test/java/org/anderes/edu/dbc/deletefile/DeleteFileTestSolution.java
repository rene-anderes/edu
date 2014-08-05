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
public class DeleteFileTestSolution {

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
        assertFalse(tempFile.exists());
    }

    @Test(expected = NullPointerException.class)
    public void shoulBeFailed() {
        deleteTempFile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shoulBeFailedWrongFile() throws IOException {
        File txtFile = File.createTempFile("test", ".txt");
        deleteTempFile(txtFile.getPath());
    }

    @Test
    public void cannotDelete() {
        assertFalse(deleteTempFile("Test_Not_Exists.tmp"));
    }

    /**
     * Löschen von temporärer Datei.
     * 
     * @param file
     *            Ein File mit der Endung tmp (Format: *.tmp)
     * @return {@code true}, wenn das File gelöscht werden konnte.
     */
    public boolean deleteTempFile(final String file) {
        if (file == null) {
            throw new NullPointerException("Der Parameter ist null");
        } else {
            if (!file.matches("\\p{Graph}+[.]tmp")) {
                String message = String.format("Es handelt sich nicht um ein Temp-File '%1$s'", file);
                throw new IllegalArgumentException(message);
            }
        }
        File delFile = new File(file);
        return delFile.delete();
    }

}
