package org.anderes.edu.xml.saxdom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DomReader implements Absence {

	private String firstname;
	private String lastname;
	private String personalnr;
	private String division;
	private String title;
	private String period;
	private Calendar absenceDate;
	
	private DomReader() {
	}

	public static Absence build() {
		return new DomReader();
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
	public void setAbsenceDate(final Calendar absenceDate) {
		this.absenceDate = absenceDate;
	}

	@Override
	public void parseFile(final String file) throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder  = factory.newDocumentBuilder();
		final InputSource inputSource = new InputSource(getClass().getResourceAsStream(file));
		final Document document = builder.parse(inputSource);
		
		setTitle(getText(document, "Title"));
		setFirstname(getText(document, "Firstname"));
		setLastname(getText(document, "Lastname"));
		setPersonalnr(getText(document, "PersonalNr"));
		setDivision(getText(document, "Division"));
		setPeriod(getText(document, "Period"));
		setAbsenceDate(getCalendar(document, "AbsenceDate"));
	}
	
	private String getText(final Document document, final String element) {
		final NodeList ndList = document.getElementsByTagName(element);
		return ndList.item(0).getTextContent();
	}
	
	private Calendar getCalendar(final Document document, final String element) {
		final SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
		final Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateformat.parse(getText(document, element)));
		} catch (ParseException e) {
			// Nothing to do
		}
		return cal;
	}

}
