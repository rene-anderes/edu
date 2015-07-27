package org.anderes.edu.dojo.testdata;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;


import java.util.List;

import org.junit.Test;

public class TestdataBuilderTest {
    
    private Generator<String> generator1 = RandomAlphabeticGenerator.setMaxLenght(20).build();
    private Generator<String> generator2 = RandomAlphabeticGenerator.setMaxLenght(10).build();
    
    @Test
    public void shouldBeCreateCollection() {
        final List<List<?>> collection = TestdataCreator.add(generator1).add(generator2).create(50);
        
        assertThat(collection, is(notNullValue()));
        assertThat(collection.size(), is(50));
        collection.forEach(row -> checkRow(row));
    }

    private void checkRow(final Collection<?> valueList) {
        assertThat(valueList.size(), is(2));
        valueList.forEach(value -> {
            assertThat((Object)value, is(instanceOf(String.class)));
        });
    }

}
