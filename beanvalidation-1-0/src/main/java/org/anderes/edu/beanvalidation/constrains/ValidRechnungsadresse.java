package org.anderes.edu.beanvalidation.constrains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { RechnungsadresseValidator.class })
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRechnungsadresse {

    String message() default "{ch.edu.validation.billingaddress}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
