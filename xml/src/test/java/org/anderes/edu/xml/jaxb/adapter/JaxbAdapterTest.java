package org.anderes.edu.xml.jaxb.adapter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.anderes.edu.xml.jaxb.adapter.generated.ObjectFactory;
import org.anderes.edu.xml.jaxb.adapter.generated.Person;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Beispiel in dem ein Adapter eingesetzt wird um das Datums-Objekt
 * zu konvertieren (beim Serialisieren und deserialisieren)
 * 
 * @author René Anderes
 *
 */
public class JaxbAdapterTest {

    private Unmarshaller jaxbUnmarshaller;
    private Marshaller jaxbMarshaller;
    private static JAXBContext jaxbContext;
    
    @BeforeClass
    public static void onetime() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            fail(e.getMessage());
        }
    }

    @Before
    public void setup() {
        assertThat(jaxbContext, is(notNullValue()));
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            fail(e.getMessage());
        }
    }
       
    @Test
    public void marshaller() throws Exception {
        
        // given
        final File file = File.createTempFile("person", "xml");
        final Person person = createSamplePersonData();
        
        // when
        jaxbMarshaller.marshal(person, file);
        jaxbMarshaller.marshal(person, System.out);

        // then
        assertThat(file.exists(), is(true));
        assertThat(file.length() > 100L, is(true));

    }

    @Test
    public void unmarshaller() throws Exception {

        // given
        final InputStream is = getClass().getResourceAsStream("/org/anderes/edu/xml/jaxb/adapter/person.xml");
        assertThat(is, is(notNullValue()));

        // when
        final Person person = (Person) jaxbUnmarshaller.unmarshal(is);

        // than
        assertThat(person, is(notNullValue()));
        assertThat(person.getBirthday(), is(february(22, 1967)));
        
        is.close();
    }

    private LocalDate february(int day, int year) {
        return LocalDate.of(year, Month.FEBRUARY, day);
    }

    private Person createSamplePersonData() {
        final ObjectFactory factory = new ObjectFactory();
        final Person person = factory.createPerson();
        person.setName("Claude Fischer");
        person.setBirthday(february(29, 2004));
        return person;
    }
}
