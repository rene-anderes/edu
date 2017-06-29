package org.anderes.edu.testing.junit.sample01;

import static org.anderes.edu.testing.junit.sample01.DoorState.CLOSE;
import static org.anderes.edu.testing.junit.sample01.DoorState.OPEN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CarTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    
    private Car carUnderTest;
    private final Integer numberOfDoor;
    
    @Before
    public void setup() {
        carUnderTest = new Car(numberOfDoor);
    }

    public CarTest(Integer number) {
        numberOfDoor = number;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> collection = new ArrayList<>(3); 
        collection.add(new Integer[] { 2 });
        collection.add(new Integer[] { 3 });
        collection.add(new Integer[] { 4 });
        return collection;
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
        thrown.expectMessage(is("Die Türnummer muss grösser als 0 und nicht grösser als " + numberOfDoor + " sein."));
        
        // when
        carUnderTest.isDoorClose(8);
    }
}
