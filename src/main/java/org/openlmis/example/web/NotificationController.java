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

import org.openlmis.example.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example controller demonstrating validation of notification payloads.
 */
@RestController
public class NotificationController extends BaseController {

  @Autowired
  @Qualifier("beforeSaveNotificationValidator")
  private NotificationValidator validator;

  /**
   * This endpoint illustrates how a Spring MVC controller might use a manually created Validator
   * (as opposed to relying on annotation-based validation).
   * @param notification the notification to validate
   * @param bindingResult the binding result for the notification payload
   * @return true if passes validation, false otherwise
   */
  @RequestMapping(path = "/notifications/validate", method = RequestMethod.POST)
  public boolean getValid(@RequestBody Notification notification, BindingResult bindingResult) {
    //Tell our validator to validate the notification, and update bindingResult with the result
    validator.validate(notification, bindingResult);

    return bindingResult.getErrorCount() == 0;
  }

}
