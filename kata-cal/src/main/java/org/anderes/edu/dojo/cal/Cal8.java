package org.anderes.edu.dojo.cal;

import static java.time.format.TextStyle.SHORT;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

public class Cal8 {

    private YearMonth yearMonth;
    private Locale locale;
    private DayOfWeek startDayOfWeek;
    private String emptyDayOfWeek = "";
    private final String SPACER = " ";
    private final int dayOfWeekLenght;
    private final int DAYS_OF_WEEK = DayOfWeek.values().length;

    public Cal8(YearMonth yearMonth, Locale locale, DayOfWeek startDayOfWeek) {
        this.yearMonth = yearMonth;
        this.locale = locale;
        this.startDayOfWeek = startDayOfWeek;
        dayOfWeekLenght = DayOfWeek.MONDAY.getDisplayName(SHORT, locale).length();
        for (int i = 0; i < dayOfWeekLenght; i++) {
            emptyDayOfWeek += SPACER;
        }
    }

    public void printTo(OutputStream outputStream) {
        try {
            writeHeader(outputStream);
            writeLineSeparator(outputStream);
            writeDayOfWeek(outputStream);
            writeLineSeparator(outputStream);
            writeCalendar(outputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
    }
    
    private void writeCalendar(OutputStream outputStream) throws IOException {
        LocalDate dateToWrite = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        do {
            dateToWrite = writeOneWeek(startDayOfWeek, outputStream, dateToWrite);
            writeLineSeparator(outputStream);
        } while (dateToWrite.getMonth() == yearMonth.getMonth());
    }

    private LocalDate writeOneWeek(DayOfWeek startDay, OutputStream outputStream, LocalDate dateToWrite) throws IOException {
        String line = "";
        DayOfWeek dayOfWeek = startDay;
        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            if (i > 0) {
                line += SPACER;
            }
            if (isNotTheSameMonth(dateToWrite)) {
                line += emptyDayOfWeek;
                continue;
            }
            if(dayOfWeek.equals(dateToWrite.getDayOfWeek())) {
                line += getFormattedDayOfMonth(dateToWrite.getDayOfMonth());
                dateToWrite = dateToWrite.plusDays(1);
            } else {
                line += emptyDayOfWeek;
            }
            dayOfWeek = dayOfWeek.plus(1);
        }
        outputStream.write(line.getBytes());
        return dateToWrite;
    }

    private boolean isNotTheSameMonth(LocalDate dateToWrite) {
        return dateToWrite.getMonth() != yearMonth.getMonth();
    }

    private String getFormattedDayOfMonth(int day) {
        String dayString = "";
        int counter = dayOfWeekLenght-1;
        if (day > 9) {
            counter--;
        }
        for (int i = 0; i < counter; i++) {
            dayString += SPACER;
        }
        dayString += day;
        return dayString;
    }
    
    private void writeDayOfWeek(OutputStream outputStream) throws IOException {
        outputStream.write(createDayOfWeekLine().getBytes());
    }
    
    private String createDayOfWeekLine() {
        DayOfWeek dayOfWeek = startDayOfWeek;
        String dayOfWeekDisplayName = "";
        for (int i = 0; i < DAYS_OF_WEEK; i++) {
            if (i > 0) {
                dayOfWeekDisplayName += SPACER;
            }
            dayOfWeekDisplayName += dayOfWeek.getDisplayName(SHORT, locale);
            dayOfWeek = dayOfWeek.plus(1);
        }
        return dayOfWeekDisplayName;
    }

    private void writeLineSeparator(OutputStream outputStream) throws IOException {
        outputStream.write(System.lineSeparator().getBytes());        
    }

    private void writeHeader(OutputStream outputStream) throws IOException {
        final String header = String.format(locale, "%1$tB %1$tY", yearMonth);
        final int width = createDayOfWeekLine().length();
        final int counter = (width - header.length()) / 2;
        String headerLine = "";
        for (int i = 0; i < counter; i++) {
            headerLine += SPACER;
        }
        headerLine += header;
        outputStream.write(headerLine.getBytes());
   }

    
}
