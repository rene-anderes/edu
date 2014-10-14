package org.anderes.edu.beanvalidation.constrains;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.anderes.edu.beanvalidation.Bestellung;

public class BestellungValidator implements ConstraintValidator<ValidBestellung, Bestellung> {

    @Override
    public void initialize(ValidBestellung constraintAnnotation) {
        // nothing to do 
    }

    @Override
    public boolean isValid(Bestellung bestellung, ConstraintValidatorContext context) {
        if (!bestellung.isRechnungAnKundenadresse() && bestellung.getRechnungsadresse() == null) {
            return false;
        }
        return true;
    }

}
