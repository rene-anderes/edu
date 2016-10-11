package org.anderes.edu.jpa.inheritance.mappedsuperclass;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "CASH_PAYMENT")
public class Cash extends Payment {

    public enum Currency { EUR, CHF };
    
    @Enumerated(EnumType.STRING)
    private Currency currency;
    
    @Column(precision=8, scale=4)
    private BigDecimal exchangeRate;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal echangeRate) {
        this.exchangeRate = echangeRate;
    }
    
}
