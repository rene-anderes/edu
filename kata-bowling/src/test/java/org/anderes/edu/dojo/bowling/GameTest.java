package org.anderes.edu.dojo.bowling;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void bowlingGameTest() {
        final Game game = new Game();
        for (int i=0; i<20; i++) {
            game.addRoll(0);
        }
        assertThat(game.getTotalScore(), is(0));
    }
}
