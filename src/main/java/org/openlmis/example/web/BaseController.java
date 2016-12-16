package org.openlmis.example.web;

import org.openlmis.example.exception.ExampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base abstract controller that defines exceptions handlers for controllers from only one module.
 * Other controllers from given module have to extend this abstract controller.
 */
@RequestMapping("/api")
public abstract class BaseController {

  Logger logger = LoggerFactory.getLogger(BaseController.class);

  @ExceptionHandler(ExampleException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public String handleNotFound(Exception exception) {
    return handleException(exception);
  }

  private String handleException(Exception exception) {
    logger.error(exception.getMessage(), exception);
    return exception.getMessage();
  }
}
