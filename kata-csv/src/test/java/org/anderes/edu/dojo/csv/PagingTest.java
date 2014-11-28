package org.anderes.edu.dojo.csv;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PagingTest {
    
    private List<List<String>> records;

    @Before
    public void setUp() throws Exception {
        records = new ArrayList<List<String>>(7);
        records.add(Arrays.asList("Peter", "42", "New York"));
        records.add(Arrays.asList("Paul", "57", "London"));
        records.add(Arrays.asList("Mary", "35", "Munich"));
        records.add(Arrays.asList("Jaques", "66", "Paris"));
        records.add(Arrays.asList("Yuri", "23", "Moscow"));
        records.add(Arrays.asList("Stephanie", "47", "Stockholm"));
        records.add(Arrays.asList("Nadia", "29", "Madrid"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldBeFirstPage() {
        final Paging paging = new Paging(records);
        final List<List<String>> result = paging.firstPage();
        assertThat(result.size(), is(3));
        assertThat(result, contains(
                        Arrays.asList("Peter", "42", "New York"), 
                        Arrays.asList("Paul", "57", "London"),
                        Arrays.asList("Mary", "35", "Munich")));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldBeLastPage() {
        final Paging paging = new Paging(records);
        final List<List<String>> result = paging.lastPage();
        assertThat(result.size(), is(3));
        assertThat(result, contains(
                        Arrays.asList("Yuri", "23", "Moscow"), 
                        Arrays.asList("Stephanie", "47", "Stockholm"),
                        Arrays.asList("Nadia", "29", "Madrid")));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void shouldBeNextPage() {
        final Paging paging = new Paging(records);
        paging.firstPage();
        final List<List<String>> result = paging.nextPage();
        assertThat(result.size(), is(3));
        assertThat(result, contains(
                        Arrays.asList("Jaques", "66", "Paris"), 
                        Arrays.asList("Yuri", "23", "Moscow"),
                        Arrays.asList("Stephanie", "47", "Stockholm")));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void shouldBeNextNextPage() {
        final Paging paging = new Paging(records);
        paging.firstPage();
        paging.nextPage();
        final List<List<String>> result = paging.nextPage();
        assertThat(result.size(), is(3));
        assertThat(result, contains(
                        Arrays.asList("Yuri", "23", "Moscow"), 
                        Arrays.asList("Stephanie", "47", "Stockholm"),
                        Arrays.asList("Nadia", "29", "Madrid")));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void shouldBePreviousPage() {
        final Paging paging = new Paging(records);
        paging.lastPage();
        final List<List<String>> result = paging.previousPage();
        assertThat(result.size(), is(3));
        assertThat(result, contains(
                        Arrays.asList("Paul", "57", "London"),
                        Arrays.asList("Mary", "35", "Munich"),
                        Arrays.asList("Jaques", "66", "Paris")));
    }
}
