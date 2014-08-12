package org.anderes.edu.testing.junit.sample01;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class JunitParameterized {

    private String value;

    @Parameters
    public static Collection<Object[]> data() {
        final String[] parameterOne = { "Hallo"};
        final String[] parameterTwo = { "World"};
        final Collection<Object[]> collection = new HashSet<>();
        collection.add(parameterOne);
        collection.add(parameterTwo);
        return collection;
    }
    
    public JunitParameterized(String value) {
        this.value = value;
    }
    
    @Test
    public void RegExCheck() {
        assertTrue(value.matches("\\w+"));
    }

}
