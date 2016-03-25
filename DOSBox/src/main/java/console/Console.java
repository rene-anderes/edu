/*
 * Course Agile Software Development
 */
package console;

import filesystem.Drive;
import invokerService.CommandInvokerService;

import java.io.IOException;

import command.framework.Outputter;

/**
 * Implements a console. The user is able to input command strings and receives
 * the output directly on that console. Configures the Invoker, the Commands and
 * the Filesystem.
 */
public class Console {
    private CommandInvokerService invoker;
    private Drive drive;
    protected Outputter outputter;

    /**
     * Constructor.
     * 
     * @param invoker
     *            reference to the invoker used.
     * @param drive
     *            reference to the drive from which the prompt is taken.
     */
    public Console(CommandInvokerService invoker, Drive drive) {
        this.invoker = invoker;
        this.drive = drive;
        this.outputter = new ConsoleOutputter();
    }

    /**
     * Processes input from the console and invokes the invoker until 'exit' is
     * typed.
     */
    public void processInput() {
        printHeader();

        String line = new String();
        while (notExitCommand(line)) {

            this.outputter.newline();
            this.outputter.print(this.drive.getPrompt());
            line = readInput();

            invoker.executeCommand(line, this.outputter);
        }
        printExitMessage();
        this.drive.save();
    }

    private boolean notExitCommand(String line) {
        return line.trim().compareToIgnoreCase("exit") != 0;
    }

    private void printExitMessage() {
        this.outputter.printLn("\nGoodbye!");
    }

    private void printHeader() {
        this.outputter.printLn("Pentagon Shell [Version 2010-08-12]");
        this.outputter.printLn("(C) Copyright 2012.");
    }

    protected String readInput() {
        StringBuilder input = new StringBuilder();
        int readChar = 0;

        try {
            while (readChar != '\n') {
                readChar = System.in.read();
                input.append((char) readChar);
            }
        } catch (IOException e) {
            // do nothing by intention
        }
        return input.toString();
    }
}
