package org.anderes.edu.beanvalidation.constrains;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Constraint(validatedBy = {})
@Pattern(regexp="[0-9]{1,4}[a-zA-Z]?")
@Size(min=1, max=5)
@ReportAsSingleViolation
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Housenumber {

    String message() default "{ch.edu.validation.housenumber}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
