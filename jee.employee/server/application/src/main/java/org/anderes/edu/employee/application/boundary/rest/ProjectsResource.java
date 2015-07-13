package org.anderes.edu.employee.application.boundary.rest;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.anderes.edu.employee.application.LargeProjectFacade;
import org.anderes.edu.employee.application.SmallProjectFacade;
import org.anderes.edu.employee.application.boundary.rest.dto.ProjectJsonDto;
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
    @Path("/")
    public Response findProjects() {
        final ArrayList<ProjectJsonDto> list = new ArrayList<>();
        
        List<SmallProject> smallProjects = sFacade.findAll();
        smallProjects.forEach(project -> list.add(createProjectJsonDto(project, ProjectType.SMALL)));

        List<LargeProject> largeProjects = lFacade.findAll();
        largeProjects.forEach(project -> list.add(createProjectJsonDto(project, ProjectType.LARGE)));
        
        /* Generic-Entity für die Liste von Entitäten */
        GenericEntity<List<ProjectJsonDto>> genericList = new GenericEntity<List<ProjectJsonDto>>(list) {};
        
        return Response.ok(genericList).build();
    }

    /**
     * Jackson Core (Data-Binding) Annotations
     */
    private ProjectJsonDto createProjectJsonDto(final Project project, final ProjectType type) {
        return ProjectJsonDto.createProjectJsonDto(project.getId())
                        .setProjectname(project.getName())
                        .setIsActive(project.isActive())
                        .setDescription(project.getDescription())
                        .setProjecttype(type.name());
    }
    
    
    
    @GET
    @Path("/{id: [0-9]+}")
    public Response findProject(@PathParam("id") final Long projectId) {

        JsonObject project = null;
        Optional<SmallProject> small = sFacade.findOne(projectId);
        if (small.isPresent()) {
            project = createJsonObject(small.get(), ProjectType.SMALL);
        } else {
            Optional<LargeProject> large = lFacade.findOne(projectId);
            if (!large.isPresent()) {
                throw new WebApplicationException(Status.NOT_FOUND);
            }
            project = createJsonObject(large.get(), ProjectType.LARGE);
        }

        return Response.ok().entity(project).build();
    }

    /**
     * JSON Processing
     */
    private JsonObject createJsonObject(Project project, ProjectType type) {
        return Json.createObjectBuilder()
                        .add("projectno", project.getId())
                        .add("projectname", project.getName())
                        .add("description", project.getDescription())
                        .add("projecttype", type.name())
                        .add("isActive", project.isActive())
                        .build();
    }

}