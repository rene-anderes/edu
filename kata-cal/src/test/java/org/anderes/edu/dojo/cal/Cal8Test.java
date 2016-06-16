package org.anderes.edu.dojo.cal;

import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.YearMonth;
import java.util.Locale;

import org.junit.Test;

public class Cal8Test {


    @Test
    public void shouldBeFebruaryCanada() throws IOException {
        // given
        final ByteArrayOutputStream expectedOutputStream = createExpectedResultForCanada();
        final Cal8 cal = new Cal8(YearMonth.of(2014, Month.FEBRUARY), Locale.CANADA, DayOfWeek.SUNDAY);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // when
        cal.printTo(outputStream);
        cal.printTo(System.out);
        
        // then
        assertArrayEquals(expectedOutputStream.toByteArray(), outputStream.toByteArray());

    }

    @Test
    public void shouldBeFebruaryDe() throws IOException {
        // given
        final ByteArrayOutputStream expectedOutputStream = createExpectedResultForDe();
        final Cal8 cal = new Cal8(YearMonth.of(2014, Month.FEBRUARY), Locale.GERMAN, DayOfWeek.MONDAY);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // when
        cal.printTo(outputStream);
        cal.printTo(System.out);
        
        // then
        assertArrayEquals(expectedOutputStream.toByteArray(), outputStream.toByteArray());

    }
    
    private ByteArrayOutputStream createExpectedResultForCanada() {
        final ByteArrayOutputStream expectedOutputStream = new ByteArrayOutputStream();
        try {
            expectedOutputStream.write("       February 2014".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("Sun Mon Tue Wed Thu Fri Sat".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("                          1".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("  2   3   4   5   6   7   8".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("  9  10  11  12  13  14  15".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write(" 16  17  18  19  20  21  22".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write(" 23  24  25  26  27  28    ".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return expectedOutputStream;
    }

    private ByteArrayOutputStream createExpectedResultForDe() {
        final ByteArrayOutputStream expectedOutputStream = new ByteArrayOutputStream();
        try {
            expectedOutputStream.write("    Februar 2014".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("Mo Di Mi Do Fr Sa So".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("                1  2".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write(" 3  4  5  6  7  8  9".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("10 11 12 13 14 15 16".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("17 18 19 20 21 22 23".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
            expectedOutputStream.write("24 25 26 27 28      ".getBytes());
            expectedOutputStream.write(System.lineSeparator().getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return expectedOutputStream;
    }
}
