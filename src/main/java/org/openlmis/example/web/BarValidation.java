package org.openlmis.example.web;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/*
    Create a custom @BarValidation annotation which may be applied to Types.
 */
@Target( {TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BarValidator.class)
public @interface BarValidation {
  String message() default "{invalid.address.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}