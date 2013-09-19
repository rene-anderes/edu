package org.anderes.edu.dbc.cd;

import java.util.List;

/**
 * Informationen über eine Compact-Disk.<br>
 * Eine CD besteht aus einer CD-Beschreibung und aus 1 bis n Tracks
 * 
 * @author René Anderes
 * 
 */
public interface CompactDisc {

    /**
     * Setzen der CD-Beschreibung
     * 
     * @param description
     *            Beschreibung (mind. 2 Zeichen)
     */
    void setDescription(final String description);

    /**
     * Gibt die CD-Beschreibung zurück.
     * 
     * @return Beschreibung (nicht {@code null}
     */
    String getDescription();

    /**
     * Einen Track der CD setzen
     * 
     * @param track (darf nicht {@code null} sein)
     */
    void setTrack(final Track track);

    /**
     * Gibt den Track mit der entsprechenden Track-Nummer zurück.
     * 
     * @param trackNo
     *            Gewünschte Track-Nummer (trackNo > 0)
     * @return Track
     */
    Track getTrack(final int trackNo);

    /**
     * Gibt eine Liste mit allen Tracks zurück.
     * 
     * @return Trackliste (nicht {@code null})
     */
    List<Track> getAllTracks();

    /**
     * Gibt die Laufzeit der ganzen CD zurück.
     * 
     * @return Laufzeit der Compact-Disc
     */
    int getTotalTime();

}
