/*
 * Course Agile Software Development
 */ 
package invoker;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CheckIfCommandExists extends IntegrationTestBase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void cdCmd() {
		checkIfCommandExists("Cd");
	}
	
	@Test
	public void chdirCmd() {
		checkIfCommandExists("Chdir");
	}
	
	@Test
	@Ignore
	public void compCmd() {
		checkIfCommandExists("Comp");
	}
	
	@Test
	public void dateCmd() {
		checkIfCommandExists("Date");
	}
	
	@Test
	@Ignore
	public void delCmd() {
		checkIfCommandExists("Del");
	}
	
	@Test
	public void dirCmd() {
		checkIfCommandExists("Dir");
	}
	
	@Test
	@Ignore
	public void exitCmd() {
		checkIfCommandExists("Exit");
	}
	
	@Test
	@Ignore
	public void findCmd() {
		checkIfCommandExists("Find");
	}
	
	@Test
	public void formatCmd() {
		checkIfCommandExists("Format");
	}
	
	@Test
	@Ignore
	public void helpCmd() {
		checkIfCommandExists("Help");
	}
	
	@Test
	@Ignore
	public void labelCmd() {
		checkIfCommandExists("Label");
	}

	@Test
	public void mdCmd() {
		checkIfCommandExists("Md");
	}

	@Test
	public void mfCmd() {
		checkIfCommandExists("Mf");
	}
	
	@Test
	public void mkdirCmd() {
		checkIfCommandExists("Mkdir");
	}
	
	@Test
	public void mkFileCmd() {
		checkIfCommandExists("Mkfile");
	}
	
	@Test
	@Ignore
	public void moveCmd() {
		checkIfCommandExists("Move");
	}
	
	@Test
	@Ignore
	public void popdCmd() {
		checkIfCommandExists("Popd");
	}
	
	@Test
	@Ignore
	public void pushdCmd() {
		checkIfCommandExists("Pushd");
	}
	
	@Test
	@Ignore
	public void rdCmd() {
		checkIfCommandExists("Rd");
	}
	
	@Test
	@Ignore
	public void renCmd() {
		checkIfCommandExists("Ren");
	}
	
	@Test
	@Ignore
	public void renameCmd() {
		checkIfCommandExists("Rename");
	}
	
	@Test
	@Ignore
	public void rmdirCmd() {
		checkIfCommandExists("Rmdir");
	}
	
	@Test
	@Ignore
	public void timeCmd() {
		checkIfCommandExists("Time");
	}
	
	@Test
	@Ignore
	public void treeCmd() {
		checkIfCommandExists("Tree");
	}
	
	@Test
	@Ignore
	public void typeCmd() {
		checkIfCommandExists("Type");
	}
	
	@Test
	@Ignore
	public void verCmd() {
		checkIfCommandExists("Ver");
	}
	
	@Test
	@Ignore
	public void volCmd() {
		checkIfCommandExists("Vol");
	}
}
