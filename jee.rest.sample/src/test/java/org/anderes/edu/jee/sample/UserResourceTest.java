package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anderes.edu.jee.rest.sample.UserResource;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

public class UserResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(UserResource.class).property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_SERVER, Boolean.TRUE);
        /** MOXY und List<String> funktioniert nicht (BUG) -> Disable MOXY */
    }
    
    @Test
    public void shouldBeFindAllUser() {
        final Response response = target("users").request(APPLICATION_JSON_TYPE).buildGet().invoke();
       
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final List<String> users = response.readEntity(new GenericType<List<String>>() {});
        assertThat(users, is(notNullValue()));
        assertThat(users.size(), is(3));
    }
    
    @Test
    public void shouldBeCorrectHeader() {
        final Response response = target("users/john").request(TEXT_PLAIN_TYPE).buildGet().invoke();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        
        // Cache-Control
        final Map<String, List<Object>> headers = response.getHeaders();
        assertThat(headers.containsKey("Cache-Control"), is(true));
        final List<Object> cache_control_header = headers.get("Cache-Control");
        assertThat(cache_control_header.size(), is(1));
        final String cache_control = (String) cache_control_header.get(0);
        assertThat(cache_control.contains("max-age=5"), is(true));
        assertThat(cache_control.contains("no-store"), is(false));
        assertThat(cache_control.contains("private"), is(true));
        
        assertThat(response.hasEntity(), is(true));
        final String users = response.readEntity(String.class);
        assertThat(users, is(notNullValue()));
        assertThat(users, is("john"));
    }
}
