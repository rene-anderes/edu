/*
 * Course Agile Software Development
 */
package filesystem;

import java.util.ArrayList;

/**
 * This class abstracts File and Directory.<br>
 * Composite-Pattern: Component<br>
 * <br>
 * Responsibilities:<br>
 * <ul>
 * <li>declares the common interface for objects in the composition. This is:
 * <blockquote>
 * - get/setName(), toString()<br>
 * - getPath()<br>
 * - isDirectory()<br>
 * - getNumberOfFiles(), getNumberOfDirectories()<br>
 * - getSize() 
 * </blockquote>
 * <li>implements default behavior for the interface common to all classes, as
 * appropriate.
 * <li>declares an interface for accessing and managing its child components.
 * This is <blockquote> - getContent() </blockquote>
 * <li>defines an interface for accessing a component's parent in the recursive
 * structure, and implements it if that's appropriate. This is <blockquote> -
 * getParent() </blockquote>
 * </ul>
 */
public abstract class FileSystemItem {

    private String name;
    private Directory parent;
    private static final String ILLEGAL_ARGUMENT_TEXT = "Error: A file or directory name may not contain '/', '\', ',', ' ' or ':'";

    protected FileSystemItem(String name, Directory parent) throws IllegalArgumentException {
        if (this.checkName(name) == false) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_TEXT);
        }
        this.name = name;
        this.parent = parent;
    }

    /**
     * Returns the name of the item. Only returns the name, not the path. The
     * path must be obtained by getPath.
     * 
     * @return Name of item - file name if isDirectory() == false - directory
     *         name if isDirectory()
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the item.
     * 
     * @param newName
     *            new name of the item. May not contain any ':', '/', ',', ' '
     *            or '\' in the name. Otherwise, the name is not changed and an
     *            IllegalArgumentException is thrown.
     * @throws IllegalArgumentException wrong argument
     */
    public void setName(String newName) throws IllegalArgumentException {
        if (this.checkName(newName) == false) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_TEXT);
        }

        this.name = newName;
    }

    /**
     * Checks whether a file or directory name contains illegal characters.
     * 
     * @param name
     *            Name to check
     * @return - true name is valid - false name contains at least one illegal
     *         character.
     */
    protected boolean checkName(String name) {
        if (name.contains("\\") || name.contains("/") || name.contains(",") || name.contains(" ")) {
            return false;
        }

        return true;
    }

    /**
     * Returns the full path of the item
     * 
     * @return Full path, e.g. "C:\thisdir\thatdir\file.txt"
     */
    public String getPath() {
        String path = new String();

        if (parent != null) {
            path = parent.getPath() + "\\" + this.name;
        } else { // For root directory
            path = this.name;
        }

        return path;
    }

    /**
     * Returns the full path of the item See getPath()
     */
    @Override
    public String toString() {
        return getPath();
    }

    /**
     * Returns the content of the item.
     * 
     * @return - the list of contained files and directories if isDirectory() -
     *         null if isDirectory() == false
     */
    abstract public ArrayList<FileSystemItem> getContent();

    /**
     * Returns the parent directory. If this item is the root directory (the top
     * most directory), null is returned.
     * 
     * @return Parent directory or null if this item is already the top most
     *         directory.
     */
    public Directory getParent() {
        return this.parent;
    }

    /**
     * Changes the parent; only accessible for subclasses. Use Directory.add()
     * otherwise
     * 
     * @param parent
     *            new parent directory. No checks are made if parent belongs to
     *            drive or if it is null
     */
    protected void setParent(Directory parent) {
        this.parent = parent;
    }

    /**
     * Returns whether this item is a directory or not
     * 
     * @return - true this item is a directory - false this item is not a
     *         directory
     */
    abstract public boolean isDirectory();

    /**
     * Returns the number of files contained by this item
     * 
     * @return number of contained files if isDirectory() 0 if isDirectory() ==
     *         false
     */
    public abstract int getNumberOfFiles();

    /**
     * Returns the number of directories contained by this item
     * 
     * @return number of contained directories if isDirectory() 0 if
     *         isDirectory() == false
     */
    public abstract int getNumberOfDirectories();

    /**
     * Returns the size of the item
     * 
     * @return the size in bytes of the file (string length of the content) if
     *         isDirectory() == false 0 if isDirectory()
     */
    public abstract int getSize();
}
