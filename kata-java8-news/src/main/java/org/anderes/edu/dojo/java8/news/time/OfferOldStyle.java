package org.anderes.edu.dojo.java8.news.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OfferOldStyle {

    private final Date validFrom;
    private final Date validTo;
    private final String description;
    private final DateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");

    public OfferOldStyle(final String description, final String validFromString, final String validToString) {
        try {
            validFrom =  dateformat.parse(validFromString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Ungültiges Format für 'validFromString'");
        }
        try {
            validTo =  dateformat.parse(validToString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Ungültiges Format für 'validToString'");
        }
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public String getDescription() {
        return description;
    }

    public long getDays() {
        return (validTo.getTime() - validFrom.getTime()) / 1000 / 3600 / 24;
    }
}
