package org.anderes.edu.testing.junit.sample01;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.anderes.edu.testing.junit.sample01.Person;
import org.junit.Test;


public class PersonTest {

    @Test
    public void test() {
        Person person = new Person("Ren�");
        person.setBirthday(december(29, 1967));
        assertEquals("Ren�", person.name);
        assertTrue(person.hasBirthday(Calendar.DECEMBER));
    }
    
    private Date december(int day, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, Calendar.DECEMBER, day);
        Date d = c.getTime(); 
        return d;
    }
}
