package org.anderes.edu.xml.jaxb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import static javax.xml.xpath.XPathConstants.*;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.anderes.edu.xml.jaxb.generated.Contact;
import org.anderes.edu.xml.jaxb.generated.ObjectFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * In diesem Beispiel werden nur Teile des XML-Files via JAXB
 * deserialisiert. Dazu wird ein Node bzw. eine Node-Liste aus
 * dem DOM erstellt.
 * 
 * @author René Anderes
 *
 */
public class PartialFromDOMwithXPathTest {
    
    private InputStream inputStream;
    private Unmarshaller jaxbUnmarshaller;
    
    @Before
    public void setup() throws Exception {
        inputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
        assertThat(inputStream, is(notNullValue()));
        final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }

    @Test
    public void shouldBeDeserializeNode() throws Exception {
        
        // es wird das Contact-Element gesucht, welches im Name-Element 'Leonardo Da Vinci' enthält
        final Node node = createNodeByXPathExpression("//contact[./name='Leonardo Da Vinci']");
        
        // es werden nur die entsprechenden Jaxb-Elemente der Node-Liste deserialisiert
        final JAXBElement<Contact> jaxbElement = jaxbUnmarshaller.unmarshal(node, Contact.class);
        final Contact contact = jaxbElement.getValue();

        assertThat(contact, is(notNullValue()));
        assertThat(contact.getName(), is(notNullValue()));
        assertThat(contact.getName(), is("Leonardo Da Vinci"));
    }

    @Test
    public void shouldBeDeserializeNodeList() throws Exception {
        
        // Node-Liste via XPath-Expression erzeugen
        final NodeList nodelist = createNodeListByXPathExpression("//contact");

        for (int index = 0; index < nodelist.getLength(); index++) {
            
            // es werden nur die entsprechenden Jaxb-Elemente der Node-Liste deserialisiert
            final JAXBElement<Contact> jaxbElement = jaxbUnmarshaller.unmarshal(nodelist.item(index), Contact.class);
            final Contact contact = jaxbElement.getValue();

            assertThat(contact, is(notNullValue()));
            assertThat(contact.getName(), is(notNullValue()));
        }

    }

    private Node createNodeByXPathExpression(String expression) throws Exception {
        final Document document = createDOM();
        final XPath xpath = XPathFactory.newInstance().newXPath();
        return (Node) xpath.evaluate(expression, document, NODE);
    }
    
    private NodeList createNodeListByXPathExpression(final String expression) throws Exception {
        final Document document = createDOM();
        final XPath xpath = XPathFactory.newInstance().newXPath();
        return (NodeList) xpath.evaluate(expression, document, NODESET);
    }

    private Document createDOM() throws ParserConfigurationException, SAXException, IOException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(inputStream);
        return document;
    }

    
}
