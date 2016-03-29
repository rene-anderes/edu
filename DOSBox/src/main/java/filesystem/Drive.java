/*
 * Course Agile Software Development
 */
package filesystem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class implements the access to the composition. Composite-Pattern:
 * Client<br>
 * <br>
 * Responsibilities:<br>
 * <ul>
 * <li>manipulates objects in the composition through the Component interface.
 * <br>
 * <li>owns the root directory which is the top of the directory tree.<br>
 * <li>knows the current directory on which most of the commands must be
 * performed.<br>
 * </ul>
 */
public class Drive {
    private String driveLetter;
    private String label;
    private Directory rootDir;
    private Directory currentDir;

    /**
     * Creates a new drive and a root directory.
     * 
     * @param driveLetter
     *            Name of the drive. May only contain a single uppercase letter.
     *            If longer name given, only the first character is taken.
     */
    public Drive(String driveLetter) {
        // TODO: How to convert parameter from char to String?
        this.driveLetter = driveLetter.substring(0, 1);
        this.driveLetter = this.driveLetter.toUpperCase();
        this.label = new String("");
        this.rootDir = new Directory(this.driveLetter + ":");
        this.currentDir = this.rootDir;
    }

    /**
     * Returns the object of the root directory.
     * 
     * @return root directory
     */
    public Directory getRootDirectory() {
        return this.rootDir;
    }

    /**
     * Returns the object of the current directory.
     * 
     * @return current directory
     */
    public Directory getCurrentDirectory() {
        return this.currentDir;
    }

    /**
     * Changes the current directory. The given directory must be part of the
     * drive's directory structure, otherwise the current directory remains
     * unchanged.
     * 
     * @param dir
     *            directory which should become the current directory
     * @return false, is the directoy not part of the drive's structure
     */
    public boolean setCurrentDirectory(Directory dir) {
        if (this.getItemFromPath(dir.getPath()) == dir) {
            this.currentDir = dir;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the drive name with a ending ':'. E.g. "C:"
     * 
     * @return the drive name
     */
    public String getDriveName() {
        return this.driveLetter + ":";
    }

    /**
     * Returns the DOS-Prompt, drive name with an ending '&gt;' and a space. E.g.
     * <code>"C:&gt; "</code>
     * 
     * @return DOS-Prompt
     */
    public String getPrompt() {
        return this.currentDir.getPath() + "> ";
    }

    /**
     * Same as getDriveName()
     */
    @Override
    public String toString() {
        return this.getDriveName();
    }

    /**
     * Changes the drive label of the drive
     * 
     * @param label
     *            new drive label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the drive label
     * 
     * @return drive label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the object of a given path name.<br>
     * <br>
     * Example:<br>
     * <code>getItemFromPath("C:\\temp\\aFile.txt");</code><br>
     * Returns the FileSystemItem-object which abstracts aFile.txt in the temp
     * directory.<br>
     * <br>
     * Remarks:<br>
     * <ul>
     * <li>Always use "\\" for backslashes since the backslash is used as escape
     * character for Java strings.
     * <li>This operation works for relative paths (temp\\aFile.txt) too. The
     * lookup starts at the current directory.
     * <li>This operation works for forward slashes '/' too.
     * <li>".." and "." are supported too. <br>
     * </ul>
     * 
     * @param givenItemPath
     *            Path for which the item shall be returned
     * @return FileSystemObject or null if no path found.
     */
    public FileSystemItem getItemFromPath(final String givenItemPath) {
        // Remove any "/" with "\"
        String givenItemPathPatched = givenItemPath.replace('/', '\\');
        givenItemPathPatched = givenItemPathPatched.trim();
        if (givenItemPathPatched.charAt(givenItemPathPatched.length() - 1) == '\\' && givenItemPathPatched.length() >= 2) {
            givenItemPathPatched = givenItemPathPatched.substring(0, givenItemPathPatched.length() - 1);
        }

        String parts[] = givenItemPathPatched.split("\\\\");
        FileSystemItem currentPos;
        if (givenItemPathPatched.startsWith("\\") || (givenItemPathPatched.length() >= 2 && givenItemPathPatched.charAt(1) == ':')) {
            // absolute
            currentPos = getRootDirectory();
        } else {
            // relative
            currentPos = getCurrentDirectory();
        }

        int item = 0;
        for (final String part : parts) {
            if (part.length() == 0 || (part.length() >= 2 && part.charAt(1) == ':') || part.equals(".")) {
                // nothing
            } else if (part.equals("..")) {
                currentPos = (FileSystemItem) currentPos.getParent();
                if (currentPos == null) {
                    currentPos = getRootDirectory();
                }
            } else {
                currentPos = getItemFromDirectory(part, (Directory) currentPos);
                if (currentPos == null) {
                    return null;
                }
                if (!currentPos.isDirectory()) {
                    if (item != parts.length - 1) {
                        return null;
                    } else {
                        return currentPos;
                    }
                }
            }
            item += 1;
        }

        return currentPos;
    }

    /**
     * Helper for getItemFromPath()
     */
    private FileSystemItem getItemFromDirectory(String givenItemName, Directory directoryToLookup) {
        ArrayList<FileSystemItem> content = directoryToLookup.getContent();

        Iterator<FileSystemItem> it = content.iterator();
        FileSystemItem item;
        FileSystemItem retVal;
        String pathName;
        while (it.hasNext()) {
            item = it.next();
            pathName = item.getPath();
            if (pathName.equalsIgnoreCase(givenItemName)) {
                return item;
            }
            if (item.getName().equalsIgnoreCase(givenItemName)) {
                return item;
            }
            if (item.isDirectory()) {
                retVal = this.getItemFromDirectory(givenItemName, (Directory) item);
                if (retVal != null) {
                    return retVal;
                }
            }
        }
        return null;
    }

    /**
     * Builds up a directory structure from the given path on a real drive.
     * Subdirectories become directories and subdirectories Files in that
     * directory and the subdirectories become files, content is set to full
     * path, filename and size of that file.
     * 
     * Example:<br>
     * <code>
     * C:\temp<br>
     * +-- MyFile1.txt (size 112000 Bytes)<br>
     * +-- MyFile2.txt (50000)<br>
     * +-- SubDir1 (Dir)<br>
     * ....+-- AnExecutable.exe (1234000)<br>
     * ....+-- ConfigFiles (Dir)<br>
     * </code> <br>
     * Results in<br>
     * <ul>
     * <li>All files and subdirectories of the root directory deleted
     * <li>Current directory set to root directory
     * <li>File MyFile1.txt added to root directory with content
     * "C:\temp\MyFile1.txt, size 112000 Bytes"
     * <li>File MyFile2.txt added to root directory with content
     * "C:\temp\MyFile2.txt, size 50000 Bytes"
     * <li>Directory SubDir1 added to root directory
     * <li>File AnExecutable.exe added to SubDir1 with content
     * "C:\temp\SubDir1\AnExecutable.exe, size 1234000 Bytes"
     * <li>Directory ConfigFiles added to SubDir1 <br>
     * </ul>
     * 
     * @param path
     *            Path which points to a directory
     */
    public void createFromRealDirectory(String path) {
        // Not yet implemented
    }

    /**
     * Stores a directory structure persistently. Is called when "exit" is
     * called. Hint: Stores the object stream in a fixed file.
     */
    public void save() {
        // Not yet implemented
    }

    /**
     * Creates a directory structure from an object stream. Is called at startup
     * of the application.
     */
    public void restore() {
        // Not yet implemented
    }
}
