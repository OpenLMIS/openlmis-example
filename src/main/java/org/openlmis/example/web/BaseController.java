package org.openlmis.example.web;

import org.openlmis.example.exception.ExampleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Base abstract controller that defines exceptions handlers for controllers from only one module.
 * Other controllers from given module have to extend this abstract controller.
 */
public abstract class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(ExampleException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFound(Exception e) {
        return handleException(e);
    }

    private String handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return e.getMessage();
    }
}
