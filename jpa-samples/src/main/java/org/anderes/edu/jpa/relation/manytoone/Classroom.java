package org.anderes.edu.jpa.relation.manytoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Classroom {

    @Id
    @GeneratedValue
    private Long id;
    
    private String description;

    Classroom() {
        super();
    }
    
    public Classroom(String description) {
        super();
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
