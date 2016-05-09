package org.anderes.edu.xml.saxdom.exercise.absence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public abstract class DomMapper {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public static Absence mapToAbsence(final Document document) {
        Validate.notNull(document);
        
        final AbsenceData absence = new AbsenceData();
        absence.setTitle(getText(document, "Title").orElse(null));
        absence.setFirstname(getText(document, "Firstname").orElse(null));
        absence.setLastname(getText(document, "Lastname").orElse(null));
        absence.setPersonalnr(getText(document, "PersonalNr").orElse(null));
        absence.setDivision(getText(document, "Division").orElse(null));
        absence.setPeriod(getText(document, "Period").orElse(null));
        absence.setAbsenceDate(getDate(document, "AbsenceDate"));
        absence.setComment(getText(document, "Note").orElse(null));
        return absence;
    }

    private static Optional<String> getText(final Document document, final String element) {
        final NodeList ndList = document.getElementsByTagName(element);
        if (ndList.item(0) == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(ndList.item(0).getTextContent());
    }
    
    private static LocalDate getDate(final Document document, final String element){
        try {
            return LocalDate.parse(getText(document, element).orElse(""), FORMATTER);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }
}
