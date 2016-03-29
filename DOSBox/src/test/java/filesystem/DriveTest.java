/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package filesystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DriveTest extends FileSystemTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void constructor() {
		String driveName = "d";
		
		Drive testdrive = new Drive(driveName);
		assertEquals(testdrive.getRootDirectory(), testdrive.getCurrentDirectory());
		assertEquals(0, testdrive.getCurrentDirectory().getName().compareTo("D:"));
		assertEquals(0, testdrive.getRootDirectory().getName().compareTo("D:"));
		assertEquals(0, testdrive.getDriveName().compareTo("D:"));
		
		testdrive = new Drive("Hello");
		assertEquals(0, testdrive.getDriveName().compareTo("H:"));
	}
	
	@Test
	public void currentDirectory() {
		assertEquals(0, drive.getCurrentDirectory().getName().compareTo("C:"));
		
		Directory subDir = new Directory("subDir");
		drive.getRootDirectory().add(subDir);
		drive.setCurrentDirectory(subDir);
		assertEquals(drive.getCurrentDirectory(), subDir);
	}
	
	@Test
	public void getItemFromPathWithAbsolutePaths() {
		String testpath;
		
		testpath = rootDir.getPath();
		assertEquals(drive.getItemFromPath(testpath), rootDir);
		testpath = subDir1.getPath();
		assertEquals(drive.getItemFromPath(testpath), subDir1);
		testpath = subDir2.getPath();
		testpath = testpath.replace('\\', '/');
		assertEquals(drive.getItemFromPath(testpath), subDir2);
		
		testpath = file2InDir1.getPath();
		assertEquals(file2InDir1, drive.getItemFromPath(testpath));
		testpath = fileInRoot1.getPath();
		assertEquals(drive.getItemFromPath(testpath), fileInRoot1);
		
		testpath = "g:\\gaga\\gugus";
		assertNull(drive.getItemFromPath(testpath));
		
		testpath = "\\" + subDir1.getName();
		assertEquals(drive.getItemFromPath(testpath), subDir1);

		assertEquals(drive.getItemFromPath("C:\\subDir1"), subDir1);
		assertEquals(drive.getItemFromPath("c:\\subDir1"), subDir1);
		assertEquals(drive.getItemFromPath("c:/subDir1"), subDir1);
}

	@Test
	public void getItemFromPathWithRelativePaths() {
		String subSubDirName = new String("SubSubDir1");
		Directory subSubDir1 = new Directory(subSubDirName);
		subDir1.add(subSubDir1);
		
		drive.setCurrentDirectory(subDir1);
		assertEquals(drive.getItemFromPath(subSubDirName), subSubDir1);
	}
	
	@Test
	public void getItemFromPathWithSpecialPaths() {

		// Path "\"
		assertEquals(drive.getRootDirectory(), drive.getItemFromPath("\\"));
		
		// Path ".."
		drive.setCurrentDirectory(subDir2);
		assertEquals(subDir2.getParent(), drive.getItemFromPath(".."));
		drive.setCurrentDirectory(rootDir);
		assertEquals(rootDir, drive.getItemFromPath(".."));
		
		// Path "."
		drive.setCurrentDirectory(subDir1);
		assertEquals(subDir1, drive.getItemFromPath("."));
		
		// Path ".\"
		drive.setCurrentDirectory(subDir1);
		assertEquals(subDir1, drive.getItemFromPath(".\\"));
		
		// Path ".\subDir2"
		drive.setCurrentDirectory(rootDir);
		assertEquals(subDir2, drive.getItemFromPath(".\\subDir2"));
		
		// Path "..\subDir1"
		drive.setCurrentDirectory(subDir2);
		assertEquals(subDir1, drive.getItemFromPath("..\\subDir1"));
		
		// Path ".\..\subDir1"
		drive.setCurrentDirectory(subDir2);
		assertEquals(subDir1, drive.getItemFromPath(".\\..\\subDir1"));
		
		// Path "..\..
		drive.setCurrentDirectory(subDir21);
		assertEquals(rootDir, drive.getItemFromPath("..\\.."));

		// Path "..\..\subDir1
		drive.setCurrentDirectory(subDir21);
		assertEquals(subDir1, drive.getItemFromPath("..\\..\\subDir1"));

		// Path "..\..\subDir1\file1InDir1
		drive.setCurrentDirectory(subDir21);
		assertEquals(file1InDir1, drive.getItemFromPath("..\\..\\subDir1\\file1InDir1"));
		
		// Path "..\..\subDir1\..\subDir2
		drive.setCurrentDirectory(subDir21);
		assertEquals(subDir2, drive.getItemFromPath("..\\..\\subDir1\\..\\subDir2"));

		// Path "..\..\subDir1\..\..\..\subDir2
		drive.setCurrentDirectory(subDir21);
		assertEquals(subDir2, drive.getItemFromPath("..\\..\\subDir1\\..\\..\\..\\subDir2"));
	}
	
	@Test
	public void singleCharacterDirectories() {
		assertEquals(2, rootDir.getNumberOfDirectories());

		Directory newDir = new Directory("N");
		rootDir.add(newDir);
		assertEquals(3, rootDir.getNumberOfDirectories());

		FileSystemItem item = drive.getItemFromPath(rootDir.getPath() + "\\N");
		assertNotNull(item);
		assertTrue(item.isDirectory());
		assertEquals(item, newDir);
	}	
}
