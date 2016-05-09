package org.anderes.edu.xml.saxdom.exercise.absence;

import java.util.Calendar;
import java.util.Optional;

public interface Absence {

	public String getFirstname();
	
	public String getLastname();
	
	public String getPersonalNr();
	
	public String getDivision();
	
	public String getTitle();
	
	public String getPeriod();
	
	public Calendar getDate();
	
	public Optional<String> getNote();
}
