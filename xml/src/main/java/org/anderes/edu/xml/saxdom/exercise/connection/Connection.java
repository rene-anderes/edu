package org.anderes.edu.xml.saxdom.exercise.connection;

import java.time.LocalDate;

public class Connection {

    private LocalDate date;
    private Locality fromLocality = new Locality();
    private Locality toLocality = new Locality();
    private String travelWith;
    private Allocation allocation = new Allocation();
    private String comment;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Locality getFrom() {
        return  fromLocality;
    }

    public Locality getTo() {
        return toLocality;
    }

    public String getTravelWith() {
        return travelWith;
    }

    public void setTravelWith(String travelWith) {
        this.travelWith = travelWith;
    }

    public Allocation getAllocation() {
        return allocation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
