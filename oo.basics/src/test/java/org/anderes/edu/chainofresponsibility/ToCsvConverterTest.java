package org.anderes.edu.chainofresponsibility;

import org.anderes.edu.chainofresponsibility.StructuredFile;
import org.anderes.edu.chainofresponsibility.ToCsvConverter;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

/**
 * Testklasse f�r toCsvConverter
 *
 * @author Ren� Anderes
 */
public class ToCsvConverterTest {

    /**
     * Test mittels {@code EasyMock.createMock()}
     */
    @Test
    public void convertMock() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt erzeugen
        ToCsvConverter mockConverterXml = createMock(ToCsvConverter.class);
        StructuredFile structuredFile = new StructuredFile("test.xml");
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Verhalten aufzeichnen
        expect(mockConverterXml.convert(structuredFile)).andReturn(structuredFile.getContent());
        replay(mockConverterXml);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Test's durchf�hren
        String str = mockConverterXml.convert(structuredFile);
        assertNotNull(str);
        assertEquals("content", str);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock-Objekt verifizieren
        verify(mockConverterXml);
    }
}
