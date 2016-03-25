/*
 * Course Agile Software Development
 */
package command.framework;

/**
 * This interface needs to be implemented by a class that outputs the passed
 * strings to either a console, a TCP/IP-Port, to a file, etc. Currently, output
 * to a console is supported only. This interface is used to inverse the
 * dependency from invoker to commands. With this interface, the commands do not
 * need to know a particular invoker to output strings.
 */
public interface Outputter {

    /**
     * Outputs the string and adds a newline at the end. If a newline is already
     * passed, two newlines are output.
     * 
     * @param line
     *            string to output. No ending newline character required.
     */
    public void printLn(String line);

    /**
     * Outputs the string. Does not add a newline at the end.
     * 
     * @param text
     *            string to output.
     */
    public void print(String text);

    /**
     * Outputs a newline.
     */
    public void newline();

    /**
     * Reads a single character from the channel May be used to ask the user
     * Yes/No questions.<br>
     * 
     * Note: This function does not return until user entered a character. It
     * may be that calling this function causes a deadlock! <br>
     * Usage:<br>
     * &nbsp;&nbsp;&nbsp; outputter.printLn("Overwrite (Yes/No/All)?:");<br>
     * &nbsp;&nbsp;&nbsp; char in = outputter.readSingleCharacter();<br>
     * &nbsp;&nbsp;&nbsp; if (in != 'y' && in != 'Y') {<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return;<br>
     * &nbsp;&nbsp;&nbsp; }<br>
     * 
     * @return character read
     */
    public char readSingleCharacter();
}
