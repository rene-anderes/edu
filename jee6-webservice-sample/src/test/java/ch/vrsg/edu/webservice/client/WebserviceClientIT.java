package ch.vrsg.edu.webservice.client;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterRequest;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.FindeMitarbeiterResponse;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterNotFoundException;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterService;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.MitarbeiterService_Service;
import ch.vrsg.intra.xmlns.xmlns.mitarbeiter._1.ObjectFactory;

public class WebserviceClientIT {

    private final String wsdlLocation = "http://localhost:7001/sample/MitarbeiterService?WSDL";
    private final ObjectFactory factory = new ObjectFactory();
    private MitarbeiterService port;

    @Before
    public void setUp() throws Exception {
        final MitarbeiterService_Service service = new MitarbeiterService_Service(new URL(wsdlLocation));
        port = service.getPort(MitarbeiterService.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeFindMitarbeiter() throws Exception {
        FindeMitarbeiterRequest requestParameter = factory.createFindeMitarbeiterRequest();
        requestParameter.setNachname("Da Vinci");
        requestParameter.setVorname("Leonardo");
        FindeMitarbeiterResponse response = port.findeMitarbeiter(requestParameter);
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getVorname(), is("Leonardo"));
        assertThat(response.getNachname(), is("Da Vinci"));
    }

    @Test(expected = MitarbeiterNotFoundException.class)
    public void shouldBeNotFind() throws Exception {
        FindeMitarbeiterRequest requestParameter = factory.createFindeMitarbeiterRequest();
        requestParameter.setNachname("August");
        requestParameter.setVorname("Leonardo");
        port.findeMitarbeiter(requestParameter);
    }
}
