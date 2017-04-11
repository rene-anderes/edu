package org.anderes.edu.exam.practise.book;

/**
 * Schittstelle zum Kapitel das Inhalt eines Buches ist.
 */
public interface Chapter {

    /**
     * Liefert die Kapitelnummer zurück.
     * 
     * @return Eindeutige Kapitelnummer
     */
    int getChapterNumber();

    /**
     * Liefert den Inhalt des Kapitels zurück.
     * 
     * @return Inhalt
     */
    String getContent();

    /**
     * Liefert die Anzahl Wörter dieses Kaiptels zurück.
     * 
     * @return Anzahl Wörter
     */
    int getNumberOfWords();
}
