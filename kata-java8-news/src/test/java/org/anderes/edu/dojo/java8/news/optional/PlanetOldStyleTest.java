package org.anderes.edu.dojo.java8.news.optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlanetOldStyleTest {

    @Test
    public void shouldBeCreateNewWithAllData() {
        
        final PlanetOldStyle planet = new PlanetOldStyle("Erde", 1.00, 1.00, 1);
        assertThat(planet.getName(), is("Erde"));
        assertThat(planet.getDiameter(), is(Double.valueOf(1.00)));
        assertThat(planet.getMass(), is(Double.valueOf(1.00)));
        assertThat(planet.getMoons(), is(Integer.valueOf(1)));
    }
    
    @Test
    public void shouldBeCreateNewWithPartialData() {
        
        final PlanetOldStyle planet = new PlanetOldStyle("Mars", 0.532, 0.11, null);
        assertThat(planet.getName(), is("Mars"));
        assertThat(planet.getDiameter(), is(Double.valueOf(0.532)));
        assertThat(planet.getMass(), is(Double.valueOf(0.11)));
        assertThat(planet.getMoons(), is(nullValue()));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldBeCreateNewWithWrongData() {
        new PlanetOldStyle("Jupiter", 0.532, null, 67);
    }
}
