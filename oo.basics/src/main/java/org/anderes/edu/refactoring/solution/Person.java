package org.anderes.edu.refactoring.solution;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Person {

    /** Mögliche Adress-Typen */
    public enum AddressType {
        PRIVATE, BUSINESS
    };

    private final static int FULL_AGE_YEAR = 18;
    /** Der Name der Person */
    private String name = "";
    /** Liste von Adressen */
    private Map<AddressType, String> addresses = new HashMap<AddressType, String>();
    /** Geburtstag */
    private LocalDate birthday = LocalDate.MIN;

    /**
     * Konstruktor
     * 
     * @param name
     *            Name der Person
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
    @Override
    public String toString() {
        String toString = bannerToString();
        toString += nameToString();
        toString += AddressesToString();
        toString += birthdayToString();
        toString += bannerToString();
        return toString;
    }

    private String birthdayToString() {
        return String.format("Geburtstag: %1$td.%1$tm.%1$tY", birthday) + '\n';
    }

    private String AddressesToString() {
        String toString = "";
        for (AddressType addressType : addresses.keySet()) {
            toString += addressType + ": " + addresses.get(addressType) + '\n';
        }
        return toString;
    }

    private String nameToString() {
        return "Name: " + name + '\n';
    }

    private String bannerToString() {
        return  "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + '\n';
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
     * @param type
     *            Adress-Type
     * @param address
     *            Adresse
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Refactoring: Erklärende Variablen einführen
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Liefert {@code true}, wenn die Person im angegeben Monat Geburtstag hat.
     * 
     * @return {@code true},wenn die Person im angegeben Monat Geburtstag hat, sonst {@code false}
     */
    public boolean hasBirthday(Month month) {
        return birthday.getMonth().compareTo(month) == 0;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Refactoring: Erklärende Variablen einführen, Magic Number ersetzen
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Liefert {@code true}, wenn die Person volljährig ist.
     * 
     * @return {@code true}, wenn die Person volljährig ist, sonst {@code false}
     */
    public boolean isFullAge() {
        LocalDate fullAgeDate = LocalDate.now().minusYears(FULL_AGE_YEAR);
        return birthday.compareTo(fullAgeDate) < 0;
    }
}
