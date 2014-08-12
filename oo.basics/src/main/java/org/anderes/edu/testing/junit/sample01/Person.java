package org.anderes.edu.testing.junit.sample01;

import java.time.LocalDate;


public class Person {

	/** Der Name der Person */
	public String name = "";
	/** Geburtstag */
	private LocalDate birthday = LocalDate.MIN;
	
	/**
	 * Konstruktor
	 * 
	 * @param name Name der Person
	 */
	public Person(String name) {
		this.name = name;
	}
	
	/**
	 * Setzt den Geburtstag.
	 * 
	 * @param Datum
	 */
	public void setBirthday(LocalDate date) {
		birthday = date;
	}
	
	/**
	 * Gibt das Datum für den Geburtstag zurück.
	 * 
	 * @return Datum des Geburtstag
	 */
	public LocalDate getBirthday() {
		return birthday;
	}
		
	/**
	 * Liefert {@code true}, wenn die Person im angegeben Monat Geburtstag hat.
	 * 
	 * @return {@code true},wenn die Person im angegeben Monat Geburtstag hat, sonst {@code false}
	 */
	public boolean hasBirthday(int month) {
	    return birthday.getMonth().getValue() == month;
	}
}
