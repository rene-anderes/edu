/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package filesystem;


public abstract class FileSystemTestCase {

	protected Directory rootDir;
	protected Directory subDir1;
	protected Directory subDir2;
	protected Directory subDir21;
	protected File fileInRoot1;
	protected File fileInRoot2;
	protected File file1InDir1;
	protected File file2InDir1;
	protected Drive drive;

	public void setUp() throws Exception {
		this.drive = new Drive("C");
		this.rootDir = this.drive.getRootDirectory();
		this.fileInRoot1 = new File("FileInRoot1", "");
		this.rootDir.add(this.fileInRoot1);
		this.fileInRoot2 = new File("FileInRoot2", "");
		this.rootDir.add(this.fileInRoot2);
		
		this.subDir1 = new Directory("subDir1");
		this.rootDir.add(subDir1);
		this.file1InDir1 = new File("File1InDir1", "");
		this.subDir1.add(this.file1InDir1);
		this.file2InDir1 = new File("File2InDir1", "");
		this.subDir1.add(this.file2InDir1);
		
		this.subDir2 = new Directory("subDir2");
		this.rootDir.add(subDir2);
		
		this.subDir21 = new Directory("subDir21");
		this.subDir2.add(subDir21);
	}
}
