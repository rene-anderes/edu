package org.anderes.edu.jpa.relation.map;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;

@Entity
public class Story {

    @Id @GeneratedValue
    private Long id;
    private String description;
    
    @ElementCollection
    @CollectionTable(name="STORY_TASK", 
        joinColumns = { @JoinColumn(name = "STORY_ID") })
    @MapKeyJoinColumn(name="TASK_ID")
    @Column(name="SEQUENCENO")
    private Map<Task, Integer> tasks = new HashMap<>();
    
    public Long getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Map<Task, Integer> getTasks() {
        return tasks;
    }
    
    public void setTasks(Map<Task, Integer> tasks) {
        this.tasks = tasks;
    }
}
