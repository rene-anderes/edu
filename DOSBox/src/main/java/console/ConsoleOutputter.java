/*
 * Course Agile Software Development
 */
package console;

import java.io.IOException;

import command.framework.Outputter;

/**
 * Implements the outputter interface that all text is sent to the console
 * (System.out).
 */
public class ConsoleOutputter implements Outputter {

    public void newline() {
        System.out.println("");
    }

    public void print(String text) {
        System.out.print(text);
    }

    public void printLn(String line) {
        System.out.println(line);
    }

    public char readSingleCharacter() {
        int in = 0;
        int readChar = 0;

        try {
            while (in != '\n') {
                if (in != '\n' && in != '\r') // do not consider \r and \n
                    readChar = in;
                in = System.in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            readChar = 0;
        }
        return (char) readChar;
    }
}
