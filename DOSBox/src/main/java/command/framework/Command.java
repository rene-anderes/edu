/*
 * Course Agile Software Development
 */
package command.framework;

import java.util.ArrayList;

import filesystem.Drive;

/**
 * Abstract class for all commands.<br>
 * Command-Pattern: Command.<br>
 * <br>
 * Responsibilities:
 * <ul>
 * <li>declares an interface for executing an operation. This is <blockquote> -
 * execute() </blockquote>
 * <li>declares an in interface to pass parameters controlling the execution.
 * This is <blockquote> - checkNumberOfParameters()<br>
 * - checkParameterValues() </blockquote>
 * <li>provides the concrete command with various information. This is
 * <blockquote> - getDrive()<br>
 * - getParameters() </blockquote>
 * <li>provides functionality to the invoker. This is <blockquote> -
 * compareCmdName()<br>
 * - setParameters()<br>
 * - checkParameters()<br>
 * - reset() </blockquote>
 * </ul>
 * Note: checkParameters() is a Template Method and checkNumberOfParameters()
 * and checkParameterValues() are the hooks for deriving classes
 */
public abstract class Command {

    /**
     * Stores the name on which the commands reacts. See compareCmdName()
     */
    private String cmdName;

    /**
     * The list of parameters passed to the command. Use getParameters() to
     * obtain this list from a concrete command.
     */
    private ArrayList<String> parameters;

    /**
     * Drive on which the command operates. Use getDrive() to obtain the drive
     * from a concrete command.
     */
    private Drive drive;

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected Command(String cmdName, Drive drive) {
        this.cmdName = cmdName.toLowerCase();
        this.drive = drive;
    }

    /**
     * Returns true if the passed name and the command name fit. Used to obtain
     * the concrete command from the list of commands.
     * 
     * @param cmdName
     *            name with which the command name shall be compared.
     * @return
     *         <ul>
     *         <li>true if names fit
     *         <li>false otherwise
     *         </ul>
     */
    public final boolean compareCmdName(String cmdName) {
        if (this.cmdName.compareTo(cmdName) == 0)
            return true;
        else
            return false;
    }

    /**
     * Returns the drive on which the command shall operate.
     * 
     * @return the drive
     */
    public final Drive getDrive() {
        return this.drive;
    }

    /**
     * Sets the list of parameters. This operation shall be called by the
     * invoker only.
     * 
     * @param params list of parameters
     */
    public final void setParameters(ArrayList<String> params) {
        this.parameters = params;
    }

    /**
     * Returns the list of parameters, e.g. to check parameters by a concrete
     * command.
     * 
     * @return the list of parameters
     */
    protected final ArrayList<String> getParameters() {
        return parameters;
    }

    /**
     * Resets the internal state of a command. Must be called after each
     * execution.
     */
    public final void reset() {
        this.parameters = null;
    }

    /**
     * Returns the name of the command.
     * 
     * @return the name of the command
     */
    @Override
    public final String toString() {
        return this.cmdName;
    }

    /**
     * Checks the passed parameters. This consists of three steps which are both
     * overrideable by concrete commands.<br>
     * 1.) Check the number of parameters.<br>
     * 2.) Check the values of all passed parameters<br>
     * 3.) Assign the parameters to attributes in the concrete commands.<br>
     * <br>
     * Template Method Pattern: Template Method.<br>
     * 
     * @param outputter
     *            The outputter must be used to output any error description.
     * @return
     *         <ul>
     *         <li>true if number and values of the parameters are correct.
     *         Execute() may use the parameters afterwards unchecked.
     *         <li>false if the number is below or above excepted range or if
     *         any value is incorrect. An explaining error message must be given
     *         by the concrete command.
     *         </ul>
     * @throws Exception
     *             Thrown if this.parameters is not set.
     */
    public final boolean checkParameters(Outputter outputter) throws Exception {
        if (this.parameters == null) {
            throw new Exception("Parameters checked before set!");
        }

        if (this.checkNumberOfParameters(this.parameters.size()) == false) {
            outputter.printLn("The syntax of the command is incorrect.");
            return false;
        }

        if (this.checkParameterValues(outputter) == false)
            return false;

        this.setParameters();

        return true;
    }

    /**
     * <b>Can be overwritten</b> by the concrete commands if something must be
     * checked. Checks if the number of parameters is in range. Do not output
     * anything, an explaining error message is output by the abstract command.
     * <br>
     * <br>
     * Template Method Pattern: Hook.<br>
     * 
     * @param number
     *            Number of parameters passed by the caller.
     * @return
     *         <ul>
     *         <li>true if number of parameters is within expected range
     *         <li>false otherwise
     *         </ul>
     */
    protected boolean checkNumberOfParameters(int number) {
        return true;
    }

    /**
     * <b>Can be overwritten</b> by the concrete commands if at least the value
     * of one parameter must be checked. Checks all values of all passed
     * parameters. An explaining error message must be output by the concrete
     * command.<br>
     * <br>
     * Template Method Pattern: Hook.<br>
     * 
     * @param outputter
     *            The outputter must be used to output error messages.
     * @return
     *         <ul>
     *         <li>true if all values of all parameters passed are correct.
     *         <li>false if at least one value of one parameter in incorrect.
     *         </ul>
     */
    protected boolean checkParameterValues(Outputter outputter) {
        return true;
    }

    /**
     * <b>Can be overwritten</b> by the concrete commands if the list of
     * parameters is very complex. Assign the parameters in the parameter-list
     * to appropriate attributes in the concrete commands.
     */
    protected void setParameters() {
    }

    /**
     * <b>Must be overwritten</b> by the concrete commands to implement the
     * execution of the command.
     * 
     * @param outputter
     *            Must be used to output any text.
     */
    public abstract void execute(Outputter outputter);
}
