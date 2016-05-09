package org.anderes.edu.xml.saxdom;

import static java.time.Month.FEBRUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.anderes.edu.xml.saxdom.exercise.absence.Absence;
import org.anderes.edu.xml.saxdom.exercise.absence.SaxAbsenceHandler;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

public class SaxReaderAbsenceTest {
    
    private final String xsdPath = "/org/anderes/edu/xml/saxdom/absence/Absence.xsd";
    private SaxAbsenceHandler contentHandler;
    
    @Before
    public void setup() {
        contentHandler = new SaxAbsenceHandler();
    }

	@Test
	public void shouldBeReadTheXMLFile() throws Exception {

		SaxReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence.xml", xsdPath, contentHandler);
		final Absence absence = contentHandler.getAbsence();
		
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
	    SaxReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence.xml", "", contentHandler);
    }
    
    @Test(expected = SAXParseException.class)
    public void shouldBeReadAException() throws Exception {
        SaxReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence_NotValid.xml", xsdPath, contentHandler);
    }
    
    @Test(expected = Exception.class)
    public void shouldBeReadAExceptionWrongCharacter() throws Exception {
        SaxReader.parseFile("/org/anderes/edu/xml/saxdom/absence/Absence_wrong_character.xml", xsdPath, contentHandler);
    }
}
