package org.anderes.edu.xml.saxdom.exercise.absence;

import java.time.LocalDate;
import java.util.Optional;

public interface Absence {

	public String getFirstname();
	
	public String getLastname();
	
	public String getPersonalNr();
	
	public String getDivision();
	
	public String getTitle();
	
	public String getPeriod();
	
	public LocalDate getDate();
	
	public Optional<String> getComment();
}
