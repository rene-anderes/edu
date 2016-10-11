package org.anderes.edu.jpa.inheritance.mappedsuperclass;

import static org.anderes.edu.jpa.inheritance.mappedsuperclass.Cash.Currency.CHF;
import static org.anderes.edu.jpa.inheritance.mappedsuperclass.Cash.Currency.EUR;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PaymentTest {

 private EntityManager entityManager;
    
    @Before
    public void setUp() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
    }

    @Test
    public void shouldBeCashPaymentCHF() {
        final Cash cashPayment = entityManager.find(Cash.class, 2000L);
        assertThat(cashPayment.getAmount(), is(expectedAmountOf(250.35)));
        assertThat(cashPayment.getCurrency(), is(CHF));
        assertThat(cashPayment.getExchangeRate(), is(expectedExchangeRateOf(1D)));
    }
    
    @Test
    public void shouldBeCashPaymentEUR() {
        final Cash cashPayment = entityManager.find(Cash.class, 2001L);
        assertThat(cashPayment.getAmount(), is(expectedAmountOf(960.50)));
        assertThat(cashPayment.getCurrency(), is(EUR));
        assertThat(cashPayment.getExchangeRate(), is(expectedExchangeRateOf(1.101)));
    }
    
    @Test
    public void shouldBeCreditCardPayment() {
        final CreditCard creditCardPayment = entityManager.find(CreditCard.class, 2011L);
        assertThat(creditCardPayment.getAmount(), is(BigDecimal.valueOf(34.55)));
    }
    
    private BigDecimal expectedExchangeRateOf(final double rate) {
        return BigDecimal.valueOf(rate).setScale(4);
    }
    
    private BigDecimal expectedAmountOf(final double amount) {
        return BigDecimal.valueOf(amount).setScale(2);
    }
}
