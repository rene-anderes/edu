package org.anderes.edu.dbc.cd;

/**
 * Ein einfacher Datencontainer für einen Track einer CD
 * 
 * @author René Anderes
 * 
 */
public interface Track {
    /**
     * Setzen der Track-Nummer
     * 
     * @param number
     *            Nummer (number > 0)
     */
    void setTrackNo(int number);

    /**
     * Setzen des Titels bzw. Beschreibung
     * 
     * @param description
     *            Beschreibung (mind. 2 Zeichen)
     */
    void setDescription(final String description);

    /**
     * Setzen der Laufzeit des Tracks
     * 
     * @param time
     *            Laufzeit (time > 0)
     */
    void setTime(final int time);

    /**
     * Gibt die Laufzeit des Tracks zurück.
     * 
     * @return Laufzeit
     */
    int getTime();

    /**
     * Gibt die Beschreibung des Tracks zurück.
     * 
     * @return Beschreibung (nicht {@code null})
     */
    String getDescription();

    /**
     * Gibt die Track-Nummer zurück.
     * 
     * @return Tracknummer
     */
    int getTrackNo();

    boolean isNull();

    public static final Track NULL = new Track() {

	@Override
	public void setTrackNo(int number) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public void setDescription(String description) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public void setTime(int time) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public int getTime() {
	    return 0;
	}

	@Override
	public String getDescription() {
	    return "";
	}

	@Override
	public int getTrackNo() {
	    return 0;
	}

	@Override
	public boolean isNull() {
	    return true;
	}
    };
}
