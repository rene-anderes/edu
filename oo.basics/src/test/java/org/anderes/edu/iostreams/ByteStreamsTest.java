package org.anderes.edu.iostreams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Ignore;
import org.junit.Test;

public class ByteStreamsTest {
    
    @Test @Ignore
    public void readFromConsole() throws IOException {
        ByteArrayOutputStream userInput = new ByteArrayOutputStream();
        while(true) {
            int value = System.in.read();
            if (value == 13 || value == 10) {
                break;
            }
            userInput.write(value);
        }
        userInput.close();
        
        assertArrayEquals("Hello World".getBytes(), userInput.toByteArray());
        
    }

    @Test
    public void readImageFile() throws IOException {
        try (FileInputStream inputStream = new FileInputStream("target/test-classes/java_logo.png")) {
            assertThat(inputStream.available(), is(3866));
        }
    }
    
    @Test
    public void readResourcesImage() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/java_logo.png")) {
            assertThat(inputStream.available(), is(3866));
        }
    }
    
    @Test
    public void readImageWriteToZip() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/java_logo.png")) {
            FileOutputStream outputStream = new FileOutputStream("target/test-classes/java_logo.zip");
            try(ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
                byte[] imageBytes = new byte[inputStream.available()];
                inputStream.read(imageBytes);
                zipOutputStream.putNextEntry(new ZipEntry("java_logo.png"));
                zipOutputStream.write(imageBytes);
                zipOutputStream.closeEntry();
            }
        }
    }
    
    @Test
    public void objectStreams() throws IOException, ClassNotFoundException {
        FileOutputStream outputStream = new FileOutputStream("target/test-classes/info.obj");
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject("ObjectStreamTest");
        }
        FileInputStream inputStream = new FileInputStream("target/test-classes/info.obj");
        assertThat(inputStream.available(), is(23));
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            String value = (String)objectInputStream.readObject();
            assertThat(value, is("ObjectStreamTest"));
        }
    }
}
