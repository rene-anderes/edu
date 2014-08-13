package org.anderes.edu.dojo.boxplot;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BoxplotTest {
   
    private List<Double> values = Arrays.asList(18d, 24d, 19d, 19d, 20d, 25d, 24d, 18d, 24d, 17d);
    private Boxplot boxplot = new Boxplot(values);
    
    @Test
    public void shouldBeMedian() {
        assertThat(boxplot.median(), is(19.5));
    }
    
    @Test
    public void shouldBeMin() {
        assertThat(boxplot.min(), is(17d));
    }
    
    @Test
    public void shouldBeMax() {
        assertThat(boxplot.max(), is(25d));
    }
    
    @Test
    public void shouldBeLowerQuartile() {
        assertThat(boxplot.lowerQuartile(), is(18d));
    }
    
    @Test
    public void shouldBeUpperQuartile() {
        assertThat(boxplot.upperQuartile(), is(24d));
    }
}
