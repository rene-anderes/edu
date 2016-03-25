/*
 * Course Agile Software Development
 */
package command.library;

import java.util.Iterator;

import command.framework.Command;
import command.framework.Outputter;

import filesystem.Directory;
import filesystem.Drive;

class CmdMkDir extends Command {

    protected CmdMkDir(String name, Drive drive) {
        super(name, drive);
    }

    @Override
    protected boolean checkNumberOfParameters(int number) {

        // Commands like "mkdir dir1 dir2 dir3" are allowed too.
        if (number < 1)
            return false;

        return true;
    }

    @Override
    protected boolean checkParameterValues(Outputter outputter) {
        Iterator<String> it = this.getParameters().iterator();

        String parameter = null;
        while (it.hasNext()) {
            parameter = it.next();
            parameter = parameter.replace('/', '\\');

            // Do not allow "mkdir c:\temp\dir1" to keep the command simple
            if (parameter.contains("\\")) {
                outputter.printLn("At least one parameter denotes a path rather than a directory name.");
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(Outputter outputter) {
        Iterator<String> it = this.getParameters().iterator();

        String newDirName;
        while (it.hasNext()) {
            newDirName = it.next();

            Directory newDir = new Directory(newDirName);
            this.getDrive().getCurrentDirectory().add(newDir);
        }
    }
}
