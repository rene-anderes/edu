package org.anderes.edu.xml.jaxb.modeldriven.withadapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class OrderDateAdapter extends XmlAdapter<String, LocalDate> {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    @Override
    public LocalDate unmarshal(String dateAsString) throws Exception {
        return LocalDate.parse(dateAsString, dateFormat);
    }

    @Override
    public String marshal(LocalDate date) throws Exception {
        return dateFormat.format(date);
    }
}
