package ch.vrsg.edu.webservice.client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.junit.Before;
import org.junit.Test;

/**
 * REST-Cleint mit JSON-P Verarbetung der Daten die mittels Json übermittelt werden
 * 
 * @author René Anderes
 *
 */
public class RestClientIT {

    private UriBuilder uri;
    private Client client;

    @Before
    public void setup() {
        client = ClientBuilder.newBuilder().register(JsonProcessingFeature.class).build();
        uri = UriBuilder.fromPath("sample").scheme("http").host("localhost").port(7001).path("resources").path("employees");
    }

    @Test
    public void shouldBeFindAll() {

        final WebTarget target = client.target(uri);

        final Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        assertThat(response.getStatus(), is(200));
        assertThat(response.hasEntity(), is(true));

        // JSON-P Verarbeitung
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.size(), is(3));
        jsonArray.forEach(element -> assertThat(((JsonObject) element).getString("firstname").length() > 0, is(true)));
        jsonArray.forEach(element -> assertThat(((JsonObject) element).getString("lastname").length() > 0, is(true)));
    }
}
