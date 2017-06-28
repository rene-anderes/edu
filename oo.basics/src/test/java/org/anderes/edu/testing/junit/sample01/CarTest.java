package org.anderes.edu.testing.junit.sample01;

import static org.anderes.edu.testing.junit.sample01.DoorState.CLOSE;
import static org.anderes.edu.testing.junit.sample01.DoorState.OPEN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CarTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    
    private Car carUnderTest;
    
    @Before
    public void setup() {
        carUnderTest = new Car(4);
    }
    
    @Test
    public void setDoorStateOpen() {
        
        // when
        carUnderTest.setDoorState(1, OPEN);
        
        // then
        assertThat(carUnderTest.isDoorOpen(1), is(true));
        assertThat(carUnderTest.isDoorClose(1), is(false));
        
    }
    
    @Test
    public void setDoorStateClose() {
        
        // when
        carUnderTest.setDoorState(1, CLOSE);
        
        // then
        assertThat(carUnderTest.isDoorOpen(1), is(false));
        assertThat(carUnderTest.isDoorClose(1), is(true));
        
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWrongDoorNumber() {
        
        // when
        carUnderTest.setDoorState(8, CLOSE);
        
        // then Exception
    }
    
    @Test
    public void isDoorOpenThrowException() {
        
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(is("Die Türnummer muss grösser als 0 und nicht grösser als 4 sein."));
        
        // when
        carUnderTest.isDoorClose(8);
    }
}
