package org.anderes.edu.dojo.java8.news;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class StreamOperations {

    private Stream<String> stringStream;
    private Stream<Integer> integerStream;
    
    @Before
    public void setup() {
        stringStream = Stream.of("Erde", "Mond", "Merkur", "Venus", "Sonne", "Mars", "Jupiter", "Saturn");
        integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
    }
    
    @Test
    @SuppressWarnings({ "resource", "unused" })
    public void createStreamOperations() throws IOException {
        String[] myArray = { "Erde", "Mond", "Merkur", "Venus", "Sonne", "Mars", "Jupiter", "Saturn" };
        List<String> myList = Arrays.asList( "Kallisto", "Io", "Europa", "Ganymed" );
        
        Stream<String> streamOfArray = Arrays.stream(myArray);
        Stream<String> streamOfList = myList.stream(); 
        Stream<String> stringStream  = Stream.of( "Kallisto", "Io", "Europa", "Ganymed" );
        Stream<Integer> integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
        
        Stream<Path> directoryFiles = Files.list(Paths.get( "D:/Temp/"));
        directoryFiles.forEach(System.out::println);

        IntStream intByRange = IntStream.range(7, 42);
        assertThat(intByRange.count(), is(35L));
    }
    
    @Test
    public void intermediateStreamOperationsFilter() {
        
        Stream<String> filtered = stringStream.filter(element -> element.matches("\\w*[e]\\w*"));
        assertThat(filtered.count(), is(5L));
    }
    
    @Test
    public void intermediateStreamOperationsFilterFilter() {
         
        Stream<String> filtered = stringStream
                        .filter(element -> element.length() > 4)
                        .filter(element -> element.matches("\\w*[e]\\w*"));
        assertThat(filtered.count(), is(4L));
    }
    
    @Test
    public void intermediateStreamOperationsFilterPredicate() {
         
        Stream<String> filtered = stringStream
                        .filter(((Predicate<String>) 
                                        element -> element.length() > 4)
                                        .and(element -> element.matches("\\w*[e]\\w*"))
                        );
        assertThat(filtered.count(), is(4L));
    }
    
    @Test
    public void intermediateStreamOperationsFilterWithPredicate() {
        
        final Predicate<String> predicate = element -> element.length() > 4;
        predicate.and(element -> element.matches("\\w{0,}[e]\\w{0,}"));
        
        Stream<String> filtered = stringStream.filter(predicate);
        
        List<String> filteredList = filtered.collect(Collectors.toList());
        
        assertThat(Arrays.asList("Merkur", "Venus", "Sonne", "Jupiter", "Saturn"), is(filteredList));
    }
    
    @Test
    public void intermediateStreamOperationsSkipLimit() {

        Stream<String> paged = stringStream.skip(2).limit(3);
        assertThat(paged.count(), is(3L));
    }
    
    @Test
    public void intermediateStreamOperationsSorted() {
        Stream<String> stringStream  = Stream.of( "Kallisto", "Io", "Europa", "Ganymed" );
        
        Stream<String> sortedStream = stringStream.sorted((p1,p2) -> Integer.compare(p1.length(), p2.length()));
        
        List<String> sortedList = sortedStream.collect(Collectors.toList());
        assertThat(sortedList, is(Arrays.asList("Io", "Europa", "Ganymed", "Kallisto")));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerMap() {
        
        Stream<Double> doubleStream = integerStream.map(i -> Math.sqrt(i.doubleValue()));
        assertThat(doubleStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerMapToDouble() {
        
        DoubleStream doubleStream = integerStream.mapToDouble(Math::sqrt);
        assertThat(doubleStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerPeek() {
        
        Stream<Integer> myStream = integerStream.peek(System.out::println);
        assertThat(myStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerMapToBigDecimal() {
        
        Stream<BigDecimal> myStream = integerStream.map(BigDecimal::new);
        assertThat(myStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSorted() {
        integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21, 1, 2, 3, 5, 8, 13, 21 );
        Stream<Integer> myStream = integerStream.sorted().distinct();
        assertThat(myStream.count(), is(7L));
    }

    @Test
    public void terminalOperationsReduce() {
        Stream<Integer> integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
        
        int value = integerStream.reduce(0, (a, b) -> a + 24 + b);
        
        // 1 + 24 + 2 = 27
        // 27 + 24 + 3 = 54
        // 54 + 24 + 5 = 83
        // ....
        assertThat(value, is(221));
    }
    
    @Test
    public void terminalOperationsCollectToList() {
        List<Integer> valueList = integerStream.collect(Collectors.toList());
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsCollectToCollection() {
        HashSet<Integer> valueList = integerStream.collect(Collectors.toCollection(HashSet::new));
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsCollectToMap() {
        Map<Short, Boolean> valueList = integerStream.collect(Collectors.toMap(Integer::shortValue, value -> value % 2 == 0));
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsFindFirst() {
        Optional<Integer> optional = integerStream.findFirst();
        assertThat(optional.isPresent(), is(true));
    }

    @Test
    public void terminalOperationsAnyMatch() {
        boolean ok = integerStream.anyMatch(a -> a > 9);
        assertThat(ok, is(true));
    }
    
    @Test
    public void filterMapReduce() {
        Integer sum = stringStream.
                        filter(planet -> !planet.contentEquals("Sonne"))
                        .map(String::length)
                        .reduce(0, Integer::sum);
        assertThat(sum, is(36));
    }
    
    @Test
    public void filterMapReduceParallel() {
        Integer sum = stringStream
                        .parallel()
                        .filter(planet -> !planet.contentEquals("Sonne"))
                        .map(String::length)
                        .reduce(0, Integer::sum);
        assertThat(sum, is(36));
    }
    
    @Test
    public void filterMapReduceCollectJoining() {
        String resultString = stringStream.collect(Collectors.joining(";"));
        assertThat(resultString, is("Erde;Mond;Merkur;Venus;Sonne;Mars;Jupiter;Saturn"));
    }
    
    @Test
    public void iterateNatural() {
        Stream.iterate(1, a -> a+1).limit(100).forEach(System.out::println);
    }
    
    @Test
    public void iterateFibonacci() {
        
        Stream.iterate(new BigDecimal[]{ BigDecimal.valueOf(1), BigDecimal.valueOf(1)}, a -> new BigDecimal[]{a[1], a[0].add(a[1]) })
                .limit(100)
                .map(v -> v[0])
                .forEach(System.out::println);
    }
    
    @Test
    public void streamWithSideEffectsNotStateless() {
        /** dont do this.... */
        Set<Character> charsAlreadySeen = new HashSet<>();
        stringStream.map(p -> p.charAt(0))
                .filter(c -> {
                    if (charsAlreadySeen.contains(c)) {
                        return false;
                    } else {
                        charsAlreadySeen.add(c);
                        return true;
                    }})
                .forEach(System.out::println);
    }
   
    @Test
    public void streamNotStatelessButAllowed() {
        final StringBuffer sb = new  StringBuffer() ; 
        stringStream.parallel().forEach(s -> sb.append(s)); 
        String resultString = sb.toString(); 
        
        assertThat(resultString.length(), is(41));
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void streamWithInterference1() {
        final List<Integer> underlyingList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9)); 
        underlyingList.stream()
            .map(i->2*i)
            .forEach(underlyingList::add);  // <=  ConcurrentModificationException 
    }
    
    @Test(expected = ConcurrentModificationException.class)
    public void streamWithInterference2() {
        final List<Integer> underlyingList = Collections.synchronizedList(new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9))); 
        underlyingList.stream()
            .map(i->2*i)
            .forEach( underlyingList ::add);  // <=  ConcurrentModificationException 

    }
    
    @Test
    public void comparatorFunctionalInterface() {
        final List<String> myList = Arrays.asList( "Kallisto", "Io", "Europa", "Ganymed" );
        /** old style */
//        myList.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return Integer.compare(o1.length(), o2.length());
//            }
//            
//        });
        /** with Lambda */
        Comparator<String> c = (o1, o2) -> Integer.compare(o1.length(), o2.length());
        myList.sort(c);
        assertThat(myList, contains("Io", "Europa", "Ganymed", "Kallisto"));
    }
}
