package command.library;

import command.framework.Command;
import filesystem.Drive;

/**
 * Abstrakte Basisklasse für den Date und Time Command.
 * 
 * @author fu, mak
 */
public abstract class CmdDateTime extends Command {

    /** Zeitabweichung in ms verglichen zur Systemzeit */
    protected static long delta = 0;

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected CmdDateTime(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number == 0 || number == 1;
    }
}
