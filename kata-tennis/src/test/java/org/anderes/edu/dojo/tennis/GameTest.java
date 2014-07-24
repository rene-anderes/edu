package org.anderes.edu.dojo.tennis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.anderes.edu.dojo.tennis.Game;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game game;
    
    @Before
    public void setup() {
        game = new Game("Roger Federer", "Raphael Nadal");
    }
    
    @Test
    public void shouldBeRapahelWin() {
        game.playerTwoWin();
        assertThat(game.score(), is("0:15"));
        game.playerTwoWin();
        assertThat(game.score(), is("0:30"));
        game.playerTwoWin();
        assertThat(game.score(), is("0:40"));
        game.playerTwoWin();
        assertThat(game.win(), is("Raphael Nadal"));
    }
    
    @Test(expected = IllegalStateException.class)
    public void shouldBeException() {
        game.playerTwoWin();
        assertThat(game.score(), is("0:15"));
        game.playerTwoWin();
        assertThat(game.score(), is("0:30"));
        game.playerTwoWin();
        assertThat(game.score(), is("0:40"));
        game.playerTwoWin();
        assertThat(game.win(), is("Raphael Nadal"));
        game.playerTwoWin();
    }
    
    @Test
    public void shouldBeDeuce() {
        game.playerTwoWin();
        assertThat(game.score(), is("0:15"));
        game.playerOneWin();
        assertThat(game.score(), is("15:15"));
        game.playerOneWin();
        assertThat(game.score(), is("30:15"));
        game.playerOneWin();
        assertThat(game.score(), is("40:15"));
        game.playerTwoWin();
        assertThat(game.score(), is("40:30"));
        game.playerTwoWin();
        assertThat(game.score(), is("deuce"));
        game.playerTwoWin();
        assertThat(game.score(), is("40:A"));
        game.playerOneWin();
        assertThat(game.score(), is("deuce"));
        game.playerOneWin();
        assertThat(game.score(), is("A:40"));
        game.playerOneWin();
        assertThat(game.win(), is("Roger Federer"));
    }
    
    @Test public void shouldBeToString() {
        assertThat(game.toString(), is("Roger Federer gegen Raphael Nadal, Spielstand 0:0"));
    }
}
