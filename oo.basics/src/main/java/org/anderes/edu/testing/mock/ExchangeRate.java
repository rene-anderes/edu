package org.anderes.edu.testing.mock;

/**
 * Wechslekurs
 * @author René Anderes
 */
public interface ExchangeRate {

	/**
	 * Gibt den Umrechnnugskurs zurück.
	 *
	 * @param inputCurrency Basiswährung
	 * @param outputCurrency Zielwährung
	 * @return Umrechnngskurs
	 * @throws IllegalArgumentException Wenn die Parameter keine Kursumrechnung zulassen
	 */
	public double getRate(String inputCurrency, String outputCurrency) throws IllegalArgumentException;
}
