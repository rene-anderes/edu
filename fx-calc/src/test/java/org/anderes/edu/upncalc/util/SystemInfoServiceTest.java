package org.anderes.edu.upncalc.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SystemInfoServiceTest {
    
    private SystemInfoService service;

    @Before
    public void setup() {
        service = new SystemInfoService();
    }
    
    @Test
    public void shouldBeImplementationVersion() {
        // when
        final String version = service.getImplementationVersion();
        
        // then
        assertThat(version, is(not(nullValue())));
        assertThat(version.length() >= 5, is(true));
    }

    @Test
    public void shouldBeRuntimeVersion() {
        // when
        final String version = service.getJREVersion();
        
        // then
        assertThat(version, is(not(nullValue())));
        assertThat(version.startsWith("1.8"), is(true));
    }
}
