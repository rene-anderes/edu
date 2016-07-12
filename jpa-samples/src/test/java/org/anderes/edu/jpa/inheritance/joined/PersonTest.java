package org.anderes.edu.jpa.inheritance.joined;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

    private PersonRepository repository;
    
    @Before
    public void setUp() throws Exception {
        repository = PersonRepository.build();
    }

    @Test
    public void findPersonById() {
        final Person person = repository.getPersonById(10001l);
        assertThat(person, is(not(nullValue())));
        assertThat(person.getPersonNr(), is(10001));
    }
    
    @Test
    public void findPersonByIdCriteria() {
        final Person person = repository.getPersonByIdCriteria(10001l);
        assertThat(person, is(not(nullValue())));
        assertThat(person.getPersonNr(), is(10001));
    }
    
    @Test
    public void findAllPersons() {
        final List<Person> persons = repository.getPersons();
        assertThat(persons, is(not(nullValue())));
        assertThat(persons.size(), is(3));
    }
}
