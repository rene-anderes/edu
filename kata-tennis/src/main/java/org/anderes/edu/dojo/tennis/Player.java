package org.anderes.edu.dojo.tennis;

import java.util.HashMap;
import java.util.Map;

import static org.anderes.edu.dojo.tennis.Player.State.*;

public class Player {
    
    public enum State { 
        LOVE("0"), FIFTEEN("15"), THIRTY("30"), FORTY("40"), DEUCE("deuce"), 
        ADVANTAGE("A"), WITHOUADVANTAGE("40"), LOSE("loose"), WIN("win");
        
        private String value;
        private State(final String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    
    private final static Map<State, State> transitionWin;
    private final static Map<State, State> transitionLose;
    private State activeState;
    private String name;

    static {
        transitionWin = new HashMap<>(7);
        transitionWin.put(LOVE, FIFTEEN);
        transitionWin.put(FIFTEEN, THIRTY);
        transitionWin.put(THIRTY, FORTY);
        transitionWin.put(FORTY, WIN);
        transitionWin.put(DEUCE, ADVANTAGE);
        transitionWin.put(WITHOUADVANTAGE, DEUCE);
        transitionWin.put(ADVANTAGE, WIN);
        
        transitionLose = new HashMap<>(7);
        transitionLose.put(LOVE, LOVE);
        transitionLose.put(FIFTEEN, FIFTEEN);
        transitionLose.put(THIRTY, THIRTY);
        transitionLose.put(FORTY, FORTY);
        transitionLose.put(DEUCE, WITHOUADVANTAGE);
        transitionLose.put(ADVANTAGE, DEUCE);
        transitionLose.put(WITHOUADVANTAGE, LOSE);
    }
    
    public Player(final String name) {
        this.name = name;
        activeState = LOVE;
    }

    public String getName() {
        return name;
    }
    
    public String point() {
        return activeState.getValue();
    }

    public boolean hasWin() {
        return activeState == WIN;
    }

    public void lose() {
        if (activeState == WIN || activeState == LOSE) {
            throw new IllegalStateException();
        }
        activeState = transitionLose.get(activeState);
    }

    public void win() {
        if (activeState == WIN || activeState == LOSE) {
            throw new IllegalStateException();
        }
        activeState = transitionWin.get(activeState);
    }
    
    public void setIsDeuce() {
        activeState = DEUCE;
    }

    public boolean isDeuce() {
        return activeState == DEUCE;
    }
    
    public boolean isForty() {
        return activeState == FORTY;
    }
}
