package org.anderes.edu.testing.junit.sample03;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.anderes.edu.testing.junit.sample03.Person;
import org.junit.Test;


public class PersonTest {

    @Test
    public void test() {
        Person person = new Person("René");
        person.setBirthday(december(29, 1967));
        assertEquals("René", person.name);
        assertTrue(person.hasBirthday(Calendar.DECEMBER));
        
//        assertFalse(person.hasBirthday(-1));
//        assertFalse(person.hasBirthday(12));
//        assertFalse(person.hasBirthday(Calendar.JANUARY));
    }
    
    private Date december(int day, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, day);
        Date d = c.getTime(); 
        return d;
    }
}
