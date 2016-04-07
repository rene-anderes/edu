/*
 * Course Agile Software Development
 */
package command.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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
    private HashSet<Option> options;

    private enum Option {
        COMPACT("[/][wW]"), DIRLIST("[/][sS]"), NONE("");
        private String regEx;

        private Option(String regEx) {
            this.regEx = regEx;
        }

        public String getRegEx() {
            return regEx;
        }
    }

    public CmdDir(String name, Drive drive) {
        super(name, drive);
        options = new HashSet<Option>();
    }

    @Override
    public boolean checkNumberOfParameters(int number) {
        if (number == 0 || number == 1 || number == 2 || number == 3)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkParameterValues(Outputter outputter) {
        this.dirToPrint = this.getDrive().getCurrentDirectory();
        this.options.clear();
        for (int i = 0; i < this.getParameters().size(); i++) {
            if (checkOption(this.getParameters().get(i))) {
                continue;
            }
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

    /**
     * Überprüft den Parameter ob es sich um eine Option des Kommandos handelt.
     * 
     * @param parameter
     *            Parameter
     * @return true -> Option des Kommandos, sonst false
     */
    private boolean checkOption(String parameter) {
        boolean found = false;
        for (Option o : Option.values()) {
            if (parameter.matches(o.getRegEx())) {
                options.add(o);
                found = true;
            }
        }
        return found;
    }

    @Override
    public void execute(Outputter outputter) {
        ArrayList<FileSystemItem> content = this.dirToPrint.getContent();
        Collections.sort(content, new FileDirComparator());
        if (options.contains(Option.DIRLIST)) {
            printAllDirectories(outputter, content, this.dirToPrint);
            outputter.print("\n");
            printSummary(outputter, dirToPrint);
        } else {
            printDirectory(outputter, content, this.dirToPrint);
            outputter.printLn("\t" + this.dirToPrint.getNumberOfDirectories() + " Dir(s)");
        }
    }

    private void printSummary(Outputter outputter, FileSystemItem item) {
        outputter.printLn("Total Files Listed:");
        outputter.printLn("\t" + fileCount(item) + " File(s) " + byteCount(item) + " bytes");
        outputter.printLn("\t" + dirCount(item) + " Dir(s)");
    }

    private int byteCount(FileSystemItem item) {
        int c = item.getSize();
        if (item.isDirectory()) {
            for (FileSystemItem it : item.getContent()) {
                c += byteCount(it);
            }
        }
        return c;
    }

    private int fileCount(FileSystemItem item) {
        int c = item.getNumberOfFiles();
        for (FileSystemItem it : item.getContent()) {
            if (it.isDirectory()) {
                c += fileCount(it);
            }
        }
        return c;
    }

    private int dirCount(FileSystemItem item) {
        int c = item.getNumberOfDirectories();
        for (FileSystemItem it : item.getContent()) {
            if (it.isDirectory()) {
                c += dirCount(it);
            }
        }
        return c;
    }

    private void printAllDirectories(Outputter outputter, ArrayList<FileSystemItem> content, Directory dir) {
        printDirectory(outputter, content, dir);
        for (FileSystemItem it : content) {
            if (it.isDirectory()) {
                outputter.print("\n");
                printAllDirectories(outputter, it.getContent(), (Directory) it);
            }
        }
    }

    private void printDirectory(Outputter outputter, ArrayList<FileSystemItem> content, Directory dir) {
        outputter.printLn("Directory of " + dir);
        outputter.newline();

        Iterator<FileSystemItem> it = content.iterator();

        if (options.contains(Option.COMPACT)) {
            printCompactItem(outputter, it);
        } else {
            printStandardItem(outputter, it);
        }
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

    private void printCompactItem(Outputter outputter, Iterator<FileSystemItem> it) {
        boolean first = true;
        FileSystemItem item;
        while (it.hasNext()) {
            if (first) {
                first = false;
            } else {
                outputter.print("\t");
            }
            item = it.next();
            printCompactItem(outputter, item);
        }
        outputter.newline();
    }

    private void printCompactItem(Outputter outputter, FileSystemItem item) {
        if (item.isDirectory()) {
            outputter.print("[");
            outputter.print(item.getName());
            outputter.print("]");
        } else {
            outputter.print(item.getName());
        }
    }

    /**
     * Comparator f�r die Sortierung der Filesystem-Item
     */
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
