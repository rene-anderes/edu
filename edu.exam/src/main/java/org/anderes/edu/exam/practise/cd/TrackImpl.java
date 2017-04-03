package org.anderes.edu.exam.practise.cd;

public class TrackImpl implements Track {

    private String description;
    private int time;
    private int number;

    @Override
    public void setTrackNo(int number) {
	this.number = number;
    }

    @Override
    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public void setTime(int time) {
	this.time = time;
    }

    @Override
    public int getTime() {
	return time;
    }

    @Override
    public String getDescription() {
	return description;
    }

    @Override
    public int getTrackNo() {
	return number;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + number;
	result = prime * result + time;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TrackImpl other = (TrackImpl) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (number != other.number)
	    return false;
	if (time != other.time)
	    return false;
	return true;
    }

}
