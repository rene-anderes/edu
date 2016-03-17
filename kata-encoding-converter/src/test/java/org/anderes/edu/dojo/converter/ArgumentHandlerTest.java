package org.anderes.edu.dojo.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;

import org.junit.Test;

public class ArgumentHandlerTest {
    
    @Test
    public void shouldBeAllArguments() {
        
        // given
        String[] args = { "pom.xml", "-d", "d:\\temp", "-r", "-convertFrom", "ISO-8859-1", "-convertTo", "UTF-8" };

        // when
        final ArgumentHandler argument = new ArgumentHandler(args);
        
        // then
        assertThat(argument.getFile().isPresent(), is(true));
        assertThat(argument.getFile().get(), is("pom.xml"));
        assertThat(argument.isRecursive(), is(true));
        assertThat(argument.getConvertFrom().isPresent(), is(true));
        assertThat(argument.getConvertFrom().get(), is("ISO-8859-1"));
        assertThat(argument.getConvertTo().isPresent(), is(true));
        assertThat(argument.getConvertTo().get(), is("UTF-8"));
        assertThat(argument.getDirectory().isPresent(), is(true));
        assertThat(argument.getDirectory().get(), is(Paths.get("d:\\temp")));
    }

    @Test
    public void shouldBeNotAllArguments() {
        
        // given
        String[] args = { "pom.xml", "-convertFrom", "ISO-8859-1", "-convertTo", "UTF-8" };
        
        // when
        final ArgumentHandler argument = new ArgumentHandler(args);
        
        // then
        assertThat(argument.getFile().isPresent(), is(true));
        assertThat(argument.getFile().get(), is("pom.xml"));
        assertThat(argument.isRecursive(), is(false));
        assertThat(argument.getConvertFrom().isPresent(), is(true));
        assertThat(argument.getConvertFrom().get(), is("ISO-8859-1"));
        assertThat(argument.getConvertTo().isPresent(), is(true));
        assertThat(argument.getConvertTo().get(), is("UTF-8"));
        assertThat(argument.getDirectory().isPresent(), is(false));
    }
    
    @Test
    public void shouldBePathWithQuote() {
        
        // given
        String[] args = { "-d", "\"d:\\temp\\Test Files\"" };
        
        // when
        final ArgumentHandler argument = new ArgumentHandler(args);
        
        // then
        assertThat(argument.getDirectory().isPresent(), is(true));
        assertThat(argument.getDirectory().get(), is(Paths.get("d:\\temp\\Test Files")));
    }
    
    @Test
    public void shouldBeCheckArgumentOK() {

        // given
        String[] args = { "pom.xml", "-convertFrom", "ISO-8859-1", "-convertTo", "UTF-8" };
     
        // when
        final ArgumentHandler argument = new ArgumentHandler(args);
        
        // then
        assertThat(argument.checkArguments(), is(true));
        
    }
    
    @Test
    public void shouldBeCheckArgumentNOK() {

        // given
        String[] args = { "pom.xml", "ISO-8859-1", "-convertTo", "UTF-8" };
     
        // when
        final ArgumentHandler argument = new ArgumentHandler(args);
        
        // then
        assertThat(argument.checkArguments(), is(false));
        
    }
}
