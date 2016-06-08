package org.anderes.edu.testing.junit.sample01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import static java.time.Month.*;

import org.junit.Test;


public class PersonTest {

    @Test
    public void shouldBeBirthdayInDecember() {
        
        // Given
        Person person = new Person("René");
        
        // when
        person.setBirthday(december(29, 1967));
        
        // then
        assertEquals("René", person.name);
        assertTrue(person.hasBirthday(DECEMBER.getValue()));
    }
    
    private LocalDate december(int day, int year) {
        return LocalDate.of(year, DECEMBER, day);
    }
}
