package org.anderes.edu.xml.saxdom;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import static java.util.Calendar.*;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.xml.sax.SAXParseException;

public class SaxReaderTest {

	@Test
	public void shouldBeReadTheXMLFile() throws Exception {
		final Absence absence = SaxReader.parseFile("/org/anderes/edu/xml/saxdom/Absence.xml", "/org/anderes/edu/xml/saxdom/Absence.xsd");
		
		assertThat(absence.getTitle(), is("Herr"));
		assertThat(absence.getFirstname(), is("Peter"));
		assertThat(absence.getLastname(), is("Schreiner"));
		assertThat(absence.getPersonalNr(), is("A-123-8"));
		assertThat(absence.getDivision(), is("Entwicklung"));
		assertThat(absence.getPeriod(), is("Ganzer Tag"));
		assertThat(absence.getDate(), is(truncateDate(2014, FEBRUARY, 21)));
		
	}
	@Test(expected = IllegalArgumentException.class)
    public void shouldBeReadAIllegalArgumentException() throws Exception {
	    SaxReader.parseFile("/org/anderes/edu/xml/saxdom/Absence.xml", "");
    }
    
    @Test(expected = SAXParseException.class)
    public void shouldBeReadAException() throws Exception {
        SaxReader.parseFile("/org/anderes/edu/xml/saxdom/Absence_NotValid.xml", "/org/anderes/edu/xml/saxdom/Absence.xsd");
    }
    
	private Calendar truncateDate(final int year, final int month, final int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH);
	}
}
