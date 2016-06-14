package org.openlmis.example.web;

import org.openlmis.example.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;


@RestController
public class NotificationController {
  @Autowired
  @Qualifier("beforeSaveNotificationValidator")
  NotificationValidator validator;

  /*
     This endpoint illustrates how a Spring MVC controller might use a manually created Validator
     (as opposed to relying on annotation-based validation)
   */
  @RequestMapping(path = "/api/notifications/validate", method = RequestMethod.POST)
  public boolean getValid(@RequestBody Notification notification, BindingResult bindingResult,
                          SessionStatus status) {
    //Tell our validator to validate the notification, and update bindingResult with the result
    validator.validate(notification, bindingResult);

    return bindingResult.getErrorCount() == 0;
  }

}
