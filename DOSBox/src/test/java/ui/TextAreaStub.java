/*
 * Course Agile Software Development
 * 
 * (c) 2010 by Zuehlke Engineering AG
 */ 
package ui;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextAreaStub extends JTextArea {

	private StringBuffer content;

	public TextAreaStub() {
		content = new StringBuffer();
	}

	@Override
	public void append(String text) {
		content.append(text);
	}

	public String getContent() {
		return content.toString();
	}
}
