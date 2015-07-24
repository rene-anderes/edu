package org.anderes.edu.dojo.testdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RandomStringGeneratorTest {
    
    @Test
    public void shouldBeStringWithMinAndMax() {
        Generator<String> generator = RandomAlphabeticGenerator.setMinLenght(5).setMaxLenght(200).build();
        for (int i = 0; i < 10000; i++) {
            final String str = generator.next();
            assertThat("String: '" + str + "' ist zu kurz.", str.length() > 4, is(true));
            assertThat("String: '" + str + "' ist zu lang.", str.length() < 201, is(true));
        }
    }
    
    @Test
    public void shouldBeStringWithoutMinAndMax() {
        Generator<String> generator = RandomAlphabeticGenerator.build();
        for (int i = 0; i < 10000; i++) {
            final String str = generator.next();
            assertThat("String: '" + str + "' ist zu kurz.", str.length() > 0, is(true));
            assertThat("String: '" + str + "' ist zu lang.", str.length() < 1001, is(true));
        }
    }

}
