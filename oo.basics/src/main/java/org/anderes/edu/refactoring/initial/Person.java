package org.anderes.edu.refactoring.initial;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class Person {

    /** Der Name der Person */
    public String name = "";
    /** Liste von Adressen */
    private Map<String, String> addresses = new HashMap<String, String>();
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
    @Override
    public String toString() {
        String toString = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + '\n';
        toString += "Name: " + name + '\n';
        for (String addressType : addresses.keySet()) {
            toString += addressType + ": " + addresses.get(addressType) + '\n';
        }
        toString += String.format("Geburtstag: %1$td.%1$tm.%1$tY", birthday) + '\n';

        toString += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + '\n';

        return toString;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Refactoring: Collection kapseln
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Gibt eine Liste mit allen verfügbaren Adressen zurück.
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
    public void setBD(LocalDate date) {
        birthday = date;
    }

    /**
     * Gibt das Datum für den Geburtstag zurück.
     * 
     * @return Datum des Geburtstag
     */
    public LocalDate getBD() {
        return birthday;
    }


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
        return birthday.compareTo(LocalDate.now().minusYears(18)) < 0;
    }
}
