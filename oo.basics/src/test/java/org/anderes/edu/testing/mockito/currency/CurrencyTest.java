package org.anderes.edu.testing.mockito.currency;

import org.anderes.edu.testing.mock.Currency;
import org.anderes.edu.testing.mock.ExchangeRate;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Testklasse für die Klasse {@link Currency}
 * 
 * @author René Anderes
 */
public class CurrencyTest {

    /**
     * Test für die Euro-Umrechnung
     */
    @Test
    public void toEuro() {
	
	/* Mock-Objekt von der Klasse bzw. Schnittstelle,
	 * die simuliert werden soll, erzeugen */
	ExchangeRate mock = mock(ExchangeRate.class);
	when(mock.getRate("CHF", "EUR")).thenReturn(1.2);

	Currency currency = new Currency(22.45, "CHF");
	/* Mock-Objekt benutzen */
	Currency actual = currency.toEuros(mock);

	/* Normaler Junit-Test */ 
	Currency expected = new Currency(26.94, "EUR");
	assertEquals(expected, actual);
	
	/* Verifizieren ob das Mock-Objekt 
	 * so benutzt wurde wie vorgesehen */
	verify(mock).getRate("CHF", "EUR");
	
    }
}
