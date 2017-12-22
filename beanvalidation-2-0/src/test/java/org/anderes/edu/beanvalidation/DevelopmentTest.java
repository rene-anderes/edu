package org.anderes.edu.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class DevelopmentTest {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory factory = Validation
                .byDefaultProvider()
                .configure()
                .clockProvider(this::configureClockWithFutureTime)
                .buildValidatorFactory();
        this.validator = factory.getValidator();
    }

    private Clock configureClockWithFutureTime() {
        return Clock.offset(Clock.systemDefaultZone(), Duration.ofDays(-2));
    }
    
    @Test
    public void shouldBeCorrect() {
        Development development = new Development();
        development.setNextRelease(LocalDate.now());
        Set<ConstraintViolation<Development>> violations = validator.validate(development);
        
        assertThat(violations.size(), is(0));
    }
}
