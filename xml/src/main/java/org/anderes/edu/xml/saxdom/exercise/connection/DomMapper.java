package org.anderes.edu.xml.saxdom.exercise.connection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DomMapper {

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    
    public static Connection mapToConnection(final Document document) {
        Validate.notNull(document);
        
        final Connection connection = new Connection();
        connection.setDate(LocalDate.parse(getText(document, "Datum").get(), dateFormatter));
        mapFromLocality(document, connection.getFrom());
        mapToLocality(document, connection.getTo());
        connection.setTravelWith(getText(document, "ReiseMit").get());
        connection.getAllocation().setFirstClass(Integer.parseInt(getText(document, "ErsteKlasse").get()));
        connection.getAllocation().setSecondClass(Integer.parseInt(getText(document, "ZweiteKlasse").get()));
        final Optional<String> comment = getText(document, "Bemerkung");
        if (comment.isPresent()) {
            connection.setComment(comment.get());
        }
        return connection;
    }

    private static Optional<String> getText(final Document document, final String element) {
        final NodeList ndList = document.getElementsByTagName(element);
        if (ndList.getLength() == 0) {
            return Optional.empty();
        }
        return Optional.of(ndList.item(0).getTextContent());
    }
    
    private static void mapFromLocality(final Document document, final Locality fromLocality) {
        final NodeList ndList = document.getElementsByTagName("Von").item(0).getChildNodes();
        fillLocality(fromLocality, ndList);
    }
    
    private static void mapToLocality(final Document document, final Locality toLocality) {
        final NodeList ndList = document.getElementsByTagName("Nach").item(0).getChildNodes();
        fillLocality(toLocality, ndList);
    }

    private static void fillLocality(final Locality locality, final NodeList ndList) {
        for (int c = 0; c < ndList.getLength(); c++) {
            if (ndList.item(c).getNodeName().equals("Bahnhof")) {
                locality.setStation(ndList.item(c).getTextContent());
            } else if (ndList.item(c).getNodeName().equals("Zeit")) {
                locality.setTime(LocalTime.parse(ndList.item(c).getTextContent(), timeFormatter));
            } else if (ndList.item(c).getNodeName().equals("Gleis")) {
                locality.setPlatform(Integer.parseInt(ndList.item(c).getTextContent()));
            }
        }
    }
}
