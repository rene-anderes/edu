package command.framework;

import java.util.Arrays;
import java.util.List;

/**
 * Sorgt dafür, dass nur eine bestimmte Anzahl Zeichen pro Zeile ausgegeben wird.
 * 
 * @author ra, hum
 */
public class Wrapper implements Outputter {

	private int maxChar = 60;
	private Outputter outputter;
	private int charCounter;
	
	/**
	 * Konstruktor
	 * 
	 * @param maxChar Max. Anzahl Zeichen pro Zeile
	 */
    public Wrapper(Outputter outputter, int maxChar) {
		this.outputter = outputter;
		this.maxChar = maxChar;
		this.charCounter = 0;
	}

	@Override
	public void newline() {
		outputter.newline();
	}

	@Override
	public void print(String text) {
		text = text.replaceAll("[\t]", "    ");
		int actCounter = charCounter + text.length();
		if (actCounter > maxChar) {
			String newText = "";
			List<String> strList = getStringToken(text);
			for (String s : strList) {
				int l = s.length();
				if (l > maxChar) {
					s = s.substring(0, maxChar - 3);
					s += "...\n";
				} 
				if ((charCounter > 0) && ((actCounter + l) > maxChar)) {
					newText += "\n";
					charCounter = 0;
				}
				newText += s;
			}
			text = newText;
		} 
		charCounter += text.length();
		outputter.print(text);
	}

	private List<String> getStringToken(String text) {
		List<String> strList = Arrays.asList(text.split("[ \n]"));
		return strList;
	}
	
	@Override
	public void printLn(String line) {
		print(line);
		newline();

	}

	@Override
	public char readSingleCharacter() {
		return outputter.readSingleCharacter();
	}

}
