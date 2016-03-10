package org.anderes.edu.jee.rest.sample.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Damit dieses POJO mittels MOXy in ein JSON serislisiert werden kann,
 * müssen alle Klassenmember einen Getter und Setter haben.
 *
 */
public class Project {
    
    private int projectNo;
    private String projectname;
    private String description;
    private Set<Milestone> milestones = new HashSet<>();
    
    /**
     * Dieser Konstruktor wird von MOXy benötigt um das Objekt zu serialisieren
     */
    @SuppressWarnings("unused")
    private Project() {
        super();
    }

    public Project(int projectNo) {
        super();
        this.projectNo = projectNo;
    }

    public int getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(int projectNo) {
        this.projectNo = projectNo;
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
    
    public Project addMilestone(final Milestone milestone) {
        this.milestones.add(milestone);
        return this;
    }
    
    public Project removeMilestone(final Milestone milestone) {
        this.milestones.remove(milestone);
        return this;
    }
    
    public Set<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(Set<Milestone> milestones) {
        this.milestones = milestones;
    }
}
