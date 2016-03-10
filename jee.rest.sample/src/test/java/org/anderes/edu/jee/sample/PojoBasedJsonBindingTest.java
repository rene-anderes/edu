package org.anderes.edu.jee.sample;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anderes.edu.jee.rest.sample.PojoBasedJsonBinding;
import org.anderes.edu.jee.rest.sample.dto.Project;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

public class PojoBasedJsonBindingTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(PojoBasedJsonBinding.class);
    }
    
    @Test
    public void testApplicationWadl() {
        final WebTarget target = target();
        final String applicationWadl = target.path("application.wadl").request().get(String.class);
        assertTrue("Something wrong. Returned wadl length is not > 0", applicationWadl.length() > 0);
    }
    
    @Test
    public void shouldBeFindAll() {
        final Response response = target("projects").request(APPLICATION_JSON_TYPE).get();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final List<Project> projects = response.readEntity(new GenericType<List<Project>>() {});
        assertThat(projects, is(notNullValue()));
    }
    
    @Test
    public void shouldBeFindOne() {
        final Response response = target("projects/1002").request(APPLICATION_JSON_TYPE).get();
        
        assertThat(response, is(notNullValue()));
        assertThat(response.getStatusInfo(), is(Status.OK));
        assertThat(response.hasEntity(), is(true));
        final Project project = response.readEntity(Project.class);
        assertThat(project.getProjectNo() > 0, is(true));
        assertThat(project.getMilestones(), is(notNullValue()));
        assertThat(project.getMilestones().size(), is(2));
    }

}
