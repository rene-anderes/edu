package org.anderes.edu.xml.jaxb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.anderes.edu.xml.jaxb.adapter.generated.ObjectFactory;
import org.anderes.edu.xml.jaxb.adapter.generated.Person;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Beispiel in dem ein XML File mittels JAXB
 * serialisiert und deserialisiert wird
 * 
 * @author René Anderes
 *
 */
public class MarshallerUnmarshallerJaxbAdapterTest {

	@Test
	@Ignore
	public void marshaller() {
		
	
        try {
 
            final File file = File.createTempFile("person", "xml");
            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // writing to a file
//            jaxbMarshaller.marshal(contactlist, file);
//            // writing to console
//            jaxbMarshaller.marshal(contactlist, System.out);
//            
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
			final InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/jaxb/person.xml");
			assertThat(is, is(notNullValue()));

            final JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            final Person person = (Person) jaxbUnmarshaller.unmarshal(is);
            
            assertThat(person, is(notNullValue()));
            assertThat(person.getBirthday(), is(february(22, 1967)));
 
          } catch (JAXBException e) {
              e.printStackTrace();
        	  fail(e.getMessage());
          }
	}
	
	private LocalDate february(int day, int year) {
	    return LocalDate.of(year, Month.FEBRUARY, day);
	}
}
