package org.anderes.edu.dojo.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class EncodingConverterTest {
    
    private final Charset targetEncoding;
    private final Charset sourceEncoding;
    private final String text;

    public EncodingConverterTest(final String sourceEncoding, final String targetEncoding, final String text) {
        this.sourceEncoding = Charset.forName(sourceEncoding);
        this.targetEncoding = Charset.forName(targetEncoding);
        this.text = text;
        System.out.println(String.format("Test mit Text '%s' und Source-Encoding '%s', Target-Encoding '%s'", 
                        this.text, this.sourceEncoding.name(), this.targetEncoding.name()));
    }
    
    @Test
    public void shouldBeConvert() throws IOException {
        
        // given
        final EncodingConverter converter = new EncodingConverter(sourceEncoding.name(), targetEncoding.name());
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ByteBuffer sourceBuffer = sourceEncoding.encode(text);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(sourceBuffer.array(), 0, sourceBuffer.limit());
        final ByteBuffer expectedBuffer = targetEncoding.encode(text);
        final int expectedLength = expectedBuffer.limit();
        ByteArrayOutputStream expectedOutputstream = new ByteArrayOutputStream(expectedLength);
        expectedOutputstream.write(expectedBuffer.array(), 0, expectedLength);

        // when
        converter.convert(inputStream, outputStream);
        
        // then
        assertThat(outputStream.size(), is(expectedLength));
        assertThat(outputStream.toByteArray(), is(expectedOutputstream.toByteArray()));
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Parameter.getParameter();
    }
    
    private enum Parameter {
        ENCODING_TEST1("ISO-8859-1", "UTF-8", "René"),
        ENCODING_TEST2("UTF-8", "ISO-8859-1", "René"),
        ENCODING_TEST3("ISO-8859-1", "ISO-8859-8", "René");
        
        private String sourceEncoding;
        private String targetEncoding;
        private String text;
        
        private Parameter(String sourceEncoding, String targetEncoding, String text) {
            this.sourceEncoding = sourceEncoding;
            this.targetEncoding = targetEncoding;
            this.text = text;
        }
        
        public static Collection<Object[]> getParameter() {
            Collection<Object[]> collection = new ArrayList<Object[]>(); 
            for (Parameter parameter : Parameter.values()) {
                collection.add(new Object[] { parameter.sourceEncoding, parameter.targetEncoding, parameter.text});
            }
            return collection;
        }
    }
}
