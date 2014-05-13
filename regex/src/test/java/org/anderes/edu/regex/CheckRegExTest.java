package org.anderes.edu.regex;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CheckRegExTest {
    
    private enum Parameter {
        REGEX_1("q[^u]", "WordList.txt", 7),
        REGEX_2("<[h|H][r|R] ?((size|SIZE)? ?= ?\"[0-9]+\" ?)?>", "RegEx.html", 4),
        REGEX_3("<b>.*?</b>", "EchtFett.txt", 2);
        
        private String regEx;
        private String fileName;
        private Integer noOfFinds;
        
        private Parameter(String regEx, String fileName, Integer noOfFinds) {
            this.regEx = regEx;
            this.fileName = fileName;
            this.noOfFinds = noOfFinds;
        }
        
        public static Collection<Object[]> getParameter() {
            Collection<Object[]> collection = new ArrayList<Object[]>(); 
            for (Parameter parameter : Parameter.values()) {
                collection.add(new Object[] { parameter.regEx, parameter.fileName, parameter.noOfFinds});
            }
            return collection;
        }
    }
    
    private final String regEx;
    private final String fileName;
    private final Integer noOfFinds;
    private Pattern pattern;
    private String content;

	public CheckRegExTest(final String regEx, final String fileName, final Integer noOfFinds) {
        this.regEx = regEx;
        this.fileName = fileName;
        this.noOfFinds = noOfFinds;
    }
    
    @Before
    public void setup() {
    	System.out.println("~~~~~~~~~ " + fileName + "~~~~~~~~~");
    	System.out.println(">>>>> Pattern: " + regEx + '\n');
        pattern = Pattern.compile(regEx);
        FileHandlerRule handler = new FileHandlerRule(fileName);
        content = handler.getContent();
    }
    
    @After
    public void shutdown() {
    	System.out.println("\n~~~~~~~~~ \\ " + fileName + "~~~~~~~~~");
    }

	@Parameters
	public static Collection<Object[]> data() {
		return Parameter.getParameter();
	}

	@Test
	public void RegExCheck() {

		Matcher matcher = pattern.matcher(content);

		int counter = 0;
		while (matcher.find()) {
			counter++;
			System.out.println("Gefunden: " + matcher.group());
		}
		assertThat(counter, is(noOfFinds));
	}

}
