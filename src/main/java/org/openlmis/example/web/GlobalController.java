package org.openlmis.example.web;

import org.openlmis.example.exception.ExampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller that defines exception handler methods that will apply to exceptions raised across the
 * whole application, not in just one module. It is possible to override the global behaviour with
 * exception handler methods in base module controller. So in this example, if a given controller
 * extends BaseController, an exception handler method from BaseController will invoke when
 * ExampleException will be thrown. If a given controller does not extends BaseController,
 * throwing an ExampleException will invoke method from GlobalController.
 */
@ControllerAdvice
public class GlobalController {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);

  @ExceptionHandler(ExampleException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public String handleNotFound(Exception exception) {
    return handleException(exception);
  }

  private String handleException(Exception exception) {
    LOGGER.error(exception.getMessage(), exception);
    return exception.getMessage();
  }

}
