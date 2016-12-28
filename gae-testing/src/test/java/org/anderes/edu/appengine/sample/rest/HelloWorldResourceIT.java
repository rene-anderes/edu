package org.anderes.edu.appengine.sample.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant;

import org.anderes.edu.appengine.sample.dto.MessageDto;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Before;
import org.junit.Test;

/**
 * Dieser Test funktioniert nur dann richtig, wenn der App-Engine DevServer läuft
 */
public class HelloWorldResourceIT {

    private final static Logger LOGGER = Logger.getLogger(HelloWorldResourceIT.class.getName());
    private UriBuilder uri;
    private Client client;
    
    @Before
    public void setup() {
        LOGGER.log(Level.INFO, "HelloWorldResourceIT setup");
        client = ClientBuilder.newClient();
        client.register(JacksonFeature.class).register(new LoggingFeature(LOGGER)); 
//        uri = UriBuilder.fromResource(MyApplication.class)
//                .scheme("https").host("testing-153906.appspot.com")
//                .path(HelloWorldResource.class);
        uri = UriBuilder.fromResource(MyApplication.class)
                .scheme("http").host("localhost").port(8089)
                .path(HelloWorldResource.class);
    }
    
    @Test
    public void shouldBeSayHello() {
        // given
        final WebTarget target = client.target(uri);
        
        // when
        final Response response = target.request(TEXT_PLAIN).buildGet().invoke();
        
        // then
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        assertThat(response.readEntity(String.class), is("Hello World"));
    }
    
    @Test
    public void shouldBeSendMessage() {
        // given
        final WebTarget target = client.target(uri);
        final Variant variant = new Variant(TEXT_PLAIN_TYPE, "de_CH", "UTF-8");
        final Entity<String> entity = Entity.entity("Hello World!", variant);

        // when
        final Response response = target.request(TEXT_PLAIN_TYPE).put(entity);
        
        // then
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(false));
    }
    
    @Test
    public void shouldBeMessageResponse() {
        // given
        final WebTarget target = client.target(uri.path("test"));
        
        // when
        final Response response = target.request(APPLICATION_JSON_TYPE).buildGet().invoke();
        
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.hasEntity(), is(true));
        assertThat(response.getMediaType(), is(APPLICATION_JSON_TYPE));
        final MessageDto dto = response.readEntity(MessageDto.class);
        assertThat(dto.getMessage(), is("Hello World"));
    }
}
