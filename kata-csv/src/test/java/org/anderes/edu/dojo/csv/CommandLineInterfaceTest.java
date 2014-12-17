package org.anderes.edu.dojo.csv;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.anderes.edu.dojo.csv.CommandLineInterface.Command;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandLineInterfaceTest {

    private ByteArrayOutputStream outputStream;
    private byte[] input;
    private CommandLineInterface cli;

    @Before
    public void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        input = new byte[2];
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
        cli = new CommandLineInterface(outputStream, inputStream);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldBeFirstPage() {
        input[0] = 'f';
        final Command command = cli.showAndWait();
        assertThat(outputStream.toString(), is("N(ext page, P(revious page, F(irst page, L(ast page, eX(it\n"));
        assertThat(command, is(Command.FIRST));
    }
    
    @Test
    public void shouldBeLastPage() {
        input[0] = 'l';
        final Command command = cli.showAndWait();
        assertThat(outputStream.toString(), is("N(ext page, P(revious page, F(irst page, L(ast page, eX(it\n"));
        assertThat(command, is(Command.LAST));
    }

    @Test
    public void shouldBeNextPage() {
        input[0] = 'n';
        final Command command = cli.showAndWait();
        assertThat(outputStream.toString(), is("N(ext page, P(revious page, F(irst page, L(ast page, eX(it\n"));
        assertThat(command, is(Command.NEXT));
    }
    
    @Test
    public void shouldBePrevPage() {
        input[0] = 'p';
        final Command command = cli.showAndWait();
        assertThat(outputStream.toString(), is("N(ext page, P(revious page, F(irst page, L(ast page, eX(it\n"));
        assertThat(command, is(Command.PREV));
    }
    
    @Test
    public void shouldBeExit() {
        input[0] = 'x';
        final Command command = cli.showAndWait();
        assertThat(outputStream.toString(), is("N(ext page, P(revious page, F(irst page, L(ast page, eX(it\n"));
        assertThat(command, is(Command.EXIT));
    }
}
