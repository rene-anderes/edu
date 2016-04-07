/*
 * Course Agile Software Development
 */ 
package filesystem;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class DirectoryTest {

	private Directory rootDir;
	private Directory subDir1;
	private Directory subDir2;

	@Before
	public void setUp() throws Exception {
		rootDir = new Directory("root");
		subDir1 = new Directory("subDir1");
		rootDir.add(subDir1);
		subDir2 = new Directory("subDir2");
		rootDir.add(subDir2);
	}
	
	@Test
	public void constructor() {
		String name = "root";
		Directory testdir = new Directory(name);

		assertEquals(0, testdir.getName().compareTo(name));
		assertTrue(testdir.getContent().isEmpty());
		assertNull(testdir.getParent());
	}
	
	@Test
	public void subDirectory() {
		ArrayList<FileSystemItem> content = rootDir.getContent();
		assertTrue(content != null);
		assertTrue(content.size() == 2);
		
		FileSystemItem item = content.get(0);
		assertTrue(item.isDirectory());
		assertEquals(0, item.getName().compareTo(subDir1.getName()));
		
		FileSystemItem parent = item.getParent();
		
		assertTrue(parent.isDirectory());
		assertEquals(parent, rootDir);
		assertNull(parent.getParent());
		
		String path = item.getPath();
		assertEquals(rootDir.getName() + "\\" + subDir1.getName(), path);
	}
	
	@Test
	public void containingFiles() {
		
	}
	
	@Test
	public void forDirectory() {
		assertTrue(rootDir.isDirectory());
		assertTrue(subDir2.isDirectory());
	}
	
	@Test
	public void rename() {
		subDir1.setName("NewName");
		
		assertEquals("NewName", subDir1.getName());
	}
	
	@Test
	public void numberOfFilesAndDirectories() {
		//TODO
	}
}
