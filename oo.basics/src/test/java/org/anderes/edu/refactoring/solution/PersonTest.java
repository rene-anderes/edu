package org.anderes.edu.refactoring.solution;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static org.anderes.edu.refactoring.solution.Person.AddressType.BUSINESS;
import static org.anderes.edu.refactoring.solution.Person.AddressType.PRIVATE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse für {@link Person}
 * 
 * @author René Anderes
 */
public class PersonTest {

	private Person person = new Person("Herr Bär");
	
	@Before
	public void setUp() throws Exception {
		person.addAddress(PRIVATE, "Aufbinderstrasse 3, 9999 Entenhausen");
		person.setBirthday(december(29, 1967));
	}
	
	private LocalDate december(int dayOfMonth, int year) {
        return LocalDate.of(year, DECEMBER, dayOfMonth);
    }
	
	@Test
	public void person() {
		assertNotNull(person);
		assertEquals("Herr Bär", person.getName());
		assertNotNull(person.getAddress(PRIVATE));
		assertNull(person.getAddress(BUSINESS));
		assertEquals("Aufbinderstrasse 3, 9999 Entenhausen", person.getAddress(PRIVATE));
		assertEquals(december(29, 1967), person.getBirthday());
		assertThat(person.toString(), is(expectedToString()));
        assertThat(person.hasBirthday(DECEMBER), is(true));
        assertThat(person.hasBirthday(JANUARY), is(false));
        assertThat(person.isFullAge(), is(true));
	}
	
	private String expectedToString() {
        String toString = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + '\n';
        toString += "Name: Herr Bär" + '\n';
        toString += "PRIVATE: Aufbinderstrasse 3, 9999 Entenhausen" + '\n';
        toString += "Geburtstag: 29.12.1967" + '\n';
        toString += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"  + '\n';
        return toString;
    }
}
