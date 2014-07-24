package org.anderes.edu.dojo.tennis;


public class Game {
    
    private final Player playerOne;
    private final Player playerTwo;
    
    public Game(String namePlayerOne, String namePlayerTwo) {
        playerOne = new Player(namePlayerOne);
        playerTwo = new Player(namePlayerTwo);
    }

    public void playerOneWin() {
        playerOne.win();
        playerTwo.lose();
        setDeuceIfNecessary();
    }
    
    public void playerTwoWin() {
        playerOne.lose();
        playerTwo.win();
        setDeuceIfNecessary();
    }

    public String score() {
        if (isDeuce()) {
            return playerOne.point();
        }
        return playerOne.point() + ":" + playerTwo.point();
    }

    public String win() {
        if (playerOne.hasWin()) {
            return playerOne.getName();
        }
        return playerTwo.getName();
    }
    
    private void setDeuceIfNecessary() {
        if (playerOne.isForty() && playerTwo.isForty()) {
            playerOne.setIsDeuce();
            playerTwo.setIsDeuce();
        }
    }
    
    private boolean isDeuce() {
        return playerOne.isDeuce() && playerTwo.isDeuce();
    }

    @Override
    public String toString() {
        return String.format("%s gegen %s, Spielstand %s", playerOne.getName(), playerTwo.getName(), score());
    }
    
    
}
