package org.anderes.edu.testing.mock;

/**
 * Klasse für einen Betrag in einer bestimmten Währung
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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (amount ^ (amount >>> 32));
        result = prime * result + cents;
        result = prime * result + ((units == null) ? 0 : units.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Currency other = (Currency) obj;
        if (amount != other.amount)
            return false;
        if (cents != other.cents)
            return false;
        if (units == null) {
            if (other.units != null)
                return false;
        } else if (!units.equals(other.units))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return amount + "." + Math.abs(cents) + " " + units;
    }
}
