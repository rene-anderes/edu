package org.anderes.edu.testing.junit.sample02;

import static org.junit.Assert.*;

import org.anderes.edu.testing.junit.sample02.Money;
import org.anderes.edu.testing.junit.sample02.MoneyIncorrect;
import org.junit.Ignore;
import org.junit.Test;


public class TestMoney_2 {

	@Test 
	public void simpleAdd() {
	    Money m12CHF= new Money(12, "CHF"); 
	    Money m14CHF= new Money(14, "CHF"); 
	    Money expected= new Money(26, "CHF"); 
	    Money result= m12CHF.add(m14CHF); 
	    assertTrue(expected.equals(result));
	}
	
	@Ignore
	@Test 
	public void simpleAddFailures() {
	    MoneyIncorrect m12CHF= new MoneyIncorrect(12, "CHF"); 
	    MoneyIncorrect m14CHF= new MoneyIncorrect(14, "CHF"); 
	    MoneyIncorrect expected= new MoneyIncorrect(26, "CHF"); 
	    MoneyIncorrect result= m12CHF.add(m14CHF); 
	    assertTrue("Unerwartetes Resultat!", expected.equals(result));
	}
}
