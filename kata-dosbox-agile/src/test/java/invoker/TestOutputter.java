/*
 * Course Agile Software Development
 */ 
package invoker;

import command.framework.Outputter;

public class TestOutputter implements Outputter {

	private StringBuffer output;
	private char characterThatIsRead = 0;
	private boolean characterWasRead = false;
	
	public TestOutputter() {
		this.output = new StringBuffer();
	}

	public void newline() {
		output.append("\n");
	}

	public void print(String text) {
		output.append(text);
	}

	public void printLn(String line) {
		output.append(line);
		newline();
	}
	
	public void reset() {
		this.output = new StringBuffer();
		this.characterWasRead = false;
		this.characterThatIsRead = 0;
		
	}
	
	public String getOutput() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return output.toString();
	}

	public boolean isEmpty() {
		if(this.output.length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public char readSingleCharacter() {
		this.characterWasRead = true;
		return this.characterThatIsRead;
	}
	
	public void setCharacterThatIsRead(char character) {
		this.characterThatIsRead = character;
		this.characterWasRead = false;
	}

	public boolean characterWasRead() {
		return characterWasRead;
	}
}
