package org.anderes.edu.xml.jaxb.adapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String marshal(LocalDate date) {
        return dateFormat.format(date);
    }

    public static LocalDate unmarshal(String dateString) {
        return LocalDate.parse(dateString, dateFormat);
    }

}