package org.anderes.edu.testing.junit.sample02;

import static org.junit.Assert.*;

import org.anderes.edu.testing.junit.sample02.Money;
import org.junit.Test;


public class TestMoney_1 {

	@Test 
	public void simpleAdd() {
	    Money m12CHF= new Money(12, "CHF"); 
	    Money m14CHF= new Money(14, "CHF"); 
	    Money expected= new Money(26, "CHF"); 
	    Money result= m12CHF.add(m14CHF); 
	    assertTrue(expected.equals(result));
	}
}
