package org.anderes.edu.exam.practise.cd;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 
 * @author René Anderes
 * @version 1.0
 *
 */
public class CompactDiscImpl implements CompactDisc {

    private String description;
    private TreeMap<Integer, Track> tracks = new TreeMap<Integer, Track>();

    @Override
    public void setDescription(String description) {
	this.description = description;
    }
 
    @Override
    public String getDescription() {
	return description;
    }

    @Override
    public void setTrack(Track track) {
	tracks.put(track.getTrackNo(), track);
    }

    @Override
    public Track getTrack(int trackNo) {
	return tracks.get(trackNo);
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
