/*
 * Course Agile Software Development
 */
package filesystem;

import java.util.ArrayList;

/**
 * This class implements the behavior of concrete files. Composite-Pattern: Leaf
 * <br>
 * <br>
 * Responsibilities:<br>
 * <li>represents leaf objects in the composition. A leaf has no children.
 * <li>defines behavior for primitive objects in the composition.
 */
public class File extends FileSystemItem {
    private String fileContent;

    private static final String[] ILLEGAL_NAME_CHARS = { "/", "\\", ",", " ", ":" };

    /**
     * Constructor.
     * 
     * @param name
     *            A name for the file. Note that file names may not contain '\'
     *            '/' ':' ',' ';' and ' '.
     * @param fileContent
     *            Any string which represents the content of the file. The
     *            content may not contain characters like ',' and ';'.
     */
    public File(String name, String fileContent) {
        super(name, null);
        this.fileContent = fileContent;
    }

    @Override
    protected boolean checkName(String name) {
        for (final String ill : ILLEGAL_NAME_CHARS) {
            if (name.contains(ill))
                return false;
        }
        return true;
    }

    /**
     * Returns file content
     * 
     * @return file content as string
     */
    public String getFileContent() {
        return this.fileContent;
    }

    /**
     * null is returned since a files does not contain any subitems.
     */
    @Override
    public ArrayList<FileSystemItem> getContent() {
        return null;
    }

    /**
     * false is returned since a file is not a directory.
     */
    @Override
    public boolean isDirectory() {
        return false;
    }

    /**
     * 0 is returned since a files does not contain other files.
     * 
     * @return 0
     */
    @Override
    public int getNumberOfFiles() {
        return 0;
    }

    /**
     * 0 is returned since a files does not contain directories.
     * 
     * @return 0
     */
    @Override
    public int getNumberOfDirectories() {
        return 0;
    }

    /**
     * Returns the size of the file.
     * 
     * @return Stringlength of file content string
     */
    @Override
    public int getSize() {
        return fileContent.length();
    }
}
