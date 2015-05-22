package org.anderes.edu.employee.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final JobTitle rhs = (JobTitle) obj;
        return new EqualsBuilder().append(title, rhs.title).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("title", title).toString();
    }
}
