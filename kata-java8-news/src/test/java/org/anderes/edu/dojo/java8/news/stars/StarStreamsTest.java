package org.anderes.edu.dojo.java8.news.stars;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class StarStreamsTest {

    private Collection<Star> starCollection;
    private final Comparator<? super Star> comparatorDistance = (s1, s2) -> s1.getDistance().compareTo(s2.getDistance());
    
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
        Optional<Star> star = starCollection.stream().min(comparatorDistance);
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("Sonne"));
    }
    
    /**
     * Liefert den nächstgelegenen Sterne welcher nicht im Sonnensystem liegt züruck.
     */
    @Test
    public void shouldBeNearestStarNotInSunsystem() {
        
        // when
        Optional<Star> star = starCollection.stream().filter(s -> !s.getSystem().equals("Sonnensystem")).min(comparatorDistance);
        
        // then
        assertThat(star.isPresent(), is(true));
        assertThat(star.get().getStarname(), is("Proxima Centauri (V645 Centauri)"));
    }
    
    
    /**
     * Liefert den Stern zurück, der am weitesten von der Erde entfernt ist.
     */
    @Test
    public void shouldBeMostDistantStar() {
        
        // when
        Optional<Star> star = starCollection.stream().max(comparatorDistance);
        
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
        
        // then
        assertThat(distanceList, is(notNullValue()));
        assertThat(distanceList.size(), is(65));
        assertThat(distanceList.stream().findFirst().get(), is(1.6E-5));
        assertThat(distanceList.stream().skip(64).findFirst().get(), is(16.19));
    }
    
    /**
     * Liefert die durchschnittliche Entfernung aller Sterne ausserhalb des Sonnensystems
     */
    @Test
    public void shouldBeAverageStarDistance() {
    
        // when
        double average = starCollection.stream().filter(s -> !s.getSystem().equals("Sonnensystem")).mapToDouble(s -> s.getDistance()).average().getAsDouble();
        
        // then
        assertThat(average, is(12.16934375));
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
    
    /**
     * Liefert eine Liste aller Sterne zurück, die den Grossbuchstaben 'A' im Namen tragen, sortiert nach Entfernung aufsteigend.
     */
    @Test
    public void shouldBeFindStarsWithA() {
        
        // when
        LinkedList<Star> stars = starCollection.stream().filter(star -> star.getStarname().matches(".*?[A].*")).sorted(comparatorDistance).collect(Collectors.toCollection(LinkedList::new));
        
        // then
        assertThat(stars, is(notNullValue()));
        assertThat(stars.size(), is(17));
        assertThat(stars.peekFirst().getStarname(), is("Alpha Centauri B (HD 128621)"));
        assertThat(stars.peekLast().getStarname(), is("GJ 412 A"));
    }
}
