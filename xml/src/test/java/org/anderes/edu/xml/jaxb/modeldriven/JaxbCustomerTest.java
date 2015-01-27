package org.anderes.edu.xml.jaxb.modeldriven;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.anderes.edu.xml.jaxb.adapter.generated.ObjectFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JaxbCustomerTest {

    private File file;

    @Before
    public void setUp() throws Exception {
        file = File.createTempFile("person", "xml");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void marshaller() throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class, Order.class);
        jaxbContext.createMarshaller().marshal(createSampleCustomer(), file);
        
        assertThat(file.exists(), is(true));
        assertThat(file.length() > 100L, is(true));
    }

    private Customer createSampleCustomer() {
        final Customer customer = new Customer("Hans Pfister");
        
        return customer;
    }
}
