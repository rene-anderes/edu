package org.anderes.edu.dojo.java8.news.time;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.NOVEMBER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class OfferOldStyleTest {

    private Date expectedValidFrom = november(1, 2014); 
    private Date expectedValidTo = december(31, 2014);
 
    @Test
    public void shouldBeCorrect() {
        // when
        final OfferOldStyle offer = new OfferOldStyle("Top Angebot", "01.11.2014", "31.12.2014");
        
        // then
        assertThat(offer.getValidFrom(), is(notNullValue()));
        assertThat(offer.getValidFrom(), is(expectedValidFrom));
        assertThat(offer.getValidTo(), is(notNullValue()));
        assertThat(offer.getValidTo(), is(expectedValidTo));
        assertThat(offer.getDays(), is(60L));
    }

    private Date november(int day, int year) {
        final Calendar date = Calendar.getInstance();
        date.set(year, NOVEMBER, day);
        return DateUtils.truncate(date, DAY_OF_MONTH).getTime();
    }
    
    private Date december(int day, int year) {
        final Calendar date = Calendar.getInstance();
        date.set(year, DECEMBER, day);
        return DateUtils.truncate(date, DAY_OF_MONTH).getTime();
    }
}
