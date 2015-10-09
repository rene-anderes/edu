package org.anderes.edu.dojo.java8.news;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Test;

public class MethodeReference {
    
    @Test
    public void showCase() {
        Collection<String> names = Arrays.asList("Peter", "Paul", "Mary", "Bill");
        
        // Klassenname::Klassenmethodenname
        names.forEach(System.out::println);

        // Klassenname::new
        Collection<Person> persons = names.stream().map(Person::new).collect(Collectors.toList());
        persons.forEach(p -> System.out.println(p.getName()));
        
        // Klassenname::Instanzmethodenname
        persons.stream().map(Person::getName).forEach(n -> System.out.println(n));
        
        // Objektreferenz::Instanzmethodenname
        PersonProcessor personProcessor =  new PersonProcessor();
        persons.forEach(personProcessor::process);
    }
    
    class Person {
        private final String name;

        Person(String name) {
            super();
            this.name = name;
        }
        
        String getName() {
            return name;
        }
    }
    
    class PersonProcessor {
        
        void process(final Person person) {
            System.out.println(person.getName());
        }
    }
}
