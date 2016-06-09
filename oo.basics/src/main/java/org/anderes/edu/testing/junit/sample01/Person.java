package org.anderes.edu.testing.junit.sample01;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Person {

    /** Der Name der Person */
    public String name = "";
    /** Geburtstag */
    private LocalDate birthday = LocalDate.MIN;
    /** Aliasname (Pseudonyme) der Person */
    private Set<String> aliasName = new TreeSet<>();

    /**
     * Konstruktor
     * 
     * @param name
     *            Name der Person
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
        if (date == null) {
            throw new IllegalArgumentException("Der Parameter darf nicht null sein.");
        }
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
     * Gibt an, ob die Person im angegeben Monat Geburtstag hat.
     * 
     * @return {@code true}, wenn die Person im angegeben
     *         Monat Geburtstag hat, sonst {@code false}
     */
    public boolean hasBirthday(int month) {
        return birthday.getMonth().getValue() == month;
    }

    /**
     * Fügt einen neuen Aliasname der Person hinzu.
     * 
     * @param alias
     *            Aliasname der hinzu gefügt wird
     */
    public void addAlias(String alias) {
        aliasName.add(alias);
    }

    /**
     * Entfernt den Aliasname der Person.
     * 
     * @param alias
     *            Aliasname der gelöscht wird
     */
    public void removeAlias(String alias) {
        aliasName.remove(alias);
    }

    /**
     * Gibt an, ob die Person über Aliasname verfügt
     * 
     * @return {@code true}, wenn die Person Aliasname
     *         besitzt, sonst {@code false}
     */
    public boolean hasAlias() {
        return !aliasName.isEmpty();
    }

    /**
     * Gibt an, ob die Person den entsprechenden Aliasname besitzt.
     * 
     * @param name
     *            Aliasname
     * @return {@code true}, wenn die Person diesen Aliasname besitzt,
     *         sonst {@code false}
     */
    public boolean isAlias(String name) {
        return aliasName.contains(name);
    }
}
