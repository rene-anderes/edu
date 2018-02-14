package org.anderes.edu.iostreams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

/**
 * Byte-Orientiert Verarbeitung mittels {@code InputStream} und {@code OutputStream}
 * 
 * @author René Anderes
 *
 */
public class ByteStreamsTest {

    /**
     * Lese das File "java_logo.png" aus dem Pfad "target/test-classes" aus
     * und überprüfe ob 3866 Bytes gelesen wurden.
     * 
     * @throws IOException
     */
    @Test
    public void readImageFile() throws IOException {
        final File logoFile = Paths.get("target", "test-classes", "java_logo.png").toFile();
        try (FileInputStream inputStream = new FileInputStream(logoFile)) {
            assertThat(inputStream.available(), is(3866));
        }
    }

    /**
     * Lese das File "java_logo.png" aus dem <strong>Klassenpfad</strong> aus
     * und überprüfe ob 3866 Bytes gelesen wurden.
     * 
     * @throws IOException
     */
    @Test
    public void readResourcesImage() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/java_logo.png")) {
            assertThat(inputStream.available(), is(3866));
        }
    }

    /**
     * Erstelle ein ZIP-File mit dem Namen "myTest.zip" im Pfad "target/test-classes/".
     * Dieses Zip soll aus zwei Einträgen bestehen: Dem File "java_logo.png" und dem File "LoremIpsum.txt".
     * Überprüfe die Existenz des Files.
     * 
     * @throws IOException
     */
    @Test
    public void readImageAndTextWriteToZip() throws IOException {
        final File zipFile = Paths.get("target", "test-classes", "myTest.zip").toFile();
        try (InputStream logoInputStream = getClass().getResourceAsStream("/java_logo.png");
                        InputStream txtInputStream = getClass().getResourceAsStream("/LoremIpsum.txt");
                        FileOutputStream outputStream = new FileOutputStream(zipFile);
                        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            
            byte[] imageBytes = new byte[logoInputStream.available()];
            logoInputStream.read(imageBytes);
            zipOutputStream.putNextEntry(new ZipEntry("java_logo.png"));
            zipOutputStream.write(imageBytes);
            
            byte[] txtBytes = new byte[txtInputStream.available()];
            txtInputStream.read(txtBytes);
            zipOutputStream.putNextEntry(new ZipEntry("LoremIpsum.txt"));
            zipOutputStream.write(txtBytes);
            zipOutputStream.closeEntry();
        }

        assertThat(zipFile.exists(), is(true));
        assertThat(zipFile.getAbsoluteFile().length(), is(4320L));
    }

    /**
     * Serialisiere mittels einem {@code ObjectOutputStream} ein String-Objekt in
     * ein File "info.obj" in den Pfad "target/test-classes/".
     * Lese das File "info.obj" mittels einem {@code InputStream} wieder ein
     * und überprüfe den Inhalt. 
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void objectStreams() throws IOException, ClassNotFoundException {
        final File infoObjFile = Paths.get("target", "test-classes", "info.obj").toFile();
        final FileOutputStream outputStream = new FileOutputStream(infoObjFile);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject("ObjectStreamTest");
        }   // der äussere Stream schliesst auch den inneren Stream
        
        final FileInputStream inputStream = new FileInputStream(infoObjFile);
        assertThat(inputStream.available(), is(23));
        
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            String value = (String) objectInputStream.readObject();
            assertThat(value, is("ObjectStreamTest"));
        }   // der äussere Stream schliesst auch den inneren Stream
    }
   
}
