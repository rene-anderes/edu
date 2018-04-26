package org.anderes.edu.sha1;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class TheValidatorTest {

    private final Path csvFile = Paths.get("target", "test-classes", "testdata", "test.csv");
    
    @Test
    public void shouldBeValidate() throws Exception {
        final TheValidator valdator = TheValidator.build();
        valdator.validate(csvFile);
    }
}
