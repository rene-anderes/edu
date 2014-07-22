package org.anderes.edu.dojo.cal;

import static java.util.Calendar.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

public class Cal {

    private final Calendar date;
    private final Locale local = Locale.GERMAN;
    
    public Cal(final Calendar date) {
        this.date = date;
    }

    public void printTo(final OutputStream outputStream) throws IOException {
        final int month = date.get(MONTH);
        final int year = date.get(YEAR);
        writeCalendarHeader(outputStream);
        writeLineSeparator(outputStream);
        writeDayOfWeek(outputStream);
        writeLineSeparator(outputStream);
        while (date.get(MONTH) == month) {
            writeOneWeek(outputStream);
            writeLineSeparator(outputStream);
        }
        date.set(year, month, 1);
    }

    private void writeOneWeek(final OutputStream outputStream) throws IOException {
        final int firstDayOfWeek = date.getFirstDayOfWeek();
        final Deque<Integer> weekDeque = createWeekDeque(firstDayOfWeek);
        final StringBuilder buffer = new StringBuilder(7);
        final int month = date.get(MONTH);
        while(!weekDeque.isEmpty()) {
            final Integer actDayOfWeek = weekDeque.remove();
            if (actDayOfWeek.equals(date.get(DAY_OF_WEEK)) && month == date.get(MONTH)) {
                appendDayOfMonth(buffer);
                date.add(DAY_OF_MONTH, 1);
            } else {
                buffer.append("  ");
            }
            if (!weekDeque.isEmpty()) {
                buffer.append(" ");
            }
        }
        outputStream.write(buffer.toString().getBytes());
    }

    private void appendDayOfMonth(final StringBuilder buffer) {
        final int day = date.get(DAY_OF_MONTH);
        if (day < 10) {
            buffer.append(" ");
        }
        buffer.append(day);
    }
    
    private void writeDayOfWeek(final OutputStream outputStream) throws IOException {
        final Map<Integer, String> indexMap = new HashMap<>(7);
        final Map<String, Integer> map = date.getDisplayNames(DAY_OF_WEEK, SHORT, local);
        for (String key : map.keySet()) {
            indexMap.put(map.get(key), key);
        }
        final int firstDayOfWeek = date.getFirstDayOfWeek();
        final StringBuilder buffer = new StringBuilder(7);
        final Deque<Integer> weekDeque = createWeekDeque(firstDayOfWeek);
        while(!weekDeque.isEmpty()) {
            buffer.append(indexMap.get(weekDeque.remove()));
            if (!weekDeque.isEmpty()) {
                buffer.append(" ");
            }
        }
        outputStream.write(buffer.toString().getBytes());
    }
    
    private Deque<Integer> createWeekDeque(final int firstIndex) {
        final Deque<Integer> deque = new LinkedList<>();
        for (int i = firstIndex; i <= 8; i++) {
            if (i == 8) {
                i = 1;
            }
            if (deque.contains(Integer.valueOf(i))) {
                break;
            }
            deque.addLast(Integer.valueOf(i));
        }
        return deque;
    }
    
    private void writeLineSeparator(final OutputStream outputStream) throws IOException {
        outputStream.write(System.lineSeparator().getBytes());
    }
    
    private void writeCalendarHeader(final OutputStream outputStream) throws IOException {
        final String header = String.format(local, "    %1$tB %1$tY", date);
        outputStream.write(header.getBytes());
    }
}
