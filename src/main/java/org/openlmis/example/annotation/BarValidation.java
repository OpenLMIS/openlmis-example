package org.openlmis.example.annotation;

import org.openlmis.example.validator.BarValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
    Create a custom @BarValidation that may be applied to Types.
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BarValidator.class)
public @interface BarValidation
{
    String message() default "{invalid.address.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}