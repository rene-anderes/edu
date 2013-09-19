package org.anderes.edu.dbc.cd;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse für den Test eines Tracks
 * 
 * @author René Anderes
 *
 */
public class TrackTest {

    private Track track = new TrackImpl();
    
    @Before
    public void setUp() throws Exception {
	track.setDescription("Track eins");
	track.setTime(5);
	track.setTrackNo(1);
    }

    /**
     * Einfache Überprüfung via Getter
     */
    @Test
    public void shouldBeComplete() {
	assertEquals("Track-Beschreibung ist falsch!", "Track eins", track.getDescription());
	assertEquals("Track-Zeit ist falsch!", 5, track.getTime());
	assertEquals("Tracknummer ist falsch!", 1, track.getTrackNo());
    }
    
    @Test (expected = NullPointerException.class)
    public void descriptionNullPointerException() {
	track.setDescription(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void descriptionToShortException() {
	track.setDescription("");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void descriptionToLongException() {
	track.setDescription("Der Titel kann auch viel zu lang sein was nicht OK ist!");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void timeIsNotOkException() {
	track.setTime(0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void trackIsNotOkException() {
	track.setTrackNo(0);
    }
}
