package org.anderes.edu.xml.xpath;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SvgTest {

    @Test
    public void shouldBeCheckSvgFile() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
        
        final DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(false); 
        final DocumentBuilder builder = domFactory.newDocumentBuilder();
        
        try(InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/xpath/anderes.svg")) {
            assertThat(is, is(not(nullValue())));
            
            Document doc = builder.parse(is);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            
            XPathExpression expr = xpath.compile("//g/title");
            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            
            assertThat(result.getLength(), is(2));
        }
    }
    
}
