package org.openlmis.example.web;


import org.openlmis.example.domain.Bar;

import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidatorContext;

/*
    Create a class whose isValid() method is automatically used to validate any class to which the
    @BarValidation has been applied.

    This class, as opposed to the NotificationValidator class, is intended to help illustrate
    custom annotation-based validation.
 */

@SuppressWarnings("PMD.UnusedPrivateField")
public class BarValidator implements ConstraintValidator<BarValidation, Bar> {
  private BarValidation barValidation;

  /*
      Although we're require to implement it, we don't make much use of the initialize() method
      in this example. It allows us to access user-defined values aptionally associated with our
      custom annotation. In our case, though, the annotation expects no such configuration.
   */
  @Override
  public void initialize(BarValidation barValidation) {
    this.barValidation = barValidation;
  }

  @Override
  public boolean isValid(Bar bar, ConstraintValidatorContext constraintContext) {
    // It's apparently recommended that null objects be treated as valid.
    // This warrants more thought/research.
    if (bar == null) {
      return true;
    }

    boolean isValid = true;

    // Validate a cross-field business rule and define an error message for it
    if (bar.getCapacity() < bar.getName().length()) {
      isValid = false;

      constraintContext.disableDefaultConstraintViolation();
      constraintContext.buildConstraintViolationWithTemplate(
          "{capacity.smaller.than.name.length.validation.error}").addConstraintViolation();
    }

    // Validate some other arbitrary rule
    if (bar.getCapacity() == 9) {
      isValid = false;

      constraintContext.disableDefaultConstraintViolation();
      constraintContext.buildConstraintViolationWithTemplate(
          "{capacity.cannot.equal.9.validation.error}").addConstraintViolation();
    }

    return isValid;
  }
}
