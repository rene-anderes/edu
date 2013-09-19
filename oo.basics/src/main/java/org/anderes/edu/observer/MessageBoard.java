package org.anderes.edu.observer;

import java.util.Observable;

public class MessageBoard extends Observable {

    public void addNewMessage(String message) {
	setChanged();
	notifyObservers(message);
    }

}
