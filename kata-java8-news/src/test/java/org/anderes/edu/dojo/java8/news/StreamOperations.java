package org.anderes.edu.dojo.java8.news;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamOperations {

    final Stream<String> stringStream = Stream.of("Erde", "Mond", "Merkur", "Venus", "Sonne", "Mars", "Jupiter", "Saturn");
    
    @Test
    public void filterWithPredicate() {
        
        final Predicate<String> predicate = element -> element.length() > 4;
        predicate.and(element -> element.matches("\\w{0,}[e]\\w{0,}"));
        
        Stream<String> filtered = stringStream.filter(predicate);
        
        List<String> filteredList = filtered.collect(Collectors.toList());
        
        assertThat(Arrays.asList("Merkur", "Venus", "Sonne", "Jupiter", "Saturn"), is(filteredList));
    }

    @Test
    public void reduce() {
        Stream<Integer> integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
        
        int value = integerStream.reduce(0, (a, b) -> a + 24 + b);
        
        // 1 + 24 + 2 = 27
        // 27 + 24 + 3 = 54
        // 54 + 24 + 5 = 83
        // ....
        assertThat(value, is(221));
    }
}
