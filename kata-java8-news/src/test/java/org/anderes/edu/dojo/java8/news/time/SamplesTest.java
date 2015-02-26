package org.anderes.edu.dojo.java8.news.time;

import static java.time.Month.FEBRUARY;
import static java.time.format.TextStyle.FULL;
import static java.util.Locale.GERMAN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class SamplesTest {

    @Test
    public void shouldBeTimestamp() {
        System.out.println("--- aktueller Zeitstempel ----");
        Instant timestamp = Instant.now();
        System.out.println(timestamp.toString());
        System.out.println("--/ aktueller Zeitstempel ----\n");
    }
    
    @Test
    public void shouldBeDuration() {
        System.out.println("--- Duration ----");
        Instant start = Instant.now();
        waitOneSecond();
        Instant end = Instant.now();
        Duration elapsed = Duration.between(start, end);
        System.out.println("Dauer: " + elapsed);
        System.out.println("--/ Duration ----\n");
    }
    
    private void waitOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { }
    }

    @Test
    public void shouldBeClock() {
        System.out.println("--- Clock ----");
        Clock clock = Clock.systemDefaultZone();
        System.out.println(String.format("Aktuelle Zeit: %s", clock.instant()));
        System.out.println(String.format("Zeitzone: %s", clock.getZone().getDisplayName(FULL, GERMAN)));
        System.out.println("--/ Clock ----\n");
    }
    
    @Test
    public void shouldBeLocalDate() {
        System.out.println("--- LocalDate ----");
        LocalDate birthday = LocalDate.of(1967, FEBRUARY, 22);
        System.out.println(birthday);
        System.out.println("--/ LocalDate ----\n");
    }
    
    @Test
    public void shouldBeParseToLocalDate() {
        System.out.println("--- parse to LocalDate ----");
        String text = "2000 12 22";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDate date = LocalDate.parse(text, formatter);
        System.out.println(date);
        System.out.println("--/ parse to LocalDate ----\n");
    }
    
    @Test
    public void shouldBeLocalTime() {
        System.out.println("--- LocalTime ----");
        LocalTime localtime1 = LocalTime.of(19, 30, 00, 0); // 19:30 Uhr
        LocalTime localtime2 = LocalTime.parse("19:30");    // auch 19:30 Uhr
        System.out.println(String.format("bis %s Uhr abends", localtime1));
        System.out.println(String.format("bis %s Uhr abends", localtime2));
        System.out.println("--/ LocalTime ----\n");
    }
    
    @Test
    public void shouldBeLocalDateTime() {
        System.out.println("--- LocalDateTime ----");
        LocalTime localtime = LocalTime.parse("19:30");
        // der 32. Tag im Jahr ist der 1. Februar
        LocalDate dateFeb1st = LocalDate.ofYearDay(2013, 32); 
        LocalDateTime localdatetime1 = LocalDateTime.of(dateFeb1st,  localtime);
        LocalDateTime localdatetime2 = LocalDateTime.of(2013, FEBRUARY, 1, 12, 30, 00, 0);
        System.out.println(localdatetime1);
        System.out.println(localdatetime2);
        System.out.println("--/ LocalDateTime ----\n");
    }
    
    @Test
    public void shouldBePeriod() {
        System.out.println("--- Period ----");
        LocalDate startDateInclusive = LocalDate.of(2012, FEBRUARY, 1);
        LocalDate endDateExclusive = LocalDate.of(2013, FEBRUARY, 2);
        Period between = Period.between(startDateInclusive, endDateExclusive);
        System.out.println(between);
        System.out.println("--/ Period ----\n");
    }
    
    @Test
    public void shouldBeZoneDateTime() {
        System.out.println("--- ZoneDateTime ----");
        LocalDateTime localdatetime = LocalDateTime.of(2013, FEBRUARY, 1, 12, 30, 00, 0);
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localdatetime, ZoneId.ofOffset("UTC", ZoneOffset.ofHours(1)));
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(localdatetime, ZoneId.systemDefault());
        System.out.println(zonedDateTime1);
        System.out.println(zonedDateTime2);
        System.out.println("--/ ZoneDateTime ----\n");
    }
    
    @Test
    public void fluentApi() {
        System.out.println("--- ZonedDateTime with fluent API ----");
        ZonedDateTime now = ZonedDateTime.now(); 
        int dayOfYear = now.plusHours(12).minusDays(7)
            .withSecond(0).withNano(0)
            .getDayOfYear();
        System.out.println(dayOfYear);
        System.out.println("--/ ZonedDateTime with fluent API ----");
    }
    
    @Test
    public void shouldBeConvert() {
        System.out.println("--- Convert ----");
        Calendar xmas = new GregorianCalendar(2013, Calendar.DECEMBER, 24);
        Instant xmasAsInstant = xmas.toInstant();  // alt -> neu
        System.out.println(LocalDate.from(xmasAsInstant.atZone(ZoneId.systemDefault())));

        Date now1   = new Date();
        Instant nowAsInstant = now1.toInstant();   // alt -> neu
        Date now2  = Date.from(nowAsInstant);      // zurueck

        assertThat(now1, is(now2));
        
        GregorianCalendar g1= (GregorianCalendar) xmas;
        ZonedDateTime z = g1.toZonedDateTime();            // alt -> neu
        GregorianCalendar g2= GregorianCalendar.from(z);   //zurueck
        
        assertThat(g1.getTime(), is(g2.getTime()));
        System.out.println("--/ Convert ----");
    }
}
