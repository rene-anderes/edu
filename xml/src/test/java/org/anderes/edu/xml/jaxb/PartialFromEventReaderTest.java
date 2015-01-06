package org.anderes.edu.xml.jaxb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.anderes.edu.xml.jaxb.generated.Contact;
import org.anderes.edu.xml.jaxb.generated.ObjectFactory;
import org.junit.Before;
import org.junit.Test;

public class PartialFromEventReaderTest {

    private InputStream inputStream;
    private Unmarshaller jaxbUnmarshaller;

    @Before
    public void setup() throws Exception {
        inputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
        assertThat(inputStream, is(notNullValue()));
        jaxbUnmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
    }

    @Test
    public void shouldBeReadPartialFromFile() throws Exception {

        final QName searchElement = new QName("contact");
        final XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(inputStream, "UTF-8");
        XMLEvent xmlEvent;
        while ((xmlEvent = eventReader.peek()) != null) {
            if (xmlEvent.isStartElement()) {
                if (((StartElement) xmlEvent).getName().equals(searchElement)) {
                    final Contact contact = jaxbUnmarshaller.unmarshal(eventReader, Contact.class).getValue();
                    assertThat(contact, is(notNullValue()));
                    assertThat(contact.getName(), is(notNullValue()));
                } else {
                    eventReader.next();
                }
            } else {
                eventReader.next();
            }
        }
    }
}
