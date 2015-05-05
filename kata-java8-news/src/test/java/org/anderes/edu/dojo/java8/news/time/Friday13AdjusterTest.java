package org.anderes.edu.dojo.java8.news.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.Month.*;
import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.*;
import java.time.temporal.TemporalAdjuster;

import org.junit.Test;

public class Friday13AdjusterTest {

    final LocalDate expectedDate = LocalDate.of(2015, NOVEMBER, 13);
    
    final TemporalAdjuster friday13Adjuster = temporal -> {
        if (temporal.get(DAY_OF_MONTH) >= 13) {
            temporal = temporal.plus(1, MONTHS);
        }
        temporal = temporal.with(DAY_OF_MONTH, 13);
        while (temporal.get(DAY_OF_WEEK) !=  DayOfWeek.FRIDAY.getValue()) {
            temporal = temporal.plus(1, MONTHS);
        }
        return temporal;
    };
    
    
    @Test
    public void shouldBeCorrectStartApril() {
        LocalDate startAt = LocalDate.of(2015, APRIL, 1);
        LocalDate nextFriday13 = startAt.with(friday13Adjuster);
        assertThat(nextFriday13, is(expectedDate));
    }
    
    @Test
    public void shouldBeCorrectStartJanuar() {
        LocalDate startAt = LocalDate.of(2015,JUNE, 1);
        LocalDate nextFriday13 = startAt.with(friday13Adjuster);
        assertThat(nextFriday13, is(expectedDate));
    }
  
    @Test
    public void shouldBeCorrectStartNovember() {
        LocalDate startAt = LocalDate.of(2015,NOVEMBER, 14);
        LocalDate nextFriday13 = startAt.with(friday13Adjuster);
        assertThat(nextFriday13, is(LocalDate.of(2016, MAY, 13)));
    }
}
