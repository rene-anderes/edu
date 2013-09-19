package org.anderes.edu.dbc.cd;

import static org.junit.Assert.*;

import org.anderes.edu.dbc.cd.CompactDisc;
import org.anderes.edu.dbc.cd.CompactDiscImpl;
import org.anderes.edu.dbc.cd.Track;
import org.anderes.edu.dbc.cd.TrackImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse die die Klasse CompactDisc testet.
 * 
 * @author René Anderes
 * 
 */
public class CompactDiscTest {

    private Track track01 = new TrackImpl();
    private Track track02 = new TrackImpl();
    private CompactDisc cd = new CompactDiscImpl();
    
    @Before
    public void setUp() throws Exception {
	track01.setDescription("Track eins");
	track01.setTime(5);
	track01.setTrackNo(1);
	
	track02.setDescription("Track zwei");
	track02.setTime(6);
	track02.setTrackNo(2);
	
	cd.setDescription("Titel der CD");
    }
    
    @Test
    public void shouldBeComplete() {
	assertEquals("CD-Titel ist falsch!", "Titel der CD", cd.getDescription());
    }
    
    @Test (expected = NullPointerException.class)
    public void titleIsNull() {
	cd.setDescription(null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void titleIsToLong() {
	cd.setDescription("Der Titel kann auch zu lang sein!");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void titleIsToShort() {
        cd.setDescription("");
    }
    
    @Test
    public void shouldBeAddTracks() {
	assertNotNull(cd.getAllTracks());
	assertEquals("Ungültige Länges der Trackliste!", 0, cd.getAllTracks().size());
	
	cd.setTrack(track01);
	assertEquals("Ein Track muss vorhanden sein!", 1, cd.getAllTracks().size());
	assertEquals(track01, cd.getTrack(1));

	cd.setTrack(track02);
	assertEquals("Zwei Tracks müssen vorhanden sein!", 2, cd.getAllTracks().size());
	assertEquals(track02, cd.getTrack(2));

	// Summe aller Zeiten
	assertEquals("Die Summe aller Tracks ist falsch!", 11, cd.getTotalTime());
    }
    
    @Test
    public void shouldBeNullObject() {
	assertTrue(cd.getTrack(5).isNull());
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void modifyDescriptionUnsupported() {
	Track track = cd.getTrack(5);
	track.setDescription("Track");
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void modifyTimeUnsupported() {
	Track track = cd.getTrack(5);
	track.setTime(3);
    }
    
    @Test (expected = UnsupportedOperationException.class)
    public void modifyTrackNoUnsupported() {
	Track track = cd.getTrack(5);
	track.setTrackNo(4);
    }
}
