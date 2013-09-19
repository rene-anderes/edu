package org.anderes.edu.refactoring.initial;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
		person.getAddresses().put("privat", "Aufbinderstrasse 3, 9999 Entenhausen");
		person.setBD(december(29, 1967));
	}
	
	private Date december(int day, int year) {
		Calendar calendar = new GregorianCalendar(year, Calendar.DECEMBER, day);
		return calendar.getTime();
	}
	
	@Test
	public void person() {
		assertNotNull(person);
		assertEquals("Herr Bär", person.name);
		assertNotNull(person.getAddresses());
		assertNotNull(person.getAddresses().get("privat"));
		assertEquals("Aufbinderstrasse 3, 9999 Entenhausen", person.getAddresses().get("privat"));
		assertEquals(december(29, 1967), person.getBD());
		List<String> printData = person.printData();
		assertEquals(3, printData.size());
		assertEquals("Name: Herr Bär", printData.get(0));
		assertEquals("Adresse: Aufbinderstrasse 3, 9999 Entenhausen", printData.get(1));
		assertEquals("Geburtstag: 29.12.1967", printData.get(2));
		assertTrue(person.hasBirthday(Calendar.DECEMBER));
		assertFalse(person.hasBirthday(Calendar.JANUARY));
	}
	
	
}
