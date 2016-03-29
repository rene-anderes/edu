/*
 * Course Agile Software Development
 */
package command.library;

import command.framework.Command;
import command.framework.Outputter;
import filesystem.Drive;
import filesystem.File;

/**
 * Create a file with/without content
 * 
 * Two parameters for this command:
 * <ul>
 * <li>filename</li>
 * <li>new file content</li>
 * </ul>
 */
class CmdMkFile extends Command {

    public CmdMkFile(String cmdName, Drive drive) {
        super(cmdName, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {
        return number == 1 || number == 2;
    }

    @Override
    public void execute(Outputter outputter) {
        String fileName = this.getParameters().get(0);
        String fileContent = "";
        if (this.getParameters().size() > 1) {
            fileContent = this.getParameters().get(1);
        }

        File newFile = new File(fileName, fileContent);
        this.getDrive().getCurrentDirectory().add(newFile);
    }
}
