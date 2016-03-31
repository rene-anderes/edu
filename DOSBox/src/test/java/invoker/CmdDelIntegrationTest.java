/*
 * Course Agile Software Development
 */
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import filesystem.File;
import filesystem.FileSystemItem;

@Ignore
public class CmdDelIntegrationTest extends IntegrationTestBase {

    /** Tests whether del C:\ProgramFiles\WinWord.exe deletes this file */
    @Test
    public void deleteSingleFileAbsolutePath() {
        int numberOfFiles = dirProgramFiles.getNumberOfFiles();

        commandInvoker.executeCommand("del " + fileWinWord.getPath(), testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("not find") == false);

        FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
        assertTrue(fi == null);
        assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFiles - 1);
        assertTrue(fileWinWord.getParent() == null);
    }

    /** Test whether del WinWord.exe deletes the file if it is in the current directory */
    @Test
    public void deleteSingleFileRelativePath() {
        int numberOfFiles = dirProgramFiles.getNumberOfFiles();

        drive.setCurrentDirectory(dirProgramFiles);
        commandInvoker.executeCommand("del " + fileWinWord.getName(), testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("not find") == false);

        FileSystemItem fi = drive.getItemFromPath("C:\\ProgramFiles\\WinWord.exe");
        assertTrue(fi == null);
        assertTrue(dirProgramFiles.getNumberOfFiles() == numberOfFiles - 1);
        assertTrue(fileWinWord.getParent() == null);
    }

    /** Tests whether del c:\temp\testdir1 deletes the entire directory, but asks before deleting all files. */
    @Test
    public void deleteDirectoryAbsolutePath() {
        int numberOfDirs = dirTemp.getNumberOfDirectories();

        testOutput.setCharacterThatIsRead('y');
        commandInvoker.executeCommand("del " + dirTestDir1, testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("are you sure"));
        assertTrue(testOutput.characterWasRead());
        assertTrue(dirTemp.getNumberOfDirectories() == numberOfDirs - 1);
        assertTrue(dirTestDir1.getPath() == null);
    }

    /**
     * Tests whether del testfile.txt /s deletes all files in the current directory and its subdirectories
     * Tests in addition, if a 4th file which is in a different directory is not deleted.
     */
    @Test
    public void deleteFilesFromSubdirectory() {
        String testFileName = "gaga.txt";
        File test1 = new File(testFileName, "");
        File test2 = new File(testFileName, "");
        File test3 = new File(testFileName, "");
        File test4 = new File(testFileName, "");

        dirTemp.add(test1);
        dirTestDir1.add(test2);
        dirTestDir1.add(test3);
        dirRoot.add(test4);

        String test1Path = test1.getPath();
        String test2Path = test2.getPath();
        String test3Path = test3.getPath();
        String test4Path = test4.getPath();

        drive.setCurrentDirectory(dirTemp);
        commandInvoker.executeCommand("del /s " + testFileName, testOutput);

        assertTrue(testOutput != null);
        assertTrue(testOutput.toString().toLowerCase().contains("deleted file - " + test1Path.toLowerCase()));
        assertTrue(testOutput.toString().toLowerCase().contains("deleted file - " + test2Path.toLowerCase()));
        assertTrue(testOutput.toString().toLowerCase().contains("deleted file - " + test3Path.toLowerCase()));
        assertTrue(testOutput.toString().toLowerCase().contains(test4Path.toLowerCase()) == false);
        assertTrue(test1.getParent() == null);
        assertTrue(test2.getParent() == null);
        assertTrue(test3.getParent() == null);
        assertTrue(test4.getParent() == dirRoot);
    }
}
