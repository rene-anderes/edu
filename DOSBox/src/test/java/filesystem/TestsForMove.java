/*
 * Course Agile Software Development
 */ 
package filesystem;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestsForMove extends FileSystemTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void fileMove() {
		ArrayList<FileSystemItem> content;
		
		// Check Preconditions
		assertEquals(0, file1InDir1.getPath().compareTo("C:\\subDir1\\File1InDir1"));
		assertEquals(file1InDir1.getParent(), subDir1);
		content = subDir2.getContent();
		assertFalse(content.contains(file1InDir1));
		content = subDir1.getContent();
		assertTrue(content.contains(file1InDir1));
		
		// Do move
		subDir2.add(file1InDir1);
		
		// Check Postconditions
		assertEquals(0, file1InDir1.getPath().compareTo("C:\\subDir2\\File1InDir1"));
		assertEquals(file1InDir1.getParent(), subDir2);
		content = subDir2.getContent();
		assertTrue(content.contains(file1InDir1));
		content = subDir1.getContent();
		assertTrue(content.contains(file1InDir1) == false);
	}
}
