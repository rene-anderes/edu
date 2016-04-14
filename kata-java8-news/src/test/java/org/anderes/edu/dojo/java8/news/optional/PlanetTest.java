package org.anderes.edu.dojo.java8.news.optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class PlanetTest {

    @Test
    public void shouldBeCreateNewWithAllData() {
        
        final Planet planet = new Planet("Erde", 1.00, 1.00, Optional.of(1));
        assertThat(planet.getName(), is("Erde"));
        assertThat(planet.getDiameter(), is(Double.valueOf(1.00)));
        assertThat(planet.getMass(), is(Double.valueOf(1.00)));
        assertThat(planet.getMoons(), is(notNullValue()));
        assertThat(planet.getMoons().orElseThrow(() -> new IllegalStateException()), is(Integer.valueOf(1)));
    }
    
    @Test
    public void shouldBeCreateNewWithPartialData() {
        
        final Planet planet = new Planet("Mars", 0.532, 0.11, Optional.empty());
        assertThat(planet.getName(), is("Mars"));
        assertThat(planet.getDiameter(), is(Double.valueOf(0.532)));
        assertThat(planet.getMass(), is(Double.valueOf(0.11)));
        assertThat(planet.getMoons(), is(Optional.empty()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldBeCreateNewWithWrongData() {
        new Planet("Jupiter", 0.532, null, Optional.of(67));
    }
}
