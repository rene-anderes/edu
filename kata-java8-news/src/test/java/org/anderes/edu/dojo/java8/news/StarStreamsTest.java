package org.anderes.edu.dojo.java8.news;

import static org.hamcrest.CoreMatchers.hasItems;
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
    
    /**
     * Liefert den nächstgelegenen Stern (von der Erde aus gesehen) züruck.
     */
    @Test
    public void shouldBeNearestStar() {
        
        // when
        Optional<Star> star = starCollection.stream().min((s1, s2) -> s1.getDistance().compareTo(s2.getDistance()));
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("Sonne"));
    }
    
    /**
     * Liefert den Stern zurück, der am weitesten von der Erde entfernt ist.
     */
    @Test
    public void shouldBeMostDistantStar() {
        
        // when
        Optional<Star> star = starCollection.stream().max((s1, s2) -> s1.getDistance().compareTo(s2.getDistance()));
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("LP 944-20"));
    }
    
    /**
     * Liefert eine sortierte Liste aller Entfernungen zurück. Den nächstegelegen Stern zuerst.
     */
    @Test
    public void shouldBeSortedDistanceList() {
        
        // when
        List<Double> distanceList = starCollection.stream().map(s -> s.getDistance()).sorted(Double::compareTo).collect(Collectors.toList());
        
//        distanceList = starCollection.stream().mapToDouble(s -> s.getDistance()).sorted().boxed().collect(Collectors.toList());
        
        // then
        assertThat(distanceList, is(notNullValue()));
        assertThat(distanceList.size(), is(65));
        assertThat(distanceList.stream().findFirst().get(), is(1.6E-5));
        assertThat(distanceList.stream().skip(64).findFirst().get(), is(16.19));
    }
    
    /**
     * Liefert ein gruppierte Liste von Sternen zurück. Gruppiert nach Sternensystem.
     * </p>
     * Map: Key = Sternensystem / Value = Liste von Sternen die zu diesem System gehören
     */
    @Test
    public void shouldBeGroupBySystem() {
        
        // when
        Map<String, List<Star>> map = starCollection.stream().collect(Collectors.groupingBy(s -> s.getSystem()));
        
        // then
        assertThat(map, is(notNullValue()));
        assertThat(map.get("Kruger 60").size(), is(2));
        assertThat(map.get("Kruger 60"), hasItems(
                        Star.create("Kruger 60","Kruger 60 A (BD+56°2783)","13.1400000000"), 
                        Star.create("Kruger 60","Kruger 60 B (DO Cephei)","13.1400000000")));
    }
}
