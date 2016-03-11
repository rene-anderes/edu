package org.anderes.edu.dojo.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class EncodingConverter {

    private Charset sourceCharset;
    private Charset targetCharset;

    public EncodingConverter(String sourceCharset, String targetCharset) {
        this.sourceCharset = Charset.forName(sourceCharset);
        this.targetCharset = Charset.forName(targetCharset);
    }

    public void convert(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        
        final InputStreamReader reader = new InputStreamReader(inputStream, sourceCharset);
        final OutputStreamWriter writer = new OutputStreamWriter(outputStream, targetCharset);
        while (true) {
            int c = reader.read();
            if (c == -1) {
                break;
            }
            writer.write(c);
        }
        writer.flush();
    }

}
