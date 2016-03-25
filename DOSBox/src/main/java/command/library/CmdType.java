package command.library;

import command.framework.Command;
import command.framework.Outputter;

import filesystem.Drive;
import filesystem.File;
import filesystem.FileSystemItem;

/**
 * Type/Cat Command, displays the content of a text file on the screen.
 * 
 * Text files are detected by the file extension. Known extensions are at the
 * moment: .txt, .html, .xml
 *
 * Command Parameters:
 * <ul>
 * <li>relative or absolute path to the file</li>
 * </ul>
 *
 * @author ris,tb
 */
public class CmdType extends Command {

    private static final String[] EXTS = { ".txt", ".html", ".xml" };

    /**
     * Constructor.
     * 
     * @param cmdName
     *            Name on which the command reacts. This name is automatically
     *            converted to lower case letters.
     * @param drive
     *            Drive on which the command operates.
     */
    public CmdType(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number == 1;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        final String filePath = getParameters().get(0);
        final FileSystemItem file = getDrive().getItemFromPath(filePath);
        if (file == null) {
            outputter.printLn("Cannot find the file specified.");
            return false;
        }

        if (file.isDirectory()) {
            outputter.printLn("Cannot cat a directory.");
            return false;
        }

        boolean validType = false;
        final String filename = getParameters().get(0).toLowerCase();
        for (final String suffix : EXTS) {
            if (filename.endsWith(suffix)) {
                validType = true;
            }
        }

        if (!validType)
            outputter.printLn("Invalid filetype.");

        return validType;
    }

    @Override
    public void execute(Outputter outputter) {
        final File file = (File) getDrive().getItemFromPath(getParameters().get(0));
        outputter.printLn(file.getFileContent());
    }
}
