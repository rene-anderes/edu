package org.anderes.edu.exam.practise.book;

import java.util.Collection;

/**
 * Schnittstelle für den Zugriff auf den Inhalt eines Buches<br>
 * Jedes Buch besteht aus 0 bis n Kapitel. Jedes Kapitel gibt es nur einmal.
 * 
 */
public interface Book {

    /**
     * Setzt den Buchtitel.
     * 
     * @param title
     *            Nuchtitel
     */
    void setTitle(final String title);

    /**
     * Gibt den Buchtitel zurück.
     * 
     * @return Buchtitel
     */
    String getTitle();

    /**
     * Dem Buch ein neues Kapitel hinzufügen
     * 
     * @param chapter
     *            Kapitel
     */
    void setChapter(final Chapter chapter);

    /**
     * Entfernt ein Kapitel aus dem Buch.
     * 
     * @param chapterNumber
     *            Kapitelnummer
     */
    void removeChapter(final int chapterNumber);

    /**
     * Liefert das gewünschte Kapitel zurück.
     * 
     * @param ChapterNumber
     *            Kapitelnummer
     * @return Kapitel mit entsprechender Nummer
     */
    Chapter getChapter(int ChapterNumber);

    /**
     * Gibt eine Liste mit allen Kapitel zurück.
     * 
     * @return Alle Kapitel
     */
    Collection<Chapter> getAllChapter();

    /**
     * Gibt die Anzahl Wörter des ganzen Buchs zurück.
     * 
     * @return Anzahl Worte des ganzen Buchs
     */
    int getTotalNumberOfWords();
}
