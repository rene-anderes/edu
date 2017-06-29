package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static javax.ws.rs.core.Response.Status.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;

import org.anderes.edu.jee.rest.sample.HelloWorldResource;
import org.anderes.edu.jee.rest.sample.IllegalArgumentExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class HelloWorldResourceTest extends JerseyTest {
     
    @Override
    protected Application configure() {
        return new ResourceConfig(HelloWorldResource.class, IllegalArgumentExceptionMapper.class);
    }
 
    @Test
    public void shouldBeHalloWorld() {
             
    	// when
        final Response response = target("helloworld").request().get();
        
        // then
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.readEntity(String.class), is("Hello World"));
    }
    
    @Test
    public void shouldBeNewMessage() throws URISyntaxException {
        
        // given
        final Variant variant = new Variant(TEXT_PLAIN_TYPE, "de_CH", "UTF-8");
        final Entity<String> entity = Entity.entity("Hello World!", variant);
        
        // when
        final Response response = target("helloworld").request().buildPost(entity).invoke();
        
        // then
        assertThat("Unerwartete Antwort vom Server.", response.getStatus(), is(CREATED.getStatusCode()));
        assertThat("Unerwartetet location", response.getLocation(), is(new URI("http://localhost:9998/helloworld/2033")));        
    }
    
    @Test
    public void shouldBeSayHello() {
             
        // when
        final Response response = target("helloworld/Bill").request().get();
        
        // then
        assertThat(response.getStatus(), is(OK.getStatusCode()));
        assertThat(response.readEntity(String.class), is("Hello Bill"));
    }
    
    @Test
    public void shouldBeSayHelloNOK() {
             
        // when
        final Response response = target("helloworld/b").request().get();
        
        // then
        assertThat(response.getStatus(), is(BAD_REQUEST.getStatusCode()));
    }
}
