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