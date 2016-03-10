 
package org.anderes.edu.jee.rest.sample;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.anderes.edu.jee.rest.sample.dto.Milestone;
import org.anderes.edu.jee.rest.sample.dto.Project;

/**
 * REST-Service für Projekte
 * <p>
 * POJO-based JSON Binding
 * <p>
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#json">Jersey JSON support</a><br>
 * <a href="https://jersey.java.net/documentation/latest/user-guide.html#json.moxy">MOXy</a> 
  * 
 * @author René Anderes
 *
 */
@Path("projects")
public class PojoBasedJsonBinding {

    @GET
	@Produces(APPLICATION_JSON)
	public Response getProjects() { 
        final GenericEntity<Collection<Project>> genericList = new GenericEntity<Collection<Project>>(createProjectList()) {};
        return Response.ok().encoding(UTF_8.name()).entity(genericList).build();
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
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
        final Project project = new Project(nextInt(1, 1000));
        project.setDescription(randomAlphabetic(nextInt(1, 1000)))
            .setProjectname(randomAlphabetic(25)).addMilestone(createMilestone()).addMilestone(createMilestone());
        return project;
    }
    
    private Milestone createMilestone() {
        final Milestone milestone = new Milestone();
        milestone.setDescription(randomAlphabetic(3));
        milestone.setDate(new Date());
        return milestone;
    }
}