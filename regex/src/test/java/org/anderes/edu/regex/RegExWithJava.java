package org.anderes.edu.regex;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegExWithJava {

	private final String ahv_number = "756.1234.5678.95";
	private final String ahv_number_wrong = "756.1234.5678.955";
	private final String regEx_ahv_number = "^\\d{3}[.](\\d{4}[.]){2}\\d{2}$";
	
	private final String text = "Für agile Projekte reicht es aber aus meiner Sicht nicht aus nur ein „Pragmatic Programmer“ zu sein und in diesem Artikel möchte ich daher den Fokus auf den Entwickler in einem Scrum Team setzen. Ich sage hier explizit nicht „Programmierer“ sondern „Entwickler“, weil der Begriff Programmierer aus meiner Sicht zu sehr auf das codieren beschränkt ist und weniger die Tätigkeiten beschreibt, die zusätzlich notwendig sind, um guten Code zu erzeugen.";
	
	@Test
	public void shouldBeStringMatch() {
		
		assertThat(ahv_number.matches(regEx_ahv_number), is(true));
		assertThat(ahv_number_wrong.matches(regEx_ahv_number), is(false));
		
	}
	
	@Test
	public void shoulBeFindEveryWordStartsWithP() {
		final Pattern regExPattern = Pattern.compile("\\b[P].+?\\b");
		final Matcher matcher = regExPattern.matcher(text);
		
		int counter = 0;
		while (matcher.find()) {
			final String log = String.format("Start index: %s | End index : %s | Value: %s", matcher.start(), matcher.end(), matcher.group());
			System.out.println(log);
			counter++;
		}
		assertThat(counter, is(5));
	}
}
