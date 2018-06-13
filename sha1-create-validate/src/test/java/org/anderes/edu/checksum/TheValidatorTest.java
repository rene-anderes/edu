package org.anderes.edu.checksum;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.anderes.edu.checksum.TheValidator;
import org.junit.Test;

public class TheValidatorTest {

    private final Path toValidate = Paths.get("target", "test-classes", "testdata", "toValidate.csv");
    private final Path wrongSha1 = Paths.get("target", "test-classes", "testdata", "wrongSha1.csv");
    
    @Test
    public void shouldBeValidate() throws Exception {
        final TheValidator valdator = TheValidator.build();
        assertThat(valdator.validate(toValidate), is(true));
    }
    
    @Test
    public void shouldBeNotValidate() throws Exception {
        final TheValidator valdator = TheValidator.build();
        assertThat(valdator.validate(wrongSha1), is(false));
    }
}
