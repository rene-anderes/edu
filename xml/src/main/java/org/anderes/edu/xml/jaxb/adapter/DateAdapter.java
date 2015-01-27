package org.anderes.edu.xml.jaxb.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String print(LocalDate date) {
        return dateFormat.format(date);
    }

    public static LocalDate parse(String dateString) {
        return LocalDate.parse(dateString, dateFormat);
    }

}