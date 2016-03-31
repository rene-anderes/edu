/*
 * Course Agile Software Development
 */ 
package filesystem;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestsForCmdDir extends FileSystemTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void content() {
		ArrayList<FileSystemItem> content;
		
		content = rootDir.getContent();
		
		assertFalse(content.isEmpty());
		assertEquals(4, content.size());  // subDir1, subDir2, file1InRoot, file2InRoot
		assertTrue(content.contains(subDir1));
		assertTrue(content.contains(subDir2));
		assertTrue(content.contains(fileInRoot1));
		assertTrue(content.contains(fileInRoot2));
		assertFalse(content.contains(file1InDir1));

		content = subDir1.getContent();
		assertFalse(content.isEmpty());
		assertEquals(2, content.size());  // file1InDir1, file2InDir1
		assertTrue(content.contains(file1InDir1));
		assertTrue(content.contains(file2InDir1));
		assertFalse(content.contains(fileInRoot2));
		
		content = subDir2.getContent();
		assertTrue(content.contains(subDir21));
	}
}
