package org.anderes.edu.streams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ArraySampleTest {

    private List<Integer> prices = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
    private ArraySample sample = new ArraySample(prices);
    
    @Before
    public void setup() {
        sample.dumpLambda();
        sample.dumpMethodeReferenze();
    }
    
    @Test
    public void getTotalCost7Test() {
        assertThat(sample.getTotalCost7(), is(162d));
    }
    
    @Test
    public void getTotalCost8Test() {
        assertThat(sample.getTotalCost8(), is(162d));
    }
}
