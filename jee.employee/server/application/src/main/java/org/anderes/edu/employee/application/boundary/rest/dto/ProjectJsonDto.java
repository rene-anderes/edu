package org.anderes.edu.employee.application.boundary.rest.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Beispiel DTO mit Jackson Core (Data-Binding) Annotations
 * 
 * @author René Anderes
 *
 */
@JsonAutoDetect
public class ProjectJsonDto {
    
    private Long projectno;
    private String projectname;
    private String description;
    private Boolean isActive;
    private String projecttype;
    
    private ProjectJsonDto() {}
    
    public static ProjectJsonDto createProjectJsonDto(final Long projectno) {
        final ProjectJsonDto projectJsonDto = new ProjectJsonDto();
        projectJsonDto.setProjectno(projectno);
        return projectJsonDto;
    }
    
    public Long getProjectno() {
        return projectno;
    }

    private void setProjectno(final Long projectnumber) {
        this.projectno = projectnumber;
    }
    
    public String getProjectname() {
        return projectname;
    }
    
    public ProjectJsonDto setProjectname(String projectname) {
        this.projectname = projectname;
        return this;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ProjectJsonDto setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public ProjectJsonDto setIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }
    
    public String getProjecttype() {
        return projecttype;
    }
    
    public ProjectJsonDto setProjecttype(String projecttype) {
        this.projecttype = projecttype;
        return this;
    }
}
