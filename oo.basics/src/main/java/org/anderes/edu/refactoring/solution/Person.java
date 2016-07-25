package org.anderes.edu.refactoring.solution;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Person {

	/** Mögliche Adress-Typen */
	public enum AddressType { PRIVATE, BUSINESS };
	/** Der Name der Person */
	private String name = "";
	/** Liste von Adressen */
	private Map<AddressType, String> addresses = new HashMap<AddressType, String>();
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
		List<String> details = getDetails();
		printDetails(details);
		return details;
	}

	/**
	 * Druckt alle übergebenen Details
	 * 
	 * @param details Daten der Person
	 */
	private void printDetails(List<String> details) {
		// Details in die Standard-Konsole schreiben
		for (String data : details) {
			System.out.println(data);
		}
	}
	
	/**
	 * Erzeugt eine Liste für die Ausgabe
	 * 
	 * @return Liste mit Daten
	 */
	private List<String> getDetails() {
		List<String> prindData = new ArrayList<String>();
		prindData.add(String.format("Name: %s", name));
		for (String address : addresses.values()) {
			prindData.add(String.format("Adresse: %s", address));
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birthday);
		String printBirthday = String.format("Geburtstag: %1$d.%2$d.%3$d", 
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
		prindData.add(printBirthday);
		return prindData;
	}
	
	/**
	 * Druckt einen Banner
	 */
	private void printBanner() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Collection kapseln
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Gibt die entsprechende Adresse zurück.
	 * 
	 * @param Adress-Typ
	 * @return Adresse oder {@code null}, wenn keine Adresse für den Typ existiert.
	 */
	public String getAddress(AddressType type) {
		return addresses.get(type);
	}
	
	/**
	 * Setzt eine Adresse für einen bestimmten Typ
	 * 
	 * @param type Adress-Type
	 * @param address Adresse
	 */
	public void addAddress(AddressType type, String address) {
		addresses.put(type, address);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Feld kapseln
	// Das Attribute "name" soll nicht mehr public sein
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public String getName() {
		return name;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Methoden umbenennen
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	/**
	 * Setzt den Geburtstag.
	 * 
	 * @param Datum
	 */
	public void setBirthday(Date date) {
		birthday = date;
	}
	
	/**
	 * Gibt das Datum für den Geburtstag zurück.
	 * 
	 * @return Datum des Geburtstag
	 */
	public Date getBirthday() {
		return birthday;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// Refactoring: Erklärende Variablen einführen
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
	
	/**
	 * Liefert {@code true}, wenn die Person im angegeben Monat Geburtstag hat.
	 * 
	 * @return {@code true},wenn die Person im angegeben Monat Geburtstag hat, sonst {@code false}
	 */
	public boolean hasBirthday(int month) {
		int birthdayMonth = getBirthdayMonth();
		return birthdayMonth == month;
	}
	
	private int getBirthdayMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(birthday);
		return calendar.get(Calendar.MONTH);
	}
}
