package org.anderes.edu.java;

public class VariableLengthArgumentLists {

    public void setParameter(String... values) {
	if (values == null || values.length == 0 || (values.length == 1 && values[0] == null)) {
	    throw new IllegalArgumentException("Paremeter ist null");
	}
	
    }
    
}
