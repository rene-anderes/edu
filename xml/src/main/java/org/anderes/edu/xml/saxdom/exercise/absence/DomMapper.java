package org.anderes.edu.xml.saxdom.exercise.absence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public abstract class DomMapper {

    public static Absence mapToAbsence(final Document document) {
        Validate.notNull(document);
        
        final AbsenceData absence = new AbsenceData();
        absence.setTitle(getText(document, "Title").orElse(null));
        absence.setFirstname(getText(document, "Firstname").orElse(null));
        absence.setLastname(getText(document, "Lastname").orElse(null));
        absence.setPersonalnr(getText(document, "PersonalNr").orElse(null));
        absence.setDivision(getText(document, "Division").orElse(null));
        absence.setPeriod(getText(document, "Period").orElse(null));
        absence.setAbsenceDate(getCalendar(document, "AbsenceDate"));
        absence.setNote(getText(document, "Note").orElse(null));
        return absence;
    }

    private static Optional<String> getText(final Document document, final String element) {
        final NodeList ndList = document.getElementsByTagName(element);
        if (ndList.item(0) == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ndList.item(0).getTextContent());
    }
    
    private static Calendar getCalendar(final Document document, final String element) {
        final SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
        final Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateformat.parse(getText(document, element).orElse("")));
        } catch (ParseException e) {
            // Nothing to do
        }
        return cal;
    }
}
