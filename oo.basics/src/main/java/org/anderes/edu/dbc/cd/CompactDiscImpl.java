package org.anderes.edu.dbc.cd;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * CD Implementation mit Design by Contract
 * 
 * @author René Anderes
 */
public class CompactDiscImpl implements CompactDisc {

    private String description;
    private TreeMap<Integer, Track> tracks = new TreeMap<Integer, Track>();

    @Override
    public void setDescription(final String description) {
	if (description == null) {
	    throw new NullPointerException("Parameter ist null");
	}
	if (description.isEmpty() || description.length() > 15) {
	    throw new IllegalArgumentException("Länge des Parameters ist falsch.");
	}
	this.description = description;
    }

    @Override
    public String getDescription() {
	return description;
    }

    @Override
    public void setTrack(final Track track) {
	if (track == null) {
	    throw new NullPointerException("Parameter ist null");
	}
	tracks.put(track.getTrackNo(), track);
    }

    @Override
    public Track getTrack(int trackNo) {
	if (tracks.containsKey(trackNo)) {
	    return tracks.get(trackNo);
	}
	return Track.NULL;
    }

    @Override
    public List<Track> getAllTracks() {
	return new ArrayList<Track>(tracks.values());
    }

    @Override
    public int getTotalTime() {
	int totaltime = 0;
	for (Track t : tracks.values()) {
	    totaltime += t.getTime();
	}
	return totaltime;
    }
}
