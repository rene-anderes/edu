package org.anderes.edu.dojo.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Optional;

public class CommandLineInterface {
    
    public enum Command { 
        NEXT("N"), PREV("P"), FIRST("F"), LAST("L"), EXIT("X");
        
        private String key;
        
        private Command(final String key) {
            this.key = key;
        }
        
        public static Optional<Command> eval(final String command) {
            for (Command c : Command.values()) {
                if (c.getKey().equalsIgnoreCase(command)) {
                    return Optional.of(c);
                }
            }
            return Optional.empty();
        }

        private String getKey() {
            return key;
        }
    };
    
    private OutputStream outputStream;
    
    public CommandLineInterface(final OutputStream outputStream) {
        super();
        this.outputStream = outputStream;
    }
    
    public Command showAndWait() {
        writeCommands();
        Optional<Command> command = Optional.empty();
        do {
            command = Command.eval(readFromConsole());
        } while (!command.isPresent());
        return command.get();
    }
    
    private void writeCommands() {
        final OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        try {
            writer.append("N(ext page, P(revious page, F(irst page, L(ast page, eX(it").append('\n').flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String readFromConsole() {
        final BufferedReader consoleInputReader = new BufferedReader(new InputStreamReader(System.in));
        String consoleInput = "";
        try {
            consoleInput = consoleInputReader.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Eingabe: " + consoleInput.substring(0, 1));
        return consoleInput.substring(0, 1);
    }

}
