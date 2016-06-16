package org.anderes.edu.xml.jaxb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.Test;

public class MoxyMarshallerUnmarshallerForJson {

    @Test
    public void marshaller() {
        
        final Contactlist contactlist = createData();
        try {
            final File file = File.createTempFile("ContactList", ".json");
            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            
            // Create the EclipseLink JAXB (MOXy) Marshaller
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
            
            // writing to a file
            jaxbMarshaller.marshal(contactlist, file);
            // writing to console
            jaxbMarshaller.marshal(contactlist, System.out);
            
            assertThat(file.exists(), is(true));
            assertThat(file.length() > 180L, is(true));
 
        } catch (JAXBException | IOException e) {
            fail(e.getMessage());
        } 
 
    }
    
    @Test
    public void unmarshaller() {
        try (InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/jaxb/contactlist.json")) {
            
            assertThat(is, is(notNullValue()));

            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            
            // Create the EclipseLink JAXB (MOXy) Unmarshaller
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbUnmarshaller.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
            
            final Contactlist contactlist = (Contactlist) jaxbUnmarshaller.unmarshal(is);
            
            assertThat(contactlist, is(notNullValue()));
            assertThat(contactlist.getContact().size(), is(2));
            
          } catch (JAXBException | IOException e) {
              fail(e.getMessage());
          }
    }
    
    private Contactlist createData() {
        final ObjectFactory factory = new ObjectFactory();
        final Contactlist contactlist = factory.createContactlist();
        final Contact contact1 = createContact1(factory);
        final Contact contact2 = createContact2(factory);
        contactlist.getContact().add(contact1);
        contactlist.getContact().add(contact2);
        return contactlist;
    }

    private Contact createContact1(final ObjectFactory factory) {
        final Contact contact = factory.createContact();
        contact.setAddress("Zürcherstrasse 1, 9000 St.Gallen");
        contact.setMail("info@mail.com");
        contact.setName("Hans Oberhänsli");
        final Phone phone = factory.createPhone();
        phone.setType(Phonetype.MOBILE);
        phone.setValue("+41 77 223 44 55");
        contact.getPhone().add(phone);
        return contact;
    }
    private Contact createContact2(final ObjectFactory factory) {
        final Contact contact = factory.createContact();
        contact.setAddress("Langstrasse 1, 9000 St.Gallen");
        contact.setMail("info@mail.com");
        contact.setName("Bill Gates");
        final Phone phone1 = factory.createPhone();
        phone1.setType(Phonetype.MOBILE);
        phone1.setValue("+41 77 211 32 55");
        final Phone phone2 = factory.createPhone();
        phone2.setType(Phonetype.MOBILE);
        phone2.setValue("+41 79 211 34 55");
        contact.getPhone().add(phone1);
        contact.getPhone().add(phone2);
        return contact;
    }
}
