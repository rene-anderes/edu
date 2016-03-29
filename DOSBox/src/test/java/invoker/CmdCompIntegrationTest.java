/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import filesystem.File;

@Ignore
public class CmdCompIntegrationTest extends IntegrationTestBase {
	
	private File file1;
	private File file2;
	private File file3;
	private File file4;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		String content1 = "This is Content A";
		String content2 = "This is Content B";
		String content3 = "this is content b";  // same as content2, but lower case 
		file1 = new File("file1_Content1", content1);
		file2 = new File("file2_Content1", content1);
		file3 = new File("file3_Content2", content2);
		file4 = new File("file4_Content3", content3);
				
		drive.getRootDirectory().add(file1);
		drive.getRootDirectory().add(file2);
		drive.getRootDirectory().add(file3);
		drive.getRootDirectory().add(file4);		
	}

	@Test
	public void compareSingleFiles() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("comp " + file1.getName() + " " + file2.getName(), testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("files compare ok"));
	}
	
	@Test
	public void compareSingleFilesNoMatch() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("comp " + file1.getName() + " " + file3.getName(), testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("compare error"));
		assertTrue(testOutput.toString().toLowerCase().contains("files compare ok") == false);
	}
	
	@Test
	public void compareDirectories() {
		//TODO
		compareSingleFiles();
	}
	
	@Test
	public void parameterSlashC() {
		drive.setCurrentDirectory(drive.getRootDirectory());
		commandInvoker.executeCommand("comp /c " + file3.getName() + " " + file4.getName(), testOutput);
		
		assertTrue(testOutput.toString().toLowerCase().contains("files compare ok"));
	}
}
