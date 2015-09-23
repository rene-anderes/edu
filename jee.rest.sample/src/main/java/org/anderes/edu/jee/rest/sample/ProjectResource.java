 
package org.anderes.edu.jee.rest.sample;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

import static java.nio.charset.StandardCharsets.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.anderes.edu.jee.rest.sample.dto.Project;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;

/**
 * REST-Service für Projekte
 * <p>
 * JSON with Padding Support
 * <p>
 * siehe https://jersey.java.net/documentation/latest/user-guide.html#d0e8676
 * 
 * @author René Anderes
 *
 */
@Path("projects")
public class ProjectResource {

    @GET
    @JSONP
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjects() { 
        final GenericEntity<Collection<Project>> genericList = new GenericEntity<Collection<Project>>(createProjectList()) {};
        return Response.ok().encoding(UTF_8.name()).entity(genericList).build();
    }

    @GET
    @Path("{id}")
    @JSONP
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProject(@PathParam("id") String id) { 
        return Response.ok().encoding(UTF_8.name()).entity(createRandomProject()).build();
    }
    
    private List<Project> createProjectList() {
        final ArrayList<Project> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final Project project = createRandomProject();
            list.add(project);
        }
        return list;
    }

    private Project createRandomProject() {
        final Project project = new Project(nextLong(1, 1000));
        project.setDescription(randomAlphabetic(nextInt(1, 1000)))
            .setProjectname(randomAlphabetic(25)).addMilestone(randomAlphabetic(3)).addMilestone(randomAlphabetic(3));
        return project;
    }
}