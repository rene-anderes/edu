package org.anderes.edu.dojo.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Frame {
    
    private List<Integer> pins = new ArrayList<>(2);
    private int spareOrStrikePoints = 0;
    
    /**
     * Punktzahl nur dieses Frame
     */
    public int getScore() {
        return getPinsSum() + spareOrStrikePoints;
    }
    
    private int getPinsSum() {
        return pins.stream().reduce(0, Integer::sum).intValue();
    }
    
    public int[] getPinsRolled() { 
        return pins.stream().mapToInt(i -> i).toArray();
    }
    
    public String toString() { 
        return String.format("([%s],%s)", pins.stream().map(v -> v.toString()).collect(Collectors.joining(",")), getScore());
    }
    
    public void addRoll(int pins) {
        this.pins.add(pins);
    }
    
    public boolean isComplete() {
        return isStrike() || pins.size() == 2;
    }
    
    public void addSpareSpikePoints(final int points) {
        if(getPinsSum() != 10) {
            throw new IllegalStateException("Das Frame ist kein Spare oder Spike");
        }
        spareOrStrikePoints = points;
    }
    
    public boolean isStrike() {
        if (pins.size() == 0) {
            return false;
        }
        return pins.get(0) == 10;
    }

    public boolean isSpare() {
        if (pins.size() == 0) {
            return false;
        }
        return !isStrike() && getPinsSum() == 10;
    }
}
