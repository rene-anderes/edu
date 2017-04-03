package org.anderes.edu.exam.practise.cd;

import java.util.List;

/**
 * Informationen �ber eine Compact-Disk.<br>
 * Eine CD besteht aus einer CD-Beschreibung und aus 0 bis n Tracks
 * 
 * @author Ren� Anderes
 *
 */
public interface CompactDisc {

    /**
     * Setzen der CD-Beschreibung
     * 
     * @param description Beschreibung
     */
    void setDescription(final String description);
    
    /**
     * Gibt die CD-Beschreibung zur�ck.
     * 
     * @return Beschreibung
     */
    String getDescription();
    
    /**
     * Einen Track der CD setzen
     * 
     * @param track
     */
    void setTrack(final Track track);
    
    /**
     * Gibt den Track mit der entsprechenden Track-Nummer zur�ck.
     * 
     * @param trackNo Gew�nschte Track-Nummer
     * @return Track
     */
    Track getTrack(final int trackNo);
    
    /**
     * Gibt eine Liste mit allen Tracks zur�ck.
     * 
     * @return Trackliste
     */
    List<Track> getAllTracks();
    
    /**
     * Gibt die Laufzeit der ganzen CD zur�ck.
     * 
     * @return Laufzeit der Compact-Disc
     */
    int getTotalTime();
    
}
