package org.anderes.edu.xml.saxdom.exercise.absence;

import java.time.LocalDate;
import java.util.Optional;

public class AbsenceData implements Absence {

    private String firstname;
    private String lastname;
    private String personalnr;
    private String division;
    private String title;
    private String period;
    private LocalDate absenceDate;
    private String comment;
    
       @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public String getPersonalNr() {
        return personalnr;
    }

    @Override
    public String getDivision() {
        return division;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPeriod() {
        return period;
    }

    @Override
    public LocalDate getDate() {
        return absenceDate;
    }

    @Override
    public Optional<String> getComment() {
        return Optional.ofNullable(comment);
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public void setPersonalnr(final String personalnr) {
        this.personalnr = personalnr;
    }

    public void setDivision(final String division) {
        this.division = division;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    public void setComment(String note) {
        this.comment = note;
    }

    public void setAbsenceDate(LocalDate date) {
        this.absenceDate = date;
    }
}
