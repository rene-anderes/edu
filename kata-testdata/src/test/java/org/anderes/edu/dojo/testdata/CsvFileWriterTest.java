package org.anderes.edu.dojo.testdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static java.nio.charset.StandardCharsets.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CsvFileWriterTest {

    private List<List<?>> testdata;
    private ByteArrayOutputStream expectedOutput;
    private Path path;
    
    @Before
    public void setup() throws Exception {
        testdata = createTestdata();
        expectedOutput = createExpectedOutput();
        path = File.createTempFile("csvTable", "csv").toPath();
    }
    
    @After
    public void tearDown() {
        File tempFile = path.toFile();
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    private List<List<?>> createTestdata() {
        List<List<?>> data = new ArrayList<List<?>>(5);
        data.add(Arrays.asList("Hans", "Muster", Integer.valueOf(200)));
        data.add(Arrays.asList("Maya", "Muster", Integer.valueOf(300)));
        data.add(Arrays.asList("Rita", "Muster", Integer.valueOf(400)));
        data.add(Arrays.asList("Marcel", "Muster", Integer.valueOf(500)));
        data.add(Arrays.asList("Sabine", "Muster", Integer.valueOf(600)));
        return data;
    }
    
    @Test
    public void ShouldBeWriteToOutputStream() {
        
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            CsvFileWriter.write(testdata).toOutputStream(outputStream);
        
            assertThat(outputStream.toString(), is(expectedOutput.toString()));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void shpuldBeWriteToFile() throws IOException {

        CsvFileWriter.write(testdata).toFile(path);
        
        final File expectedFile = path.toFile();
        assertThat(expectedFile.exists(), is(true));
        assertThat("Filegrösse stimmt nicht", expectedFile.length(), is(107L));
    }
    
    private ByteArrayOutputStream createExpectedOutput() throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(107)) {
            String row1 = "\"Hans\",\"Muster\",200" + "\r\n";
            outputStream.write(row1.getBytes());
            String row2 = "\"Maya\",\"Muster\",300" + "\r\n";
            outputStream.write(row2.getBytes());
            String row3 = "\"Rita\",\"Muster\",400" + "\r\n";
            outputStream.write(row3.getBytes());
            String row4 = "\"Marcel\",\"Muster\",500" + "\r\n";
            outputStream.write(row4.getBytes());
            String row5 = "\"Sabine\",\"Muster\",600";
            outputStream.write(row5.getBytes(UTF_8));
            return outputStream;
        }
    }
}
