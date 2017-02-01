package org.anderes.edu.upncalc.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class Utf8ControlTest {

    private ResourceBundle resourceBundle;
    
    @Before
    public void setup() {
        resourceBundle = ResourceBundle.getBundle("CalcLanguageTest", new Utf8Control());  
    }
    
    @Test
    public void shouldBeCorrectEncoding() {
        // given
        final String expectedText = "Ob Schwyz' Vamp dünkt \"Fix, quäle Jörg\"?";
        
        // when
        final String textFromResources = resourceBundle.getString("test.one");
        
        // then
        assertThat(textFromResources, is(expectedText));
        
    }
    
    @Test
    public void shouldBeCorrectCharacter() {
        // given
        final String expectedText = "Stanleys Expeditionszug quer durch Afrika wird von jedermann bewundert.";
        
        // when
        final String textFromResources = resourceBundle.getString("test.two");
        
        // then
        assertThat(textFromResources, is(expectedText));
        
    }
    
}
