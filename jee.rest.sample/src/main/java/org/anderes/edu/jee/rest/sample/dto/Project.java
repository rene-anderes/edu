package org.anderes.edu.jee.rest.sample.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Project {
    
    private final Long projectNo;
    private String projectname;
    private String description;
    private Set<String> milestones = new HashSet<>();
    
    public Project(Long projectNo) {
        super();
        this.projectNo = projectNo;
    }

    public Long getProjectNo() {
        return projectNo;
    }

    public String getProjectname() {
        return projectname;
    }
    
    public Project setProjectname(String projectname) {
        this.projectname = projectname;
        return this;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Project setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public Project addMilestone(final String milestone) {
        this.milestones.add(milestone);
        return this;
    }
    
    public Project removeMilestone(final String milestone) {
        this.milestones.remove(milestone);
        return this;
    }
    
    public Collection<String> getMilestones() {
        return Collections.unmodifiableSet(milestones);
    }
}
