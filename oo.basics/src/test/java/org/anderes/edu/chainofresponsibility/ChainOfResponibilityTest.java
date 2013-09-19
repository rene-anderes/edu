package org.anderes.edu.chainofresponsibility;

import org.anderes.edu.chainofresponsibility.ConvertJsonToCsv;
import org.anderes.edu.chainofresponsibility.ConvertXmlToCsv;
import org.anderes.edu.chainofresponsibility.StructuredFile;
import org.anderes.edu.chainofresponsibility.ToCsvConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test f�r die Zust�ndigskeitkette
 *
 * @author Ren� Anderes
 */
public class ChainOfResponibilityTest {

    private StructuredFile structuredFileXml = new StructuredFile("test.xml");
    private StructuredFile structuredFileJson = new StructuredFile("test.json");
    private ToCsvConverter converter;

    /**
     * Zust�ndigkeitskette aufbauen
     * @throws Exception Fehlerfall
     */
    @Before
    public void setUp() throws Exception {
        converter = new ConvertXmlToCsv();
        ConvertJsonToCsv jsonConverter = new ConvertJsonToCsv();
        ((ConvertXmlToCsv)converter).setSuccessor(jsonConverter);
    }

    /**
     * Testet die Zust�ndigkeitskette
     */
    @Test
    public void chain() {
        assertEquals("json to csv", converter.convert(structuredFileJson));
        assertEquals("xml to csv", converter.convert(structuredFileXml));
    }
}
