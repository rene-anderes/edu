package org.anderes.edu.employee.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Repräsentiert den Job-Title (Stellung) eines Mitarbeiters
 */
@Entity
public class JobTitle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "JOB_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Basic
    private String title;

    public JobTitle() {
    }
    
    public JobTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
