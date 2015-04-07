package org.anderes.edu.dojo.java8.news.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Offer {

    private final String description;
    private final LocalDate validFrom;
    private final LocalDate validTo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public Offer(final String description, final String validFromString, final String validToString) {
        this.description = description;
        validFrom = LocalDate.parse(validFromString, formatter);
        validTo = LocalDate.parse(validToString, formatter);
    }
    
    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    
    public long getDays() {
        return ChronoUnit.DAYS.between(validFrom, validTo);
    }
    
    public String getDescription() {
        return description;
    }
}
