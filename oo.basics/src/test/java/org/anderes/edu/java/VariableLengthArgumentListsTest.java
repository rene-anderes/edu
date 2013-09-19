package org.anderes.edu.java;

import org.junit.Test;

public class VariableLengthArgumentListsTest {

    
    @Test
    public void shouldBeOk() {
	VariableLengthArgumentLists o = new VariableLengthArgumentLists();
	o.setParameter("value1", "value2");
	o.setParameter("value1");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void shouldBeException() {
	VariableLengthArgumentLists o = new VariableLengthArgumentLists();
	o.setParameter();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void shouldBeException1() {
	VariableLengthArgumentLists o = new VariableLengthArgumentLists();
	o.setParameter(null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void shouldBeException2() {
	VariableLengthArgumentLists o = new VariableLengthArgumentLists();
	o.setParameter((String)null);
    }
}
