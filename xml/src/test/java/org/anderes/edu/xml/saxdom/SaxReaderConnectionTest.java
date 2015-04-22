package org.anderes.edu.xml.saxdom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.anderes.edu.xml.saxdom.exercise.connection.Connection;
import org.anderes.edu.xml.saxdom.exercise.connection.SaxConnectionHandler;
import org.junit.Test;

public class SaxReaderConnectionTest {

    private final String xsdPath = "/org/anderes/edu/xml/saxdom/connection/Verbindung.xsd";
    
    
    @Test
    public void shouldBeReadTheXMLFile() throws Exception {
        final LocalDate expectedDate = LocalDate.of(2014, Month.DECEMBER, 1);
        final LocalTime expectedFromTime = LocalTime.of(12, 11, 0);
        final LocalTime expectedToTime = LocalTime.of(13, 23, 0);

        SaxConnectionHandler contentHandler = new SaxConnectionHandler();
        SaxReader.parseFile("/org/anderes/edu/xml/saxdom/connection/Verbindung.xml", xsdPath, contentHandler);
        
        final Connection connection = contentHandler.getConnection();
        
        assertThat(connection, is(notNullValue()));
        assertThat(connection.getDate(), is(expectedDate));
        assertThat(connection.getFrom().getStation(), is("St.Gallen"));
        assertThat(connection.getFrom().getTime(), is(expectedFromTime));
        assertThat(connection.getFrom().getPlatform(), is(1));
        assertThat(connection.getTo().getStation(), is("Zürich"));
        assertThat(connection.getTo().getTime(), is(expectedToTime));
        assertThat(connection.getTo().getPlatform(), is(16));
        assertThat(connection.getTravelWith(), is("ICN"));
        assertThat(connection.getAllocation().getFirstClass(), is(1));
        assertThat(connection.getAllocation().getSecondClass(), is(2));
        assertThat(connection.getComment(), is("InterCity 724 Richtung: Genève"));
    }
}
