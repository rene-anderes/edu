package org.anderes.edu.dojo.bowling;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game game;
    
    @Before
    public void setup() {
        game = new Game();
    }

    @Test
    public void oneRoll() {
        game.addRoll(1);

        assertThat(game.getTotalScore(), is(1));
        assertThat(game.getFrames(), is(notNullValue()));
        assertThat(game.getFrames().size(), is(1));
        assertThat(game.getFrames().get(1).getScore(), is(1));
        assertThat(game.getFrames().get(1).getPinsRolled().length, is(1));
        assertThat(game.getFrames().get(1).getPinsRolled()[0], is(1));
    }
    
    @Test
    public void twoRolls() {
        game.addRoll(1);
        game.addRoll(4);
        
        assertThat(game.getTotalScore(), is(5));
        assertThat(game.getFrames().size(), is(1));
        assertThat(game.getFrames().get(1).getPinsRolled().length, is(2));
        assertThat(game.getFrames().get(1).getPinsRolled()[0], is(1));
        assertThat(game.getFrames().get(1).getPinsRolled()[1], is(4));
    }
    
    @Test
    public void shouldBeSpare() {
        game.addRoll(6);
        game.addRoll(4);
        game.addRoll(5);
        
        assertThat(game.getTotalScore(), is(20));
        assertThat(game.getFrames().get(1).getScore(), is(15));
        assertThat(game.getFrames().get(1).getPinsRolled()[0], is(6));
        assertThat(game.getFrames().get(1).getPinsRolled()[1], is(4));
    }
    
    @Test
    public void shouldBeSpareTwo() {
        game.addRoll(6);
        game.addRoll(4);
        game.addRoll(5);
        game.addRoll(5);
        
        assertThat(game.getTotalScore(), is(25));
        assertThat(game.getFrames().get(1).getScore(), is(15));
        assertThat(game.getFrames().get(1).getPinsRolled()[0], is(6));
        assertThat(game.getFrames().get(1).getPinsRolled()[1], is(4));
        assertThat(game.getFrames().get(2).getScore(), is(10));
        assertThat(game.getFrames().get(2).getPinsRolled()[0], is(5));
        assertThat(game.getFrames().get(2).getPinsRolled()[1], is(5));
    }
    
    @Test
    public void shouldBeCorrectToString() {
        String expectedToString = "([6,4],15), ([5,5],10)";
        game.addRoll(6);
        game.addRoll(4);
        game.addRoll(5);
        game.addRoll(5);
        
        assertThat(game.toString(), is(expectedToString));
    }
    
    @Test
    public void shouldBeSpike() {
        game.addRoll(10);
        game.addRoll(1);
        
        assertThat(game.getTotalScore(), is(12));
        assertThat(game.getFrames().get(1).getScore(), is(11));
        assertThat(game.getFrames().get(1).getPinsRolled()[0], is(10));
    }
    
    @Test
    public void shouldBeSpikeTwo() {
        game.addRoll(10);
        game.addRoll(1);
        game.addRoll(6);
        
        assertThat(game.getTotalScore(), is(24));
        assertThat(game.getFrames().get(1).getScore(), is(17));
        assertThat(game.getFrames().get(2).getScore(), is(7));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldBeWrongRollValue() {
        game.addRoll(-2);
    }
    
    @Test(expected = IllegalStateException.class)
    public void bowlingGameTest() {
        final Game game = new Game();
        for (int i=0; i < 20; i++) {
            game.addRoll(0);
        }
        assertThat(game.getTotalScore(), is(0));
        assertThat(game.isOver(), is(true));
        game.addRoll(1);
    }
}
