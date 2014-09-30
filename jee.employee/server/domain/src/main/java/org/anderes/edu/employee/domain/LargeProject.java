package org.anderes.edu.employee.domain;

import java.util.Calendar;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Repräsentiert ein grosses Projekt
 * <p>
 * Beispiel für JOINED inheritance.
 */
@Entity
@Table(name = "LPROJECT")
@DiscriminatorValue("L")
public class LargeProject extends Project {
    private static final long serialVersionUID = 1L;
    @Basic
    private double budget;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar milestone = Calendar.getInstance();

    public LargeProject() {
        super();
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Calendar getMilestone() {
        return milestone;
    }

    public void setMilestone(Calendar milestone) {
        this.milestone = milestone;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(budget).append(milestone).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
          return false;
        }
        final LargeProject rhs = (LargeProject) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(budget, rhs.budget).append(milestone, rhs.milestone).isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("budget", budget).append("milestone", milestone).toString();
    }
}
