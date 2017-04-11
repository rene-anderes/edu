package org.anderes.edu.exam.practise.cd;

/**
 * Ein einfacher Datencontainer f�r einen Track einer CD
 * 
 * @author Ren� Anderes
 *
 */
public interface Track {
    /**
     * Setzen der Track-Nummer
     * 
     * @param number Nummer
     */
    void setTrackNo(int number);
    
    /**
     * Setzen des Titels bzw. Beschreibung
     * 
     * @param description Beschreibung
     */
    void setDescription(final String description);
    
    /**
     * Setzen der Laufzeit des Tracks
     * 
     * @param time Laufzeit
     */
    void setTime(final int time);
    
    /**
     * Gibt die Laufzeit des Tracks zur�ck.
     * 
     * @return Laufzeit
     */
    int getTime();
    
    /**
     * Gibt die Beschreibung des Tracks zur�ck.
     * 
     * @return Beschreibung
     */
    String getDescription();
    
    /**
     * Gibt die Track-Nummer zur�ck.
     * 
     * @return Tracknummer
     */
    int getTrackNo();
}
