package org.anderes.edu.xml.saxdom;

import static java.time.Month.FEBRUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.anderes.edu.xml.saxdom.exercise.absence.Absence;
import org.anderes.edu.xml.saxdom.exercise.absence.DomMapper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

public class DomReaderAbsenceTest {

    private final String xsdPath = "/org/anderes/edu/xml/saxdom/absence/Absence.xsd";
    
	@Test
	public void shouldBeReadTheXMLFile() throws Exception {
		
	    final Document document = DomReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence.xml", xsdPath);
		final Absence absence = DomMapper.mapToAbsence(document);
		
		assertThat(absence.getTitle(), is("Herr"));
		assertThat(absence.getFirstname(), is("Peter"));
		assertThat(absence.getLastname(), is("Schreiner"));
		assertThat(absence.getPersonalNr(), is("A-123-8"));
		assertThat(absence.getDivision(), is("Entwicklung"));
		assertThat(absence.getPeriod(), is("Ganzer Tag"));
		assertThat(absence.getDate(), is(LocalDate.of(2014, FEBRUARY, 21)));
		assertThat(absence.getComment().isPresent(), is(false));
	}

	@Test(expected = IllegalArgumentException.class)
    public void shouldBeReadAIllegalArgumentException() throws Exception {
	    DomReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence.xml", "");
	}
	
	@Test(expected = SAXParseException.class)
    public void shouldBeReadAException() throws Exception {
        DomReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence_NotValid.xml", xsdPath);
    }
}
