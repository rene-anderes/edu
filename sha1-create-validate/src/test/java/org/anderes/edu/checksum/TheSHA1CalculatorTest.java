package org.anderes.edu.checksum;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.anderes.edu.checksum.ResultData;
import org.anderes.edu.checksum.TheCalculator;
import org.anderes.edu.checksum.TheSHA1Calculator;
import org.junit.Test;

public class TheSHA1CalculatorTest {

    
    @Test
    public void shouldBeCreateSha1ForOneFile() throws Exception {
        // given
        final Path theFile = Paths.get("target", "test-classes", "testdata", "Kata-Testdaten.pdf");
        final TheCalculator calc = new TheSHA1Calculator();
        
        // when
        final ResultData sha1 = calc.eval(theFile);
        
        // then
        assertThat(sha1, not(nullValue()));
        assertThat(sha1.getValue(), is("7F746EBE0D61189A8BB2185C82C82AB8B1EEFB2C"));
    }
}
