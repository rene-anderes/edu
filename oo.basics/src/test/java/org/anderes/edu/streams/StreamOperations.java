package org.anderes.edu.streams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Stream<String>  stringStream  = Stream.of( "Kallisto", "Io", "Europa", "Ganymed" );
        Stream<Integer> integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
        
        Stream<Path> directoryFiles = Files.list(Paths.get( "D:/Temp/"));
        directoryFiles.forEach(System.out::println);

        IntStream intByRange = IntStream.range(7, 42);
        assertThat(intByRange.count(), is(35L));
    }
    
    @Test
    public void intermediateStreamOperationsSample1() {
        
        Stream<String> filtered = stringStream.filter(element -> element.matches("\\w{0,}[e]\\w{0,}"));
        assertThat(filtered.count(), is(5L));
    }
    
    @Test
    public void intermediateStreamOperationsSample2() {
         
        Stream<String> filtered = stringStream
                        .filter(element -> element.length() > 4)
                        .filter(element -> element.matches("\\w{0,}[e]\\w{0,}"));
        assertThat(filtered.count(), is(4L));
    }
    
    @Test
    public void intermediateStreamOperationsSample3() {
         
        Stream<String> filtered = stringStream
                        .filter(((Predicate<String>) 
                                        element -> element.length() > 4)
                                        .and(element -> element.matches("\\w{0,}[e]\\w{0,}"))
                        );
        assertThat(filtered.count(), is(4L));
    }

    @Test
    public void intermediateStreamOperationsSample4() {

        Stream<String> paged = stringStream.skip(2).limit(3);
        assertThat(paged.count(), is(3L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample1() {
        
        Stream<Double> doubleStream = integerStream.map(i -> Math.sqrt(i.doubleValue()));
        assertThat(doubleStream.count(), is(7L));
        
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample2() {
        
        Stream<Double> doubleStream = integerStream.map(i -> Math.sqrt(i.doubleValue()));
        assertThat(doubleStream.count(), is(7L));
        
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample3() {
        
        DoubleStream doubleStream = integerStream.mapToDouble(Math::sqrt);
        assertThat(doubleStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample4() {
        
        Stream<Integer> myStream = integerStream.peek(System.out::println);
        assertThat(myStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample5() {
        
        Stream<BigDecimal> myStream = integerStream.map(BigDecimal::new);
        assertThat(myStream.count(), is(7L));
    }
    
    @Test
    public void intermediateStreamOperationsIntegerSample6() {
        integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21, 1, 2, 3, 5, 8, 13, 21 );
        Stream<Integer> myStream = integerStream.sorted().distinct();
        assertThat(myStream.count(), is(7L));
    }
    
    @Test
    public void terminalOperationsSample1() {
        Integer i = integerStream.reduce(0, (a, b) -> a + 24 + b);
        assertThat(i, is(221));
    }
    
    @Test
    public void terminalOperationsSample2() {
        List<Integer> valueList = integerStream.collect(Collectors.toList());
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsSample3() {
        HashSet<Integer> valueList = integerStream.collect(Collectors.toCollection(HashSet::new));
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsSample4() {
        Map<Short, Boolean> valueList = integerStream.collect(Collectors.toMap(Integer::shortValue, value -> value % 2 == 0));
        assertThat(valueList.size(), is(7));
    }
    
    @Test
    public void terminalOperationsSample5() {
        Optional<Integer> optional = integerStream.findFirst();
        assertThat(optional.isPresent(), is(true));
    }

    @Test
    public void terminalOperationsSample6() {
        boolean ok = integerStream.anyMatch(a -> a > 9);
        assertThat(ok, is(true));
    }
    
    @Test
    public void filterMapReduceSample1() {
        Integer sum = stringStream.filter(planet -> !planet.contentEquals("Sonne")).map(String::length).reduce(0, Integer::sum);
        assertThat(sum, is(36));
    }
    
    @Test
    public void filterMapReduceSample2() {
        String resultString = stringStream.collect(Collectors.joining(";"));
        assertThat(resultString, is("Erde;Mond;Merkur;Venus;Sonne;Mars;Jupiter;Saturn"));
    }
    
    @Test
    public void iterateNatural() {
        Stream.iterate(1, a -> a+1).limit(100).forEach(System.out::println);
    }
    
    @Test
    public void iterateFibonacci() {
        
        Stream.iterate(new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(1)}, a -> new BigDecimal[]{a[1], a[0].add(a[1])})
                .limit(100)
                .map(v -> v[0])
                .forEach(System.out::println);
    }
}
