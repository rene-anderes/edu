package org.anderes.edu.dojo.java8.news.time;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.time.LocalDate;

import static java.time.Month.*;

import org.junit.Test;

public class OfferTest {

    private LocalDate expectedValidFrom = LocalDate.of(2014, NOVEMBER, 1);
    private LocalDate expectedValidTo = LocalDate.of(2014, DECEMBER, 31);
   
    @Test
    public void shouldBeCorrect() {
        // when
        final Offer offer = new Offer("Top Angebot", "01.11.2014", "31.12.2014");
        
        // then
        assertThat(offer.getValidFrom(), is(notNullValue()));
        assertThat(offer.getValidFrom(), is(expectedValidFrom));
        assertThat(offer.getValidTo(), is(notNullValue()));
        assertThat(offer.getValidTo(), is(expectedValidTo));
        assertThat(offer.getDays(), is(60L));
    }

}
