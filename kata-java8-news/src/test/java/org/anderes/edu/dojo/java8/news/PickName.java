package org.anderes.edu.dojo.java8.news;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class PickName {

    private final List<String> givenNames = Arrays.asList("Peter", "Paul", "Maria", "Steve");
    
    @Test
    public void callOldStyle() {
        pickNameOldStyle(givenNames, "P");
        pickNameOldStyle(givenNames, "Z");
    }
    
    @Test
    public void callNewStyle() {
        pickName(givenNames, "P");
        pickName(givenNames, "Z");
    }
    
    
    private void pickName(final List<String> names, final String startingLetter) {
        final Optional<String> foundName = names.stream()
                        .filter(name -> name.startsWith(startingLetter))
                        .findFirst();
        System.out.println(String.format("A name staring with %s: %s", startingLetter, foundName.orElse("No name found")));
    }

    public void pickNameOldStyle(final List<String> names, final String startingLetter) {
        String foundName = null;
        for(String name : names) {
            if (name.startsWith(startingLetter)) {
                foundName = name;
                break;
            }
        }
        System.out.print(String.format("A name staring with %s: ", startingLetter));
        if (foundName == null) {
            System.out.println("No name found");
        } else {
            System.out.println(foundName);
        }
    }
}
