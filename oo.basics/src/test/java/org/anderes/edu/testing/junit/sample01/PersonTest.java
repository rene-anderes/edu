package org.anderes.edu.testing.junit.sample01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;


public class PersonTest {

    @Test
    public void test() {
        Person person = new Person("René");
        person.setBirthday(december(29, 1967));
        assertEquals("René", person.name);
        assertTrue(person.hasBirthday(Month.DECEMBER.getValue()));
    }
    
    private LocalDate december(int day, int year) {
        return LocalDate.of(year, Month.DECEMBER, day);
    }
}
