package org.anderes.edu.refactoring.initial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Person {

	/** Der Name der Person */
	public String name = "";
	/** Liste von Adressen */
	private Map<String, String> addresses = new HashMap<String, String>();
	/** Geburtstag */
	private Date birthday = new Date();
	
	/**
	 * Konstruktor
	 * 
	 * @param name Name der Person
	 */
	public Person(String name) {
		this.name = name;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Methode extrahieren
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	/**
	 * Druckt alle Daten der Person
	 * 
	 * @return Liste der druckbaren Details
	 */
	public List<String> printData() {
		printBanner();
		
		// Details ausgeben
		List<String> prindData = new ArrayList<String>();
		prindData.add(String.format("Name: %s", name));
		for (String address : addresses.values()) {
			prindData.add(String.format("Adresse: %s", address));
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birthday);
		String printBirthday = String.format("Geburtstag: %1$td.%1$tm.%1$tY", birthday);
		prindData.add(printBirthday);
		
		// Details in die Standard-Konsole schreiben
		for (String data : prindData) {
			System.out.println(data);
		}
		
		return prindData;
	}

	/**
	 * Erzeugt einen Banner f�r die Ausgabe
	 */
	private void printBanner() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Collection kapseln
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * Gibt eine Liste mit allen verf�gbaren Adressen zur�ck.
	 * 
	 * @return Map mit Adressen
	 */
	public Map<String, String> getAddresses() {
		return addresses;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Feld kapseln
	// Das Attribute "name" soll nicht mehr public sein
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Methoden umbenennen
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	/**
	 * Setzt den Geburtstag.
	 * 
	 * @param Datum
	 */
	public void setBD(Date date) {
		birthday = date;
	}
	
	/**
	 * Gibt das Datum für den Geburtstag zur�ck.
	 * 
	 * @return Datum des Geburtstag
	 */
	public Date getBD() {
		return birthday;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Erkl�rende Variablen einf�hren
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	/**
	 * Liefert {@code true}, wenn die Person im angegeben Monat Geburtstag hat.
	 * 
	 * @return {@code true},wenn die Person im angegeben Monat Geburtstag hat, sonst {@code false}
	 */
	public boolean hasBirthday(int month) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birthday);
		return calendar.get(Calendar.MONTH) == month;
	}
}
