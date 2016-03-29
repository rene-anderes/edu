package command.library;

import command.framework.Command;
import command.framework.Outputter;
import filesystem.Drive;

/**
 * Ver(sion) Command, shows the application name, authors and version.
 * 
 * Command Parameters:
 * <ul>
 * <li>(optional)/w - show only the version</li>
 * </ul>
 * 
 * @author ris,tb
 */
public class CmdVer extends Command {

    public static final String APPNAME = "DOSBox";
    public static final String AUTHOR_LABEL = "Author: ";
    public static final String VERSION_LABEL = "Version: ";
    public static final String VERSION = "1.0";
    public static final String AUTHORS = "Thomas, Rico, Marcel (*2), René, Röbi";

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    protected CmdVer(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number == 0 || number == 1;
    }

    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        if (this.getParameters().size() > 0) {
            return "/w".equalsIgnoreCase(this.getParameters().get(0));
        }
        return true;
    }

    @Override
    public void execute(Outputter outputter) {
        boolean compact = false;
        if (this.getParameters().size() > 0) {
            if ("/w".equalsIgnoreCase(this.getParameters().get(0))) {
                compact = true;
            }
        }

        if (compact) {
            outputter.printLn(VERSION);
        } else {
            outputter.printLn(APPNAME);
            outputter.printLn(AUTHOR_LABEL + AUTHORS);
            outputter.printLn(VERSION_LABEL + VERSION);
        }
    }

}
