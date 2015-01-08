package org.anderes.edu.xml.jaxb;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.anderes.edu.xml.jaxb.generated.Contact;
import org.anderes.edu.xml.jaxb.generated.Contactlist;
import org.anderes.edu.xml.jaxb.generated.ObjectFactory;
import org.anderes.edu.xml.jaxb.generated.Phone;
import org.anderes.edu.xml.jaxb.generated.Phonetype;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Beispiel in dem ein XML File mittels JAXB
 * serialisiert und deserialisiert wird. Alle XML-Files
 * werden zusätzlich gegen ein Schema validiert.
 * 
 * @author René Anderes
 *
 */
public class MarshallerUnmarshallerValidateJaxbTest {

    final File schemaFile = new File("target/classes/org/anderes/edu/xml/introduction/contacts.xsd");
    private Unmarshaller jaxbUnmarshaller;
    private InputStream xmlInputStream;
    
    @Before
    public void setup() throws JAXBException {
        xmlInputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
        assertThat(xmlInputStream, is(notNullValue()));
        final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    }
    
	@Test
	public void marshallerWithSchema() {
	    
	    assertThat(schemaFile.exists(), is(true));
		
		final Contactlist contactlist = createData();
		
        try {
            final SchemaFactory schemaFactrory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = schemaFactrory.newSchema(schemaFile);
 
            final File file = File.createTempFile("ContactList", "xml");
            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // Schema für die Validierung
            jaxbMarshaller.setSchema(schema);
            
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // writing to a file
            jaxbMarshaller.marshal(contactlist, file);
            // writing to console
            jaxbMarshaller.marshal(contactlist, System.out);
            
            assertThat(file.exists(), is(true));
            assertThat(file.length() > 200L, is(true));
 
        } catch (JAXBException | IOException | SAXException e) {
            fail(e.getMessage());
        }
 
	}
	
	@Test
	public void unmarshallerWithSchema() {
		try {
		    final SchemaFactory schemaFactrory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = schemaFactrory.newSchema(schemaFile);
                
            // Schema für die Validierung
            jaxbUnmarshaller.setSchema(schema);
            
            final Contactlist contactlist = (Contactlist) jaxbUnmarshaller.unmarshal(xmlInputStream);
            
            assertThat(contactlist, is(notNullValue()));
            assertThat(contactlist.getContact().size(), is(2));
 
          } catch (JAXBException | SAXException e) {
        	  fail(e.getMessage());
          }
	}
	
	@Test(expected = UnmarshalException.class)
    public void shouldBeNotValidXml() throws JAXBException, SAXException {
            final SchemaFactory schemaFactrory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            final Schema schema = schemaFactrory.newSchema(schemaFile);

            // Schema für die Validierung
            jaxbUnmarshaller.setSchema(schema);
            
            xmlInputStream = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist_notvalid.xml");
            jaxbUnmarshaller.unmarshal(xmlInputStream);
    }
	
	private Contactlist createData() {
		final Contactlist contactlist = new Contactlist();
		final Contact contact = new Contact();
		contact.setAddress("Zürcherstrasse 1");
		contact.setMail("info@mail.com");
		contact.setName("Hans Oberhänsli");
		final Phone phone = new Phone();
		phone.setType(Phonetype.MOBILE);
		phone.setValue("+41 77 223 44 55");
		contact.getPhone().add(phone);
		contactlist.getContact().add(contact);
		return contactlist;
	}
}
