package org.anderes.edu.xml.jaxb;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.anderes.edu.xml.jaxb.generated.Contact;
import org.anderes.edu.xml.jaxb.generated.Contactlist;
import org.anderes.edu.xml.jaxb.generated.ObjectFactory;
import org.anderes.edu.xml.jaxb.generated.Phone;
import org.anderes.edu.xml.jaxb.generated.Phonetype;
import org.junit.Test;

/**
 * Beispiel in dem ein XML File mittels JAXB
 * serialisiert und deserialisiert wird
 * 
 * @author René Anderes
 *
 */
public class MarshallerUnmarshallerJaxbTest {

	@Test
	public void marshaller() {
		
		final Contactlist contactlist = createData();
        try {
 
            final File file = File.createTempFile("ContactList", "xml");
            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // writing to a file
            jaxbMarshaller.marshal(contactlist, file);
            // writing to console
            jaxbMarshaller.marshal(contactlist, System.out);
            
            assertThat(file.exists(), is(true));
            assertThat(file.length() > 200L, is(true));
 
        } catch (JAXBException e) {
            fail(e.getMessage());
        } catch (IOException e) {
        	fail(e.getMessage());
        }
 
	}
	
	@Test
	public void unmarshaller() {
		try {
			
			// XML-File befindet sich im Klassenpfad
			final InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
			assertThat(is, is(notNullValue()));

            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            final Contactlist contactlist = (Contactlist) jaxbUnmarshaller.unmarshal(is);
            
            assertThat(contactlist, is(notNullValue()));
            assertThat(contactlist.getContact().size(), is(2));
 
          } catch (JAXBException e) {
        	  fail(e.getMessage());
          }
	}
	
	private Contactlist createData() {
	    final ObjectFactory factory = new ObjectFactory();
		final Contactlist contactlist = factory.createContactlist();
		final Contact contact = factory.createContact();
		contact.setAddress("Zürcherstrasse 1");
		contact.setMail("info@mail.com");
		contact.setName("Hans Oberhänsli");
		final Phone phone = factory.createPhone();
		phone.setType(Phonetype.MOBILE);
		phone.setValue("+41 77 223 44 55");
		contact.getPhone().add(phone);
		contactlist.getContact().add(contact);
		return contactlist;
	}
}
