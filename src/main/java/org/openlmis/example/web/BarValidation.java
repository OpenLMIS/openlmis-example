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

  /**
   * Key for creating error messages in case the constraint is violated.
   * 
   * @return key
   */
  String message() default "{invalid.address.message}";

  /**
   * Groups to which this constraint belongs.
   * 
   * @return groups
   */
  Class<?>[] groups() default {};

  /**
   * For assigning custom payload objects to this constraint.
   * 
   * @return payload
   */
  Class<? extends Payload>[] payload() default {};
}