package org.anderes.edu.streams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ArraySampleTest {

    private List<Integer> prices = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
    
    @Test
    public void getTotalCost7Test() {
        ArraySample sample = new ArraySample();
        assertThat(sample.getTotalCost7(prices), is(162d));
    }
    
    @Test
    public void getTotalCost8Test() {
        ArraySample sample = new ArraySample();
        assertThat(sample.getTotalCost8(prices), is(162d));
    }
}
