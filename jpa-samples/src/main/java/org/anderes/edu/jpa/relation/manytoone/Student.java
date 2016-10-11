package org.anderes.edu.jpa.relation.manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "STUDENTNAME")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "CLASSROOM_ID")
    private Classroom classroom;

    Student() {
        super();
    }
    
    public Student(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
