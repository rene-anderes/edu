package org.anderes.edu.jpa.manytomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Project {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
    
    @Column(name = "PROJECT_NAME", nullable = false)
    private String name;

    Project() {
        super();
    };
    
    public Project(String name) {
        super();
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

}
