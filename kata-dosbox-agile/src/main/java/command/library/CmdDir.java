/*
 * Course Agile Software Development
 */
package command.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import command.framework.Command;
import command.framework.Outputter;
import filesystem.Directory;
import filesystem.Drive;
import filesystem.FileSystemItem;

/**
 * Lists all of the available files and sub directories inside the current 
 * directory. 
 */
class CmdDir extends Command {

    private Directory dirToPrint;

    public CmdDir(String name, Drive drive) {
        super(name, drive);
    }

    @Override
    public boolean checkNumberOfParameters(int number) {
        if (number == 0 || number == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkParameterValues(Outputter outputter) {
        this.dirToPrint = this.getDrive().getCurrentDirectory();
        for (int i = 0; i < this.getParameters().size(); i++) {
            String dirPath = this.getParameters().get(i);
            FileSystemItem fs = this.getDrive().getItemFromPath(dirPath);
            if (fs == null) {
                outputter.printLn("File or Directory Not Found");
                return false;
            }
            if (fs.isDirectory() == false) {
                this.dirToPrint = fs.getParent();
            } else {
                this.dirToPrint = (Directory) fs;
            }
        }
        return true;
    }

    @Override
    public void execute(Outputter outputter) {
        ArrayList<FileSystemItem> content = this.dirToPrint.getContent();
        Collections.sort(content, new FileDirComparator());
        printDirectory(outputter, content, this.dirToPrint);
        outputter.printLn("\t" + this.dirToPrint.getNumberOfDirectories() + " Dir(s)");
    }

    private void printDirectory(Outputter outputter, ArrayList<FileSystemItem> content, Directory dir) {
        outputter.printLn("Directory of " + dir);
        outputter.newline();

        Iterator<FileSystemItem> it = content.iterator();
        printStandardItem(outputter, it);
        outputter.printLn("\t" + dir.getNumberOfFiles() + " File(s)");
    }

    private void printStandardItem(Outputter outputter, Iterator<FileSystemItem> it) {
        FileSystemItem item;
        while (it.hasNext()) {
            item = it.next();
            printStandardItem(outputter, item);
        }
    }

    private void printStandardItem(Outputter outputter, FileSystemItem item) {
        if (item.isDirectory() == true) {
            outputter.print("<DIR>");
        } else {
            outputter.print("" + item.getSize());
        }
        outputter.print("\t" + item.getName());
        outputter.newline();
    }

    /* package */ static class FileDirComparator implements Comparator<FileSystemItem> {

        @Override
        public int compare(FileSystemItem item1, FileSystemItem item2) {
            if (item1 == null) {
                return -1;
            }
            if (item2 == null) {
                return 1;
            }
            if (item1.isDirectory()) {
                if (item2.isDirectory()) {
                    return item1.getName().compareTo(item2.getName());
                } else {
                    return -1;
                }
            } else {
                if (item2.isDirectory()) {
                    return 1;
                }
                return item1.getName().compareTo(item2.getName());
            }
        }
    }
}
