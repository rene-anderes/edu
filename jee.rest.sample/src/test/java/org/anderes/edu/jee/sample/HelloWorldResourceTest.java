package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.anderes.edu.jee.rest.sample.HelloWorldResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class HelloWorldResourceTest extends JerseyTest {
     
    @Override
    protected Application configure() {
        return new ResourceConfig(HelloWorldResource.class);
    }
 
    @Test
    public void testPOST() {
        final Variant variant = new Variant(TEXT_PLAIN_TYPE, "de_CH", "UTF-8");
        final Entity<String> entity = Entity.entity("Hello World!", variant);
        
        final String hello = target("helloworld").request().buildPost(entity).invoke().readEntity(String.class);
        assertEquals("Hello World!", hello);
    }
    
    @Test
    public void shouldBeNewMessage() throws URISyntaxException {
        
        // given
        final Variant variant = new Variant(TEXT_PLAIN_TYPE, "de_CH", "UTF-8");
        final Entity<String> entity = Entity.entity("Hello World!", variant);
        
        // when
        final Response response = target("helloworld").request().buildPut(entity).invoke();
        
        // then
        assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(CREATED.getStatusCode()));
        assertThat("Unerwartetet location", response.getLocation(), is(new URI("http://localhost:9998/helloworld/2033")));        
    }
}
