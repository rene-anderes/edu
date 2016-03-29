/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package filesystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class FileTest {

	private File testfile;

	@Before
	public void setUp() throws Exception {
		testfile = new File("test", "test content");
	}
	
	@Test
	public void constructor() {
		String name = "hello.txt";
		String content = "This is the content";
		
		testfile = new File(name, content);
		assertEquals(0, testfile.getName().compareTo(name));
		assertEquals(0, testfile.getFileContent().compareTo(content));
	}
	
	@Test
	public void forDirectory() {
		assertFalse(testfile.isDirectory());
	}

	@Test
	public void rename() {
		this.testfile.setName("NewName");
		assertEquals(0, this.testfile.getName().compareTo("NewName"));
	}
	
	@Test
	public void renameWithIllegalNames() {
		String defaultName = new String("default");
		testfile.setName(defaultName);
		assertEquals(0, testfile.getName().compareTo(defaultName));
		
		try {
			testfile.setName("Illegal\\Name");
			fail();  // must throw an exception
		}
		catch(IllegalArgumentException e) {
			assertEquals(0, testfile.getName().compareTo(defaultName));
		}
	}
	
	@Test
	public void illegalCharInFilename() {
		try {
			this.testfile.setName(":");
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Check for illegal character in filename failed!");
	}
	
	@Ignore("Not Ready to Run") 
	@Test
	public void fileSize() {
		//TODO
	}
}
