package org.anderes.edu.xml.saxdom.exercise.absence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SaxAbsenceHandler implements ContentHandler {

	private Absence absence = new AbsenceData();
	private String currentValue;
	
	public SaxAbsenceHandler() {
	}

	@Override
	public void setDocumentLocator(Locator locator) {
	}

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equals("Title")) {
			absence.setTitle(currentValue);
		}
		if (localName.equals("PersonalNr")) {
			absence.setPersonalnr(currentValue);
		}
		if (localName.equals("Firstname")) {
			absence.setFirstname(currentValue);
		}
		if (localName.equals("Lastname")) {
			absence.setLastname(currentValue);
		}
		if (localName.equals("Division")) {
			absence.setDivision(currentValue);
		}
		if (localName.equals("Period")) {
			absence.setPeriod(currentValue);
		}
		if (localName.equals("AbsenceDate")) {
			final SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
			final Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(dateformat.parse(currentValue));
			} catch (ParseException e) {
				// Nothing to do
			}
			absence.setAbsenceDate(cal);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		currentValue = new String(ch, start, length);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
	}

    public Absence getAbsence() {
        return absence;
    }
}
