/*
 * Course Agile Software Development
 */
package invoker;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import filesystem.File;
import filesystem.FileSystemItem;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CmdCopyIntegrationTest extends IntegrationTestBase {

    /** Tests whether copy C:\ProgramFiles\WinWord.exe C:\Temp works */
    @Test
    public void simpleCopyWithAbsolutePaths() {
        int numberOfFilesSource = dirProgramFiles.getNumberOfFiles();
        int numberOfFilesDest = dirTemp.getNumberOfFiles();

        commandInvoker.executeCommand("copy " + fileWinWord.getPath() + " " + dirTemp.getPath(), testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("1 file(s) copied"));

        FileSystemItem fi = drive.getItemFromPath("C:\\Temp\\WinWord.exe");
        assertTrue(fi != null);
        assertTrue(fi.isDirectory() == false);
        File copiedFile = (File) fi;
        assertSame(copiedFile.getParent(), dirTemp);
        assertSame(fileWinWord.getParent(), dirProgramFiles);
        assertNotSame(fileWinWord, copiedFile);
        assertTrue(fileWinWord.getFileContent().compareTo(copiedFile.getFileContent()) == 0);
        assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFilesSource);
        assertTrue(dirTemp.getNumberOfFiles() == numberOfFilesDest + 1);
    }

    /**
     * Tests whether copy WinWord.exe ..\Temp works.
     * Current directory is C:\ProgramFiles
     */
    @Test
    public void simpleCopyWithRelativePaths() {
        int numberOfFilesSource = dirProgramFiles.getNumberOfFiles();
        int numberOfFilesDest = dirTemp.getNumberOfFiles();

        drive.setCurrentDirectory(dirProgramFiles);
        commandInvoker.executeCommand("copy " + fileWinWord.getName() + " ..\\Temp\\", testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("1 file(s) copied"));

        FileSystemItem fi = drive.getItemFromPath("C:\\Temp\\WinWord.exe");
        assertTrue(fi != null);
        assertTrue(fi.isDirectory() == false);
        File copiedFile = (File) fi;
        assertSame(copiedFile.getParent(), dirTemp);
        assertSame(fileWinWord.getParent(), dirProgramFiles);
        assertNotSame(fileWinWord, copiedFile);
        assertTrue(fileWinWord.getFileContent().compareTo(copiedFile.getFileContent()) == 0);
        assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFilesSource);
        assertTrue(dirTemp.getNumberOfFiles() == numberOfFilesDest + 1);
        assertSame(drive.getCurrentDirectory(), dirProgramFiles);
    }

    /**
     * Tests whether
     * - copy WinWord.exe
     * - copy C:\Temp\NotExistingFile.txt C:\
     * - copy WinWord.exe SkiChallenge.exe C:\Temp
     * - copy
     * - copy *.* C:\Temp
     * return errors
     */
    @Test
    public void movewithWrongParameters() {
        drive.setCurrentDirectory(dirProgramFiles);

        testOutput.reset();
        commandInvoker.executeCommand("copy WinWord.exe", testOutput);
        assertTrue(testOutput.toString().toLowerCase().contains("cannot find")
                        || testOutput.toString().toLowerCase().contains("is incorrect"));

        testOutput.reset();
        commandInvoker.executeCommand("copy C:\\Temp\\NotExistingFile.txt C:\\", testOutput);
        assertTrue(testOutput.toString().toLowerCase().contains("cannot find"));

        testOutput.reset();
        commandInvoker.executeCommand("copy WinWord.exe SkiChallenge.exe C:\\Temp", testOutput);
        assertTrue(testOutput.toString().toLowerCase().contains("syntax of the command is incorrect")
                        || testOutput.toString().toLowerCase().contains("cannot find the file specified"));

        testOutput.reset();
        commandInvoker.executeCommand("copy", testOutput);
        assertTrue(testOutput.toString().toLowerCase().contains("syntax of the command is incorrect"));

        testOutput.reset();
        commandInvoker.executeCommand("copy *.* C:\\Temp", testOutput);
        assertTrue(testOutput.toString().toLowerCase().contains("syntax of the command is incorrect")
                        || testOutput.toString().toLowerCase().contains("cannot find the file specified"));
    }

    /**
     * Copies a file to a location where a file with the same name already exists.
     * Tests whether copy /y C:\WinWord.exe C:\ProgramFiles\
     * copies the file without asking to overwrite
     */
    @Test
    public void copyWithOptionY() {
        String newFileContent = "is the new file";
        File newFile = new File("WinWord.exe", newFileContent);
        dirRoot.add(newFile);
        int filesAtSourceBeforeMove = dirRoot.getNumberOfFiles();
        int filesAtDestBeforeMove = dirProgramFiles.getNumberOfFiles();

        commandInvoker.executeCommand("copy /y C:\\WinWord.exe C:\\ProgramFiles\\", testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("1 file(s) copied"));
        FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
        assertTrue(fi != null);
        assertTrue(fi.isDirectory() == false);
        File copiedFile = (File) fi;
        assertNotSame(newFile, copiedFile);
        assertTrue(copiedFile.getFileContent().compareTo(newFileContent) == 0);
        assertSame(copiedFile.getParent(), dirProgramFiles);
        assertSame(newFile.getParent(), dirRoot);
        assertTrue(fileWinWord.getParent() == null);
        assertTrue(dirRoot.getNumberOfFiles() == filesAtSourceBeforeMove);
        assertTrue(dirProgramFiles.getNumberOfFiles() == filesAtDestBeforeMove); // A file was overwritten
    }

    /**
     * Copies a file to a location where a file with the same name already exists.
     * Tests whether copy C:\WinWord.exe C:\ProgramFiles\
     * copies the file after asking to overwrite
     */
    @Test
    public void copyWithOverwrite() {
        String newFileContent = "is the new file";
        File newFile = new File("WinWord.exe", newFileContent);
        dirRoot.add(newFile);
        int filesAtSourceBeforeCopy = dirRoot.getNumberOfFiles();
        int filesAtDestBeforeCopy = dirProgramFiles.getNumberOfFiles();
        testOutput.setCharacterThatIsRead('Y');

        commandInvoker.executeCommand("copy C:\\WinWord.exe C:\\ProgramFiles\\", testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("overwrite"));
        assertTrue(testOutput.toString().toLowerCase().contains("yes/no"));
        assertTrue(testOutput.characterWasRead());
        FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
        assertTrue(fi != null);
        assertTrue(fi.isDirectory() == false);
        File copiedFile = (File) fi;
        assertNotSame(newFile, copiedFile);
        assertTrue(copiedFile.getFileContent().compareTo(newFileContent) == 0);
        assertSame(copiedFile.getParent(), dirProgramFiles);
        assertSame(newFile.getParent(), dirRoot);
        assertTrue(fileWinWord.getParent() == null);
        assertTrue(dirRoot.getNumberOfFiles() == filesAtSourceBeforeCopy);
        assertTrue(dirProgramFiles.getNumberOfFiles() == filesAtDestBeforeCopy); // A file was overwritten
    }

    @Test
    public void directoryCopyWithAbsolutePaths() {
        int dirsAtDestBeforeCopy = dirRoot.getNumberOfDirectories();

        commandInvoker.executeCommand("copy C:\\Temp C:\\CopyOfTemp", testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("TestDir1\\test1.txt"));
        assertTrue(testOutput.toString().toLowerCase().contains("TestDir1\\test2.txt"));
        assertTrue(testOutput.toString().toLowerCase().contains("gaga.txt"));
        assertTrue(dirRoot.getNumberOfDirectories() == dirsAtDestBeforeCopy + 1);
        assertTrue(drive.getItemFromPath("C:\\CopyOfTemp\\Gaga.txt") != null);
        assertTrue(drive.getItemFromPath("C:\\CopyOfTemp\\TestDir2") != null);
        assertTrue(drive.getItemFromPath("C:\\CopyOfTemp\\TestDir1\\test1.txt") != null);
        assertTrue(drive.getItemFromPath("C:\\CopyOfTemp\\TestDir2\\test2.txt") != null);
    }
}
