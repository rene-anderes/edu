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
import org.anderes.edu.xml.jaxb.generated.Phone;
import org.anderes.edu.xml.jaxb.generated.Phonetype;
import org.junit.Test;

public class ContactJaxbTest {

	@Test
	public void marshaller() {
		
		final Contactlist contactlist = createData();
        try {
 
            final File file = File.createTempFile("ContactList", "xml");
            final JAXBContext jaxbContext = JAXBContext.newInstance(Contactlist.class);
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
			
			// XML-File
			final InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/introduction/contactlist.xml");
			assertThat(is, is(notNullValue()));

            final JAXBContext jaxbContext = JAXBContext.newInstance(Contactlist.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            final Contactlist contactlist = (Contactlist) jaxbUnmarshaller.unmarshal(is);
            
            assertThat(contactlist, is(notNullValue()));
            assertThat(contactlist.getContact().size(), is(2));
 
          } catch (JAXBException e) {
        	  fail(e.getMessage());
          }
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
