package org.anderes.edu.assoziation;

import static org.junit.Assert.assertTrue;
import java.util.Collection;

import org.anderes.edu.assoziation.Firma;
import org.anderes.edu.assoziation.Person;
import org.junit.Test;


public class UnmodifiableTest {

    @Test(expected = UnsupportedOperationException.class)
    public void listTest() {
	Firma firma = new Firma();
	Collection<Person> arbeitnehmer = firma.getArbeitnehmer();
	assertTrue(arbeitnehmer.add(new Person()));
    }
    
}
