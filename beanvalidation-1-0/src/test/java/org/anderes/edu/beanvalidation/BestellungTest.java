package org.anderes.edu.beanvalidation;

import static java.util.Calendar.DECEMBER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.anderes.edu.beanvalidation.groups.Minimal;
import org.anderes.edu.beanvalidation.groups.OrderedChecks;
import org.junit.Before;
import org.junit.Test;

public class BestellungTest {
    
    private Validator validator;
    
    @Before
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldBeValidateBestellung() {
        
        // given
        final Bestellung bestellung = createRegularBestellung();

        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);

        // then
        assertThat(constraintViolations.size(), is(0));
    }

    @Test
    public void shouldBeValidateWrongPlz() {
        
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.getKundendaten().setPlz(111);

        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);

        // then
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("kundendaten.plz"));
    }
    
    @Test
    public void shouldBeWrongHousnumber() {
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.getKundendaten().setHausnummer("AA");

        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);

        // then
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("kundendaten.hausnummer"));
    }
    
    @Test
    public void shouldBeMissingRechnungsadresse() {
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.setRechnungAnKundenadresse(false);
        
        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);

        // then
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getMessageTemplate(), is("{ch.edu.validation.billingaddress}"));
    }
    
    @Test
    public void shouldBeValidRechnungsadresse() {
        // given
        final Bestellung bestellung = createRegularBestellung();
        bestellung.setRechnungsadresse(createRegularRechnungsadreese());
        bestellung.setRechnungAnKundenadresse(false);
        
        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung);

        // then
        assertThat(constraintViolations.size(), is(0));
    }
    
    @Test
    public void shouldBeValidateGroup() {
        // given
        final Bestellung bestellung = new Bestellung();
        bestellung.setLieferdatum(december(31, 2000));
        
        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung, Minimal.class);
        
        // then
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(1));
        assertThat(constraintViolations.iterator().next().getPropertyPath().toString(), is("lieferdatum"));
    }
    
    @Test
    public void shouldBeOrderedChecks() {
        // given
        final Bestellung bestellung = createRegularBestellung();
        
        // when
        final Set<ConstraintViolation<Bestellung>> constraintViolations = validator.validate(bestellung, OrderedChecks.class);
        
        // then
        dumpConstraintViolation(constraintViolations);
        assertThat(constraintViolations.size(), is(0));
        
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
        calendar.set(year, DECEMBER, day);
        return calendar.getTime();
    }
    
    private Kundendaten createRegularKundendaten() {
        final Kundendaten kundendaten = new Kundendaten();
        kundendaten.setFirma("Microsoft").setAbteilung("Windows")
            .setName("Bill Gates").setStrasse("Richtistrasse").setHausnummer("3B")
            .setPlz(8304).setOrt("Wallisellen").setEmail("bill.gates@microsoft.com");
        return kundendaten;
    }
    
    private Kundendaten createRegularRechnungsadreese() {
        final Kundendaten kundendaten = new Kundendaten();
        kundendaten.setFirma("Oracle").setAbteilung("Database")
            .setName("Larry Ellison").setStrasse("Täfernstrasse").setHausnummer("4")
            .setPlz(5405).setOrt("Dättwil").setEmail("info@oracle.com");
        return kundendaten;
    }
    
    private void dumpConstraintViolation(Set<ConstraintViolation<Bestellung>> constraintViolations) {
        for (ConstraintViolation<Bestellung> violation : constraintViolations) {
            System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
        }
    }
}
