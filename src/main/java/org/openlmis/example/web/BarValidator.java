/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

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
