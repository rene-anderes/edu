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
 * Format Command.
 * 
 * After this command no files or directories remain in the root.
 * 
 * Command Parameters:
 * <ul>
 * <li>(optional) /F : force format, e.g. no security check</li>
 * <li>(optional) drive letter</li>
 * </ul>
 */
public class CmdFormat extends Command {

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    public CmdFormat(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number >= 0 && number <= 2;
    }

    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        boolean ret = true;
        if (getParameters().size() == 1) {
            ret = hasForceParameter();
            if (!ret) {
                ret = getDrive().getDriveName().equalsIgnoreCase(getParameters().get(0));
                if (!ret)
                    outputter.printLn("specified drive does not exist");
            }
        } else if (getParameters().size() == 2) {
            ret = hasForceParameter();
            if (ret) {
                ret = getDrive().getDriveName().equalsIgnoreCase(getParameters().get(1));
                if (!ret)
                    outputter.printLn("specified drive does not exist");
            }
        }
        return ret;
    }

    @Override
    public void execute(Outputter outputter) {
        boolean force = false;
        if (getParameters().size() == 1) {
            if (hasForceParameter()) {
                // format /f
                force = true;
            }
        } else if (getParameters().size() == 2) {
            force = true;
        }

        // security question
        if (!force) {
            outputter.printLn("Format (Y/N)?:");
            char in = outputter.readSingleCharacter();
            if (in != 'y' && in != 'Y') {
                return;
            }
        }

        getDrive().setCurrentDirectory(getDrive().getRootDirectory());
        deleteRecursive(getDrive().getRootDirectory());
    }

    private boolean hasForceParameter() {
        return "/f".equalsIgnoreCase(getParameters().get(0));
    }

    /**
     * Delete all files and directories recursively from the given base
     * directory.
     * 
     * @param dir
     *            the base directory from which to delete
     */
    private void deleteRecursive(final Directory dir) {
        while (dir.getContent().size() > 0) {
            final FileSystemItem item = dir.getContent().get(0);
            if (item.isDirectory()) {
                deleteRecursive((Directory) item);
            }
            dir.remove(item);
        }
    }
}
