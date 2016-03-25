/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package invoker;

import filesystem.Directory;
import filesystem.Drive;
import filesystem.File;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;

import persistence.DaoFactory;
import persistence.HistoryDao;
import persistence.DaoFactory.DaoType;
import static org.junit.Assert.*;

import command.framework.Command;
import command.library.CommandFactory;

/**Setup a directory structure as follows:
 * C:\
 * +---ProgramFiles       Files WinWord.exe, Excel.exe, SkiChallenge.exe
 * +---Temp               Files gaga.txt, log.log, myStuff.doc
 *     +---TestDir1       test1.txt, test2.txt, test3.txt
 *     +---TestDir2       empty
 * +---Windows            Files command.com, clock.avi, explorer.exe, TASKMAN.exe
 *     +---system32       Files $winnt$.inf, mfc40.dll mfc40u.dll, mfc42.dll
 *     +---web            Files bullet.gif
 *     +---Microsoft.NET  Files sbs_diasymreader.dll, sbs_iehost.dll
 *
 */
public abstract class IntegrationTestBase  {

	private static final int SLEEP_TIME_AFTER_TEST_CASE = 300;

	protected HistoryDao histroyDAO;
	protected CommandInvoker commandInvoker;
	protected Drive drive;
	protected TestOutputter testOutput;
	protected Directory dirRoot;
	protected Directory dirTemp;
	protected Directory dirWindows;
	protected Directory dirProgramFiles;
	protected Directory dirWindowsSystem32;
	protected Directory dirWindowsWeb;
	protected Directory dirWindowsNet;
	protected Directory dirTestDir1;
	protected Directory dirTestDir2;
	protected Directory dirTestDir3;

	protected File fileWinWord;
	protected File fileExcel;
	protected File fileSkiChallenge;
	protected File fileGaga;
	protected File fileLog;
	protected File fileMyStuff;
	protected File fileTest1;
	protected File fileTest2;
	protected File fileTest3;
	protected File fileCommand;
	protected File fileClock;
	protected File fileExplorer;
	protected File fileTaskman;
	protected File fileWinnt;
	protected File fileMFC40;
	protected File fileMFC40u;
	protected File fileMFC42;
	protected File fileBullet;
	protected File fileSbsDiasymreader;
	protected File fileSbsIehost;

	private CommandFactory factory;
	
	@Before
	public void setUp() throws Exception {
		// Setup Drive
		drive = new Drive("C");
		dirRoot = drive.getRootDirectory();
		
		// Create Directories
		dirTemp = new Directory("Temp");
		dirWindows = new Directory("Windows");
		dirProgramFiles = new Directory("ProgramFiles");
		dirWindowsSystem32 = new Directory("system32");
		dirWindowsWeb = new Directory("web");
		dirWindowsNet = new Directory("Microsoft.NET");
		dirTestDir1 = new Directory("TestDir1");
		dirTestDir2 = new Directory("TestDir2");
		dirTestDir3 = new Directory("TestDir3");
		
		// Setup Directory Structure
		dirRoot.add(dirTemp);
		dirRoot.add(dirWindows);
		dirRoot.add(dirProgramFiles);
		dirTemp.add(dirTestDir1);
		dirTemp.add(dirTestDir2);
		dirTestDir2.add(dirTestDir3);
		dirWindows.add(dirWindowsSystem32);
		dirWindows.add(dirWindowsWeb);
		dirWindows.add(dirWindowsNet);
		
		// Create Files
		fileWinWord = new File("WinWord.exe", "File WinWord.exe");
		fileExcel = new File("Excel.exe", "File Excel.exe");
		fileSkiChallenge = new File("SkiChallenge.exe", "File SkiChallenge.exe");
		fileGaga = new File("gaga.txt", "gaga.txt");
		fileLog = new File("log.log", "log.log");
		fileMyStuff = new File("myStuff.doc", "myStuff.doc");
		fileTest1 = new File("test1.txt", "test1.txt");
		fileTest2 = new File("test2.txt", "test2.txt");
		fileTest3 = new File("test3.txt", "test3.txt");
		fileCommand = new File("command.com", "command.com");
		fileClock = new File("clock.avi", "clock.avi");
		fileExplorer = new File("explorer.exe", "explorer.exe");
		fileTaskman = new File("TASKMAN.exe", "TASKMAN.exe");
		fileWinnt = new File("$winnt$.inf", "$winnt$.inf");
		fileMFC40 = new File("mfc40.dll", "mfc40.dll");
		fileMFC40u = new File("mfc40u.dll", "mfc40u.dll");
		fileMFC42 = new File("mfc42.dll", "mfc42.dll");
		fileBullet = new File("bullet.gif", "bullet.gif");
		fileSbsDiasymreader = new File("sbs_diasymreader.dll", "sbs_diasymreader.dll");
		fileSbsIehost = new File("sbs_iehost.dll", "sbs_iehost.dll");
		
		// Add files to directory structure
		dirProgramFiles.add(fileWinWord);
		dirProgramFiles.add(fileExcel);
		dirProgramFiles.add(fileSkiChallenge);
		dirTemp.add(fileGaga);
		dirTemp.add(fileLog);
		dirTemp.add(fileMyStuff);
		dirTestDir1.add(fileTest1);
		dirTestDir1.add(fileTest2);
		dirTestDir1.add(fileTest3);
		dirWindows.add(fileCommand);
		dirWindows.add(fileClock);
		dirWindows.add(fileExplorer);
		dirWindows.add(fileTaskman);
		dirWindowsSystem32.add(fileWinnt);
		dirWindowsSystem32.add(fileMFC40);
		dirWindowsSystem32.add(fileMFC40u);
		dirWindowsSystem32.add(fileMFC42);
		dirWindowsNet.add(fileBullet);
		dirWindowsNet.add(fileSbsDiasymreader);
		dirWindowsNet.add(fileSbsIehost);
		
		// Setup Command Environment
		factory = new CommandFactory(drive);
		histroyDAO = DaoFactory.createHistoryDao(DaoType.FAKE);
		commandInvoker = new CommandInvoker(histroyDAO);
		commandInvoker.setCommands(factory.getCommandList());
		
		testOutput = new TestOutputter();
}

	@After
	public void tearDown() throws Exception {
		sleepAfterTest();
	}
	
	private void sleepAfterTest() {
		try {
			Thread.sleep(SLEEP_TIME_AFTER_TEST_CASE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void checkIfCommandExists(String cmd) {
		ArrayList<Command> cmdList = factory.getCommandList();
		
		Iterator<Command> it = cmdList.iterator();
		while(it.hasNext()) {
			if(it.next().compareCmdName(cmd.toLowerCase()))
				return;
		}
		
		fail("Command \"" + cmd + "\" does not exist");
	}
}
