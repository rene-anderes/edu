package org.anderes.edu.xml.saxdom;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DomReader {
    private final static Logger logger = Logger.getLogger(DomReader.class.getName());
    
	private DomReader() {
	}

	public static Document parseFile(final String xmlFile, final String xsdFile) throws Exception  {
	    Validate.notBlank(xmlFile);
        Validate.notBlank(xsdFile);
        
	    validateFile(xmlFile, xsdFile);
	    
		return parseFile(xmlFile);
	}
	
	private static Document parseFile(final String xmlFile) throws Exception  {
	    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder  = factory.newDocumentBuilder();
        final InputSource inputSource = new InputSource(DomReader.class.getResourceAsStream(xmlFile));
        Document document = null;
        try {
            document = builder.parse(inputSource);
        } finally {
            inputSource.getByteStream().close();
        }
        return document;
	}
	
	private static void validateFile(final String xmlFile, final String xsdFile) throws Exception {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            final Schema schema = factory.newSchema(DomReader.class.getResource(xsdFile));
            final Validator validator = schema.newValidator();
            final Source source = new StreamSource(DomReader.class.getResourceAsStream(xmlFile));
            validator.validate(source);
            logger.info(xmlFile + " is valid.");
        } catch (SAXException | IOException e) {
            String log = String.format("XML-File '%s' is not valid, because %s",  xmlFile, e.getMessage());
            logger.warning(log);
            throw e;
        }
    }
}
