package org.anderes.edu.employee.domain;

import java.util.Calendar;

import javax.persistence.*;

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
}
