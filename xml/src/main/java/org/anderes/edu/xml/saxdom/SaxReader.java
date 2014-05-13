package org.anderes.edu.xml.saxdom;

import java.io.InputStream;
import java.util.Calendar;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class SaxReader implements Absence {

	private String firstname;
	private String lastname;
	private String personalnr;
	private String division;
	private String title;
	private String period;
	private Calendar absenceDate;
	
	private SaxReader() {
	}

	public static Absence build() {
		return new SaxReader();
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
	public String getPersonalNr() {
		return personalnr;
	}

	@Override
	public String getDivision() {
		return division;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getPeriod() {
		return period;
	}

	@Override
	public Calendar getDate() {
		return absenceDate;
	}

	@Override
	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	@Override
	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	@Override
	public void setPersonalnr(final String personalnr) {
		this.personalnr = personalnr;
	}

	@Override
	public void setDivision(final String division) {
		this.division = division;
	}

	@Override
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public void setPeriod(final String period) {
		this.period = period;
	}

	@Override
	public void setAbsenceDate(Calendar absenceDate) {
		this.absenceDate = absenceDate;
	}

	@Override
	public void parseFile(final String file) throws Exception {
		final XMLReader xmlReader = XMLReaderFactory.createXMLReader();

		final InputStream is = getClass().getResourceAsStream(file);
		final InputSource inputSource = new InputSource(is);

		xmlReader.setContentHandler(new SaxAbsenceHandler(this));

		xmlReader.parse(inputSource);
	}
}
