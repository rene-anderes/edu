package command.library;

import command.framework.Command;
import command.framework.Outputter;

import filesystem.Directory;
import filesystem.Drive;
import filesystem.FileSystemItem;

/**
 * Rename files and directories.
 * 
 * At the moment only renames in the current directory are working.
 * 
 * Command parameters:
 * <ul>
 * <li>source file/directory</li>
 * <li>destination file/directory</li>
 * </ul>
 * 
 * @author ris,tb
 */
public class CmdRename extends Command {

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    public CmdRename(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number == 2;
    }

    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        final Directory cur = getDrive().getCurrentDirectory();

        final String source = getParameters().get(0);
        String dest = getParameters().get(1);
        if (dest.endsWith("\\")) {
            dest = dest.substring(0, dest.length() - 1);
        }
        if (dest.endsWith("\\")) {
            outputter.printLn("Invalid Destination.");
            return false;
        }

        if (source.contains("\\") || dest.contains("\\")) {
            outputter.printLn("Only renames in the current directory are allowed.");
            return false;
        }

        final FileSystemItem sourceItem = getDrive().getItemFromPath(cur.getPath() + "\\" + source);
        final FileSystemItem destItem = getDrive().getItemFromPath(cur.getPath() + "\\" + dest);

        if (sourceItem == null) {
            outputter.printLn("Source not found");
            return false;
        }

        if (destItem != null) {
            outputter.printLn("Destination already exists");
            return false;
        }

        return true;
    }

    @Override
    public void execute(Outputter outputter) {
        final Directory cur = getDrive().getCurrentDirectory();

        final String source = getParameters().get(0);
        String dest = getParameters().get(1);
        if (dest.endsWith("\\")) {
            dest = dest.substring(0, dest.length() - 1);
        }

        final FileSystemItem sourceItem = getDrive().getItemFromPath(cur.getPath() + "\\" + source);

        sourceItem.setName(dest);
    }
}
