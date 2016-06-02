package ch.vrsg.edu.webservice.client;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

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
    private final String username = "weblogic";
    private final String password = "welcome1";

    @Before
    public void setup() {
        client = ClientBuilder.newClient();

        /* Client-Side HTTP Basic Access Authentication With JAX-RS 2.0 */
        client.register(new Authenticator(username, password));

        uri = UriBuilder.fromPath("ws-sample").scheme("http").host("localhost").port(7001).path("resources").path("employees");
    }

    @Test
    public void shouldBeFindAll() {

        final WebTarget target = client.target(uri);

        final Response response = target.request(APPLICATION_JSON_TYPE).get();
        
        assertThat(response.getStatus(), is(200));
        assertThat(response.hasEntity(), is(true));

        /* JSON-P Verarbeitung */
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.size(), is(3));
        jsonArray.forEach(element -> assertThat(((JsonObject) element).getString("firstname").length() > 0, is(true)));
        jsonArray.forEach(element -> assertThat(((JsonObject) element).getString("lastname").length() > 0, is(true)));
    }
    
    @Test
    public void shouldBeSaveNewEmployee() {
        
        final WebTarget target = client.register(JsonProcessingFeature.class).target(uri);
        final Variant variant = new Variant(APPLICATION_JSON_TYPE, "de_CH", "UTF-8");
        JsonObject employee = Json.createObjectBuilder().add("firstname", "Bill").add("lastname", "Gates").build();
        final Entity<JsonObject> entity = Entity.entity(employee, variant);
        final Response response = target.request().post(entity);
        
        assertThat(response.getStatus(), is(201));
    }
}
