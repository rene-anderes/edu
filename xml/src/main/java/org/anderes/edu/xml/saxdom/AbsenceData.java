package org.anderes.edu.xml.saxdom;

import java.util.Calendar;

public class AbsenceData implements Absence {

    private String firstname;
    private String lastname;
    private String personalnr;
    private String division;
    private String title;
    private String period;
    private Calendar absenceDate;
    
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
    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    @Override
    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    @Override
    public void setPersonalnr(final String personalnr) {
        this.personalnr = personalnr;
    }

    @Override
    public void setDivision(final String division) {
        this.division = division;
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public void setPeriod(final String period) {
        this.period = period;
    }

    @Override
    public void setAbsenceDate(final Calendar absenceDate) {
        this.absenceDate = absenceDate;
    }

}
