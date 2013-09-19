package org.anderes.edu.testing.mock;

/**
 * Klasse für einen Betrag in einer bestimmten W�hrung
 * 
 * @author René Anderes
 */
public class Currency {

    private String units;
    private long amount;
    private int cents;

    /**
     * Konstruktor
     * 
     * @param amount
     *            Betrag
     * @param code
     *            Währungscode
     */
    public Currency(double amount, String code) {
	this.units = code;
	setAmount(amount);
    }

    /**
     * Betrag setzen
     * 
     * @param amount
     *            Betrag
     */
    private void setAmount(double amount) {
	this.amount = new Double(amount).longValue();
	this.cents = (int) ((amount * 100.0) % 100);
    }

    /**
     * Umrechnung in Euro
     * 
     * @param converter
     *            Umrechnungskurs
     * @return Wert in Euro
     */
    public Currency toEuros(ExchangeRate converter) {
	if ("EUR".equals(units))
	    return this;
	else {
	    double input = amount + cents / 100.0;
	    double rate;
	    try {
		rate = converter.getRate(units, "EUR");
		double output = input * rate;
		return new Currency(output, "EUR");
	    } catch (IllegalArgumentException ex) {
		return null;
	    }
	}
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
	if (o instanceof Currency) {
	    Currency other = (Currency) o;
	    return this.units.equals(other.units) && this.amount == other.amount && this.cents == other.cents;
	}
	return false;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
	return amount + "." + Math.abs(cents) + " " + units;
    }
}
