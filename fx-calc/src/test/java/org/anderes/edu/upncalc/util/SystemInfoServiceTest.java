package org.anderes.edu.upncalc.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SystemInfoServiceTest {
    
    private SystemInfoService service;

    @Before
    public void setup() {
        service = new SystemInfoService();
    }
    
    @Test @Ignore("läuft mittels mvn test nicht, da das manifest.mf leer ist.")
    public void shouldBeImplementationVersion() {
        // when
        final String version = service.getImplementationVersion();
        
        // then
        assertThat(version, is(not(nullValue())));
        assertThat(version, is("1.0.0-RELEASE"));
    }

    @Test
    public void shouldBeRuntimeVersion() {
        // when
        final String version = service.getJREVersion();
        
        // then
        assertThat(version, is(not(nullValue())));
        assertThat(version, is("1.8.0_101"));
    }
}
