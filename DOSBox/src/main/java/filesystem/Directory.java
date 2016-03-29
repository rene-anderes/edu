/*
 * Course Agile Software Development
 */
package filesystem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class implements the behavior of concrete directories.
 * Composite-Pattern: Composite<br>
 * <br>
 * Responsibilities:<br>
 * <ul>
 * <li>defines behavior for components (directories) having children.
 * <li>stores child components (files and subdirectories).
 * <li>implements child-related operations in the Component interface. These
 * are: <blockquote> - getContent()<br>
 * - add(Directory), add(File)<br>
 * - getNumberOfFiles(), getNumberOfDirectories()<br>
 * </blockquote>
 * </ul>
 */
public class Directory extends FileSystemItem {

    private ArrayList<FileSystemItem> content;

    /**
     * Constructor.
     * 
     * @param name
     *            Name of the new directory. May not contain any ':', '/', ',',
     *            ' ' or '\' in the name. Otherwise, the name is not changed and
     *            an IllegalArgumentException is thrown.
     */
    public Directory(String name) {
        super(name, null);
        this.content = new ArrayList<FileSystemItem>();
    }

    /**
     * true is returned.
     */
    @Override
    public boolean isDirectory() {
        return true;
    }

    /**
     * An array list with the content of this directory (files and
     * subdirectories) is returned. The returned list may not be changed, only
     * read access is allowed.
     */
    @Override
    public ArrayList<FileSystemItem> getContent() {
        return this.content;
    }

    /**
     * Adds a new subdirectory to this directory. If the directory to add is
     * already part of another directory structure, it is removed from there.
     * 
     * @param subDir
     *            Object of the directory to be added.
     */
    public void add(Directory subDir) {
        this.content.add(subDir);
        if (subDir.getParent() != null) {
            subDir.getParent().content.remove(subDir);
        }
        subDir.setParent(this);
    }

    /**
     * Adds a new file to this directory. If the file to add is already part of
     * another directory structure, it is removed from there.
     * 
     * @param file
     *            Object of the file to be added.
     */
    public void add(File file) {
        this.content.add(file);
        if (file.getParent() != null) {
            file.getParent().content.remove(file);
        }
        file.setParent(this);
    }

    /**
     * Removes a directory or a file from current directory. Sets the parent of
     * the removed item to null, if contained in this directory.
     * 
     * @param item
     *            Directory or file to be removed from this directory. If item
     *            is not part of this directory, nothing happens. Note: If you
     *            need to remove the entire content, you cannot use an iterator
     *            since you change the list, the iterator is enumerating. Use
     *            this code instead:<br>
     *            <code>while(root.getContent().size() &gt; 0) {<br>
     *            root.remove(root.getContent().get(0));<br> 
     *            }</code>
     */
    public void remove(FileSystemItem item) {
        if (this.content.contains(item)) {
            item.setParent(null);
            this.content.remove(item);
        }
    }

    /**
     * Returns the number of files in this directory. Does not count number of
     * files in the subdirectories.
     */
    @Override
    public int getNumberOfFiles() {
        Iterator<FileSystemItem> it = this.content.iterator();
        int numberOfFiles = 0;

        while (it.hasNext()) {
            if (it.next().isDirectory() == false) {
                numberOfFiles++;
            }
        }

        return numberOfFiles;
    }

    @Override
    /**
     * Returns the number of subdirectories in this directory. Does not count
     * number of subdirectories in the subdirectories.
     */
    public int getNumberOfDirectories() {
        Iterator<FileSystemItem> it = this.content.iterator();
        int numberOfDirs = 0;

        while (it.hasNext()) {
            if (it.next().isDirectory()) {
                numberOfDirs++;
            }
        }

        return numberOfDirs;
    }

    /**
     * Returns 0 since directories have no size.
     */
    @Override
    public int getSize() {
        return 0;
    }
}
