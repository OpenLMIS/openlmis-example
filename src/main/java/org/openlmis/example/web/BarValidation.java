package org.openlmis.example.web;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * A custom @BarValidation annotation which may be applied to Types.
 */
@Target( {TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BarValidator.class)
public @interface BarValidation {

  /**
   * The error message for this validation.
   * @return the error message (as key)
   */
  String message() default "{invalid.address.message}";

  /**
   * The groups of classes to which this validation applies.
   * @return an array of classes representing the groups.
   */
  Class<?>[] groups() default {};

  /**
   * Type of the {@link Payload} associated with this validation.
   * @return the payload type
   */
  Class<? extends Payload>[] payload() default {};
}