package org.anderes.logging.solution;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class AppTest {
    
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void checkMainMethode() {
        exit.expectSystemExit();
        App.main(new String[] { "target/classes/data/measured_values.txt" }); 
    }
    
    @Test
    public void checkMainMethodeWithoutArgs() {
        exit.expectSystemExitWithStatus(1);
        App.main(new String[] { }); 
    }

    @Test
    public void checkMainMethodeWithoutWrongArgs() {
        exit.expectSystemExitWithStatus(2);
        App.main(new String[] { "wrong_path" }); 
    }
}
