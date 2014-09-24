package org.anderes.edu.employee.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Repräsentiert einen Abscluss oder akademischer Grad.
 */
@Entity
public class Degree implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DEGREE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic
    private String name;

    public Degree() {
    }
    
    public Degree(String name) {
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
