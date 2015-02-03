package org.anderes.edu.dojo.java8.news;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class StarStreamsTest {

    private Collection<Star> starCollection;
    
    @Before
    public void setup() {
        final Path csvFile = Paths.get("target/classes", "Sternen.csv");
        starCollection = StarReader.build(csvFile).readStars();
    }
    
    
    @Test
    public void shouldBeNearestStar() {
        
        // when
        Optional<Star> star = starCollection.stream().min((s1, s2) -> s1.getDistance().compareTo(s2.getDistance()));
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("Sonne"));
    }
    
    @Test
    public void shouldBeMostDistantStar() {
        
        // when
        Optional<Star> star = starCollection.stream().max((s1, s2) -> s1.getDistance().compareTo(s2.getDistance()));
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("LP 944-20"));
    }
    
    @Test
    public void shouldBeSortedDistanceList() {
        
        // when
        List<Double> distanceList = starCollection.stream().map(s -> s.getDistance()).sorted(Double::compareTo).collect(Collectors.toList());
        
        // then
        assertThat(distanceList, is(notNullValue()));
        assertThat(distanceList.size(), is(65));
        assertThat(distanceList.stream().findFirst().get(), is(1.6E-5));
        assertThat(distanceList.stream().skip(64).findFirst().get(), is(16.19));
    }
    
    @Test
    public void shouldBeGroupBySystem() {
        
        // when
        Map<String, List<Star>> map = starCollection.stream().collect(Collectors.groupingBy(s -> s.getSystem()));
        
        // then
        assertThat(map, is(notNullValue()));
        assertThat(map.get("Kruger 60").size(), is(2));
        assertThat(map.get("Kruger 60"), containsInAnyOrder(
                        Star.create("Kruger 60","Kruger 60 A (BD+56°2783)","13.1400000000"), 
                        Star.create("Kruger 60","Kruger 60 B (DO Cephei)","13.1400000000")));
    }
}
