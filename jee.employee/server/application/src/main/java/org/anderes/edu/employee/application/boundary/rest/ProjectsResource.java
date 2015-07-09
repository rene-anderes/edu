package org.anderes.edu.employee.application.boundary.rest;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anderes.edu.employee.application.LargeProjectFacade;
import org.anderes.edu.employee.application.SmallProjectFacade;
import org.anderes.edu.employee.domain.LargeProject;
import org.anderes.edu.employee.domain.Project;
import org.anderes.edu.employee.domain.SmallProject;

@Path("/projects")
@Stateless
@TransactionAttribute(NEVER)
@Produces({ APPLICATION_JSON })
public class ProjectsResource {

    private enum ProjectType {
        SMALL, LARGE
    };

    @EJB
    private LargeProjectFacade lFacade;
    @EJB
    private SmallProjectFacade sFacade;

    @GET
    @Path("/{id: [0-9]+}")
    public Response findProject(@PathParam("id") final Long projectId) {

        JsonObject project = null;
        Optional<SmallProject> small = sFacade.findOne(projectId);
        if (small.isPresent()) {
            project = createJson(small.get(), ProjectType.SMALL);
        } else {
            Optional<LargeProject> large = lFacade.findOne(projectId);
            if (!large.isPresent()) {
                throw new WebApplicationException(Status.NOT_FOUND);
            }
            project = createJson(large.get(), ProjectType.LARGE);
        }

        return Response.ok().entity(project).build();
    }

    /**
     * JSON Processing
     */
    private JsonObject createJson(Project project, ProjectType type) {
        return Json.createObjectBuilder()
                        .add("Projectname", project.getName())
                        .add("Description", project.getDescription())
                        .add("Projecttype", type.name())
                        .add("isActive", project.isActive().toString())
                        .build();
    }

}