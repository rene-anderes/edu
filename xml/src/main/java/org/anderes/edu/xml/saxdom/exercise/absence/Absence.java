package org.anderes.edu.xml.saxdom.exercise.absence;

import java.util.Calendar;

public interface Absence {

	public String getFirstname();
	
	public String getLastname();
	
	public String getPersonalNr();
	
	public String getDivision();
	
	public String getTitle();
	
	public String getPeriod();
	
	public Calendar getDate();

	public void setAbsenceDate(final Calendar absenceDate);

	public void setPeriod(final String period);

	public void setTitle(final String title);

	public void setDivision(final String division);

	public void setPersonalnr(final String personalnr);

	public void setLastname(final String lastname);

	public void setFirstname(final String firstname);
}
