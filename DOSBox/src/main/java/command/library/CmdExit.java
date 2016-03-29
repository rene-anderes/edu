package command.library;

import command.framework.Command;
import command.framework.Outputter;
import filesystem.Drive;

/**
 * Befehl zum Verlassen der DosBox.
 *
 * @author fu, mak
 */
public class CmdExit extends Command {

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected CmdExit(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    public void execute(Outputter outputter) {
        // Nothing to do...
    }

}
