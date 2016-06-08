package org.openlmis.example.web;

import org.openlmis.example.domain.Bar;
import org.openlmis.example.domain.Notification;
import org.openlmis.example.repository.ReadOnlyFooRepository;
import org.openlmis.example.validator.NotificationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


@RestController
public class NotificationController
{
    @Autowired @Qualifier("beforeSaveNotificationValidator")
    NotificationValidator validator;

    /*
       This endpoint illustrates how a Spring MVC controller might use a manually created Validator
       (as opposed to relying on annotation-based validation)
     */
    @RequestMapping(path = "/api/notifications/validate", method = RequestMethod.POST)
    public boolean getValid(@RequestBody Notification notification, BindingResult bindingResult, SessionStatus status)
    {
        //Tell our validator to validate the notification, and update bindingResult with the result
        validator.validate(notification, bindingResult);

        return bindingResult.getErrorCount() == 0;
    }

}
