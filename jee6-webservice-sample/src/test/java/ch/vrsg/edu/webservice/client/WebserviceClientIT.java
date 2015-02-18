package ch.vrsg.edu.webservice.client;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterRequest;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterResponse;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterService_Service;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.ObjectFactory;

public class WebserviceClientIT {

    private final ObjectFactory factory = new ObjectFactory();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeFindMitarbeiter() throws Exception {
        final MitarbeiterService_Service service = new MitarbeiterService_Service();
        FindeMitarbeiterRequest requestParameter = factory.createFindeMitarbeiterRequest();
        requestParameter.setNachname("Da Vinci");
        requestParameter.setVorname("Leonardo");
        FindeMitarbeiterResponse response = service.getMitarbeiterPort().findeMitarbeiter(requestParameter);
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getVorname(), is("Leonardo"));
        assertThat(response.getNachname(), is("Da Vinci"));
    }

}
