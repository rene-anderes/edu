package org.anderes.edu.dojo.java8.news.map;

import static org.anderes.edu.dojo.java8.news.map.MapDemonstrationTest.Key.EXTERN;
import static org.anderes.edu.dojo.java8.news.map.MapDemonstrationTest.Key.INTERN;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MapDemonstrationTest {
    
    public enum Key { INTERN, EXTERN }
    
    private Map<Key, BigDecimal> prices;

    @Before
    public void setUp() throws Exception {
        prices = new HashMap<>();
        prices.put(EXTERN, BigDecimal.valueOf(2000));
    }

    @Test
    public void mapGetOrDefault() {
                
        assertThat(prices.getOrDefault(INTERN, BigDecimal.ZERO), is(BigDecimal.ZERO));
        assertThat(prices.getOrDefault(EXTERN, BigDecimal.ZERO), is(BigDecimal.valueOf(2000)));
    }
    
    @Test
    public void mapPutIfAbsent() {
        assertThat(prices.putIfAbsent(INTERN, BigDecimal.valueOf(1007)), is(nullValue()));
        assertThat(prices.get(INTERN), is(BigDecimal.valueOf(1007)));
        assertThat(prices.putIfAbsent(EXTERN, BigDecimal.valueOf(1007)), is(BigDecimal.valueOf(2000)));
        assertThat(prices.get(EXTERN), is(BigDecimal.valueOf(2000)));
    }
    
    @Test
    public void mapReplace() {
        assertThat(prices.replace(INTERN, BigDecimal.valueOf(1007)), is(nullValue()));
        assertThat(prices.get(INTERN), is(nullValue()));
        assertThat(prices.replace(EXTERN, BigDecimal.valueOf(1007)), is(BigDecimal.valueOf(2000)));
        assertThat(prices.get(EXTERN), is(BigDecimal.valueOf(1007)));
    }
    
    @Test
    public void mapCompute() {
        assertThat(prices.compute(EXTERN, (Key k, BigDecimal p) -> p.multiply(BigDecimal.valueOf(0.9))), is(BigDecimal.valueOf(1800D)));
    }
    
    @Test
    public void mapMerge() {
        assertThat(prices.merge(INTERN, BigDecimal.valueOf(1007D), BigDecimal::add), is(BigDecimal.valueOf(1007D)));
        assertThat(prices.merge(EXTERN, BigDecimal.valueOf(1007D), BigDecimal::add), is(BigDecimal.valueOf(3007D)));
    }

}
