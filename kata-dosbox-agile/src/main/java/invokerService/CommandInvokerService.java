/*
 * Course Agile Software Development
 */
package invokerService;

import command.framework.Outputter;

public interface CommandInvokerService {

    /**
     * Interprets a command string, executes if an appropriate command is found
     * and returns all output via the outputter interface.
     * 
     * @param command
     *            String which is entered, containing the command and the
     *            parameters.
     * @param outputter
     *            Implementation of the outputter interface to which the output
     *            text is sent.
     */
    public abstract void executeCommand(String command, Outputter outputter);
}