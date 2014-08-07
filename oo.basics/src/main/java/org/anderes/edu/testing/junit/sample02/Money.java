package org.anderes.edu.testing.junit.sample02;


public class Money {

	private String currency = "";
	private int value = 0;
	
	/**
	 * Konstruktor
	 * 
	 * @param value Wert
	 * @param currency Währung
	 */
	public Money(int value, String currency) {
		super();
		this.currency = currency;
		this.value = value;
	}

	/**
	 * Addiert das Geld hinzu.
	 * 
	 * @param money Zusätzliches Geld 
	 * @return Aktuelles Vermögen
	 */
	public Money add(Money money) {
		if (currency.equalsIgnoreCase(money.getCurrency())) {
			value += money.getValue();
		}
		return this;
	}
	
	public String getCurrency() {
		return currency;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + value;
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
		Money other = (Money) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
