package org.anderes.edu.jee.rest.sample.dto;

import java.util.Date;

public class Milestone {

    private Date date;
    private String description;
    
    public Milestone() {
        super();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
