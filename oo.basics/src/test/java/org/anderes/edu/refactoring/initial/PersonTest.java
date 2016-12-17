package org.anderes.edu.refactoring.initial;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
		person.getAddresses().put("Privat", "Aufbinderstrasse 3, 9999 Entenhausen");
		person.setBD(december(29, 1967));
	}
	
	private LocalDate december(int dayOfMonth, int year) {
		return LocalDate.of(year, DECEMBER, dayOfMonth);
	}
	
	@Test
	public void person() {
		assertNotNull(person);
		assertEquals("Herr Bär", person.name);
		assertNotNull(person.getAddresses());
		assertNotNull(person.getAddresses().get("Privat"));
		assertEquals("Aufbinderstrasse 3, 9999 Entenhausen", person.getAddresses().get("Privat"));
		assertEquals(december(29, 1967), person.getBD());
		assertThat(person.toString(), is(expectedToString()));
		assertThat(person.hasBirthday(DECEMBER), is(true));
		assertThat(person.hasBirthday(JANUARY), is(false));
		assertThat(person.isFullAge(), is(true));
	}
	
	private String expectedToString() {
	    String toString = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + '\n';
	    toString += "Name: Herr Bär" + '\n';
	    toString += "Privat: Aufbinderstrasse 3, 9999 Entenhausen" + '\n';
	    toString += "Geburtstag: 29.12.1967" + '\n';
	    toString += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~"  + '\n';
	    return toString;
	}
	
}
