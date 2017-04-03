package org.anderes.edu.exam.practise.cd;
import static org.junit.Assert.*;

import org.junit.Test;


public class CompactDiscTest {

    @Test
    public void basis() {
	Track track01 = new TrackImpl();
	track01.setDescription("Track 1");
	track01.setTime(5);
	track01.setTrackNo(1);
	assertEquals("Track 1", track01.getDescription());
	assertEquals(5, track01.getTime());
	assertEquals(1, track01.getTrackNo());
	
	CompactDisc cd = new CompactDiscImpl();
	cd.setDescription("CD 1");
	assertEquals("CD 1", cd.getDescription());
	
	assertNotNull(cd.getAllTracks());
	cd.setTrack(track01);
	assertEquals(1, cd.getAllTracks().size());
	
	Track track02 = new TrackImpl();
	track02.setDescription("Track 2");
	track02.setTime(4);
	track02.setTrackNo(2);
	cd.setTrack(track02);
	assertEquals(9, cd.getTotalTime());
	
	assertEquals("Track 1", cd.getTrack(1).getDescription());
	assertEquals(track01, cd.getTrack(1));
    }
}
