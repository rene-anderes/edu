package org.anderes.edu.streams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class StreamOperations {
    
    private String[] myArray = { "Erde", "Mond", "Merkur", "Venus", "Sonne", "Mars", "Jupiter", "Saturn" };
    private List<String> myList = Arrays.asList( "Kallisto", "Io", "Europa", "Ganymed" );
    
    private Stream<String> stringStream;
    private Stream<Integer> integerStream;
    
    @Before
    public void setup() {
        stringStream = Stream.of("Erde", "Mond", "Merkur", "Venus", "Sonne", "Mars", "Jupiter", "Saturn");
        integerStream = Stream.of( 1, 2, 3, 5, 8, 13, 21 );
    }
    
    @Test
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
}
