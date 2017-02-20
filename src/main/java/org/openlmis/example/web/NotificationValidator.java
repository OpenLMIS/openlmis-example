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

import org.openlmis.example.domain.Foo;
import org.openlmis.example.domain.Notification;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/*
    This class, as opposed to the BarValidator class, is intended to help illustrate
    validation via a Validator rather than through annotations.
 */
public class NotificationValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    //Indicate that we only wish to validate Notification objects
    return Notification.class.equals(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    //Ensure that the recipient field isn't empty (null or "")
    ValidationUtils.rejectIfEmpty(errors, "recipient", "recipient.empty");

    Notification notification = (Notification) obj;
    if (!notification.getRecipient().contains("@")) {
      errors.rejectValue("recipient", "invalid.recipient.address");
    }
  }
}
