package org.anderes.edu.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;

public class BestellungTest {

    @Test
    public void shouldBeValidateBestellung() {
        
        // given
        final Bestellung bestellung = createRegularBestellung();

        // when
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // then
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void shouldBeValidateWrongPlz() {
        
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.getKundendaten().setPlz(111);

        // when
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // then
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("kundendaten.plz"));
    }
    
    @Test
    public void shouldBeValidateRechnungsadresse() {
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.setRechnungAnKundenadresse(false);
        
        // when
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // then
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getMessageTemplate(), is("{ch.edu.validation.billingaddress}"));
    }
    
    private Bestellung createRegularBestellung() {
        final Bestellung bestellung = new Bestellung();
        bestellung.setLieferdatum(december(31, 2015));
        bestellung.setKundendaten(createRegularKundendaten());
        bestellung.setRechnungAnKundenadresse(true);
        return bestellung;
    }
    
    private Date december(int day, int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.DECEMBER, day);
        return calendar.getTime();
    }
    
    private Kundendaten createRegularKundendaten() {
        final Kundendaten kundendaten = new Kundendaten();
        kundendaten.setFirma("Microsoft").setAbteilung("Windows")
            .setName("Bill Gates").setStrasse("Heidenhof").setHausnummer("3B")
            .setPlz(8041).setOrt("Zürich").setEmail("bill.gates@microsoft.com");
        return kundendaten;
    }
    
    private Kundendaten createRegularRechnungsadreese() {
        final Kundendaten kundendaten = new Kundendaten();
        
        
        return kundendaten;
    }
    
    private void dumpConstraintViolation(Set<ConstraintViolation<Bestellung>> constraintViolations) {
        for (ConstraintViolation<Bestellung> violation : constraintViolations) {
            System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
        }
    }
}
