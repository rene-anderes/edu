package org.anderes.edu.testing.junit.sample02;


import static org.junit.Assert.*;

import org.anderes.edu.testing.junit.sample02.Money;
import org.junit.Before;
import org.junit.Test;

public class TestMoney_3 {

	private Money m12CHF; 
    private Money m14CHF; 
    private Money m28USD; 

	@Before
	public void setUp() throws Exception {
		m12CHF= new Money(12, "CHF"); 
        m14CHF= new Money(14, "CHF"); 
        m28USD= new Money(28, "USD"); 
	}
	
	@Test 
	public void simpleAdd() {
	    Money result= m12CHF.add(m14CHF); 
	    Money expected= new Money(26, "CHF"); 
	    assertTrue(expected.equals(result));
	}
	
	@Test 
	public void simpleAddFail() {
	    Money result= m28USD.add(m14CHF); 
	    Money expected= new Money(40, "CHF"); 
	    assertFalse(expected.equals(result));
	}

}
