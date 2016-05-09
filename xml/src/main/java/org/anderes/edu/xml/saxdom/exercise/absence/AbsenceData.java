package org.anderes.edu.xml.saxdom.exercise.absence;

import java.util.Calendar;
import java.util.Optional;

public class AbsenceData implements Absence {

    private String firstname;
    private String lastname;
    private String personalnr;
    private String division;
    private String title;
    private String period;
    private Calendar absenceDate;
    private String note;
    
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
    public Calendar getDate() {
        return absenceDate;
    }

    @Override
    public Optional<String> getNote() {
        return Optional.ofNullable(note);
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

    public void setAbsenceDate(final Calendar absenceDate) {
        this.absenceDate = absenceDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
