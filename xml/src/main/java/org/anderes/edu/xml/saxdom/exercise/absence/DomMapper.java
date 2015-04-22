package org.anderes.edu.xml.saxdom.exercise.absence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public abstract class DomMapper {

    public static Absence mapToAbsence(Document document) {
        final Absence absence = new AbsenceData();
        absence.setTitle(getText(document, "Title"));
        absence.setFirstname(getText(document, "Firstname"));
        absence.setLastname(getText(document, "Lastname"));
        absence.setPersonalnr(getText(document, "PersonalNr"));
        absence.setDivision(getText(document, "Division"));
        absence.setPeriod(getText(document, "Period"));
        absence.setAbsenceDate(getCalendar(document, "AbsenceDate"));
        return absence;
    }

    private static String getText(final Document document, final String element) {
        final NodeList ndList = document.getElementsByTagName(element);
        return ndList.item(0).getTextContent();
    }
    
    private static Calendar getCalendar(final Document document, final String element) {
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
