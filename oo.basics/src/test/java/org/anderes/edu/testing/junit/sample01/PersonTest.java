package org.anderes.edu.testing.junit.sample01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import static java.time.Month.*;

import org.junit.Test;


public class PersonTest {

    @Test
    public void shouldBeBirthdayInDecember() {
        
        // given
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
    
    @Test
    public void shouldBeFindAlias() {
        
        // given
        Person person = new Person("Bill");
        
        // when
        person.addAlias("oncle sam");
        
        // then
        assertThat(person.hasAlias(), is(true));
        assertThat(person.isAlias("oncle sam"), is(true));
    }
    
    @Test
    public void shouldBeFindNoAlias() {
        
        // given
        Person person = new Person("Bill");
        person.addAlias("oncle sam");
        
        // when
        person.removeAlias("oncle sam");
        
        // then
        assertThat(person.hasAlias(), is(false));
        assertThat(person.isAlias("oncle sam"), is(false));
        
    }
}
