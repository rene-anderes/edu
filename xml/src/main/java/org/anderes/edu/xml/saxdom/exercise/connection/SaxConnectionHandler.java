package org.anderes.edu.xml.saxdom.exercise.connection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SaxConnectionHandler implements ContentHandler {

    private enum Group { from, to };
    private final Connection connection = new Connection();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    private String currentValue;
    private Group group;
    
    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        // TODO Auto-generated method stub

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // TODO Auto-generated method stub

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals("Von")) {
            group = Group.from;
        } else if (localName.equals("Nach")) {
            group = Group.to;
        } 
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("Datum")) {
            connection.setDate(LocalDate.parse(currentValue, dateFormatter));
        }
        if (localName.equals("Bahnhof") && group.equals(Group.from)) {
            connection.getFrom().setStation(currentValue);
        }
        if (localName.equals("Zeit") && group.equals(Group.from)) {
            connection.getFrom().setTime(LocalTime.parse(currentValue, timeFormatter ));
        }
        if (localName.equals("Gleis") && group.equals(Group.from)) {
            connection.getFrom().setPlatform(Integer.parseInt(currentValue));
        }
        if (localName.equals("Bahnhof") && group.equals(Group.to)) {
            connection.getTo().setStation(currentValue);
        }
        if (localName.equals("Zeit") && group.equals(Group.to)) {
            connection.getTo().setTime(LocalTime.parse(currentValue, timeFormatter ));
        }
        if (localName.equals("Gleis") && group.equals(Group.to)) {
            connection.getTo().setPlatform(Integer.parseInt(currentValue));
        }
        if (localName.equals("ReiseMit")) {
            connection.setTravelWith(currentValue);
        }
        if (localName.equals("ErsteKlasse")) {
            connection.getAllocation().setFirstClass(Integer.parseInt(currentValue));
        }
        if (localName.equals("ZweiteKlasse")) {
            connection.getAllocation().setSecondClass(Integer.parseInt(currentValue));
        }
        if (localName.equals("Bemerkung")) {
            connection.setComment(currentValue);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentValue = new String(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    public Connection getConnection() {
        return connection;
    }

}
