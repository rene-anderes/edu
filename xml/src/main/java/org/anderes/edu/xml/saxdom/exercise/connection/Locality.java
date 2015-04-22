package org.anderes.edu.xml.saxdom.exercise.connection;

import java.time.LocalTime;

public class Locality {

    private String station;
    private LocalTime time;
    private int platform;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getPlatform() {
        return platform;
    }

}
