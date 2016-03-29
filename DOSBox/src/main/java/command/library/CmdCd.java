/*
 * Course Agile Software Development
 */
package command.library;

import command.framework.Command;
import command.framework.Outputter;

import filesystem.Directory;
import filesystem.Drive;
import filesystem.FileSystemItem;

/**
 * Command to change current directory. Example for a command with optional
 * parameters.<br>
 * <br>
 * Pattern: Command, Concrete Command
 */
class CmdCd extends Command {

    private static final String SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED = "The system cannot find the path specified";
    private String newDirectoryName;

    /**
     * Constructor.
     */
    protected CmdCd(String name, Drive drive) {
        super(name, drive);
    }

    /**
     * Only returns true if number of parameters is either 0 or 1.<br>
     * 0: outputs the current directory.<br>
     * 1: changes current directory to the given directory.
     */
    @Override
    public boolean checkNumberOfParameters(int number) {
        if (number == 0 || number == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkParameterValues(Outputter outputter) {
        if (this.getParameters().size() > 0) {
            this.newDirectoryName = this.getParameters().get(0);
        }

        return true;
    }

    @Override
    public void execute(Outputter outputter) {
        boolean retVal = false;

        // cd without parameters
        if (this.newDirectoryName == null) {
            outputter.printLn(this.getDrive().getCurrentDirectory().getPath());
            return;
        }

        // cd with parameters: Check if passed directory is valid before change
        // to this directory
        FileSystemItem newDir = this.getDrive().getItemFromPath(this.newDirectoryName);
        if (newDir == null) {
            outputter.printLn(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
            return;
        }

        if (newDir.isDirectory() == false) {
            outputter.printLn(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
            return;
        }

        if (this.getDrive().getItemFromPath(newDir.getPath()) != newDir) {
            outputter.printLn("Path not in drive " + this.getDrive().getDriveName());
            return;
        }

        if (newDir.isDirectory()) {
            retVal = this.getDrive().setCurrentDirectory((Directory) newDir);
        }

        if (retVal == false) {
            outputter.printLn(SYSTEM_CANNOT_FIND_THE_PATH_SPECIFIED);
        }
    }

}
