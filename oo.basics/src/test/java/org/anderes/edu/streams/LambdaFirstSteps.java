package org.anderes.edu.streams;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import org.junit.Test;

public class LambdaFirstSteps {

    
    @Test
    public void shouldBeSystemOut() {
        Collection<String> myList = Arrays.asList("element1","element2","element3");
        Consumer<String> writeAllElementsToSystemOut = element -> System.out.println(element); 
        myList.stream().forEach(writeAllElementsToSystemOut);
        
        myList.stream().forEach(System.out::println);
    }
    
    @Test
    public void shouldBeMapToInt() {
        Collection<Integer> myList = Arrays.asList(1,2,3,4,5);
        int factor = 12;
        int sum = myList.stream().mapToInt(value -> value * factor).sum();
        assertThat(sum, is(180));
    }
    
    @Test
    public void shouldBeMapToDouble() {
        Collection<Integer> myList = Arrays.asList(1,2,3,4,5);
        double factor = 0.8;
        double sum = myList.stream().mapToDouble(value -> value * factor).sum();
        assertThat(sum, is(12.0));
    }
}
