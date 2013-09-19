package org.anderes.edu.testing.easymock.currency;

import org.anderes.edu.testing.mock.Currency;
import org.anderes.edu.testing.mock.ExchangeRate;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

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

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock aufzeichnen
		ExchangeRate mock = createMock(ExchangeRate.class);
		expect(mock.getRate("CHF", "EUR")).andReturn(1.2).times(1);
		replay(mock);
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock aufzeichnen beendet

		Currency currency = new Currency(22.45, "CHF");
		Currency actual = currency.toEuros(mock);

		Currency expected = new Currency(26.94, "EUR");
		assertEquals(expected, actual);

		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Mock verifizieren
		verify(mock);
	}
}
