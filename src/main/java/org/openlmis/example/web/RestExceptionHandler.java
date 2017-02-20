package org.openlmis.example.web;

import org.openlmis.example.exception.ExceptionDetail;
import org.openlmis.example.exception.ExtensionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

/**
 * A class for handling exception, that leverages springs {@link ControllerAdvice}
 * mechanism.
 */
@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  /**
   * Handle exception that signals an extension retrieval exception.
   * @param exception the exception thrown
   * @return a response entity for the exception (internal error)
   */
  @ExceptionHandler(ExtensionException.class)
  public ResponseEntity<?> handleExtensionException(ExtensionException exception) {
    LOGGER.debug(exception.getMessage(), exception);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String title = "Extension manager failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);
    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  /**
   * Handle exception that signal transaction system issues.
   * @param exception the exception thrown
   * @return a response entity for the exception (bad request)
   */
  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<?> handleConstraintViolationException(
      TransactionSystemException exception) {
    if (exception.contains(javax.validation.ConstraintViolationException.class)
        || exception.contains(org.hibernate.exception.ConstraintViolationException.class)) {
      Throwable specificException = exception.getMostSpecificCause();
      HttpStatus status = HttpStatus.BAD_REQUEST;
      String title = "Resource Property Validation Failure";
      ExceptionDetail exceptionDetail = getExceptionDetail(specificException, status, title);

      return new ResponseEntity<>(exceptionDetail, null, status);
    } else {
      throw exception;
    }
  }

  /**
   * Handle exception that signal javax constraint violations.
   * @param exception the exception thrown
   * @return a response entity for the exception (bad request)
   */
  @ExceptionHandler(javax.validation.ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(
      javax.validation.ConstraintViolationException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String title = "Resource Property Validation Failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);

    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  //TODO: Determine why this ExceptionHandler isn't being used
  /**
   * Handle exception that signal constraint violations coming from Hibernate.
   * @param exception the exception thrown
   * @return a response entity for the exception (bad request)
   */
  @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException2(
      org.hibernate.exception.ConstraintViolationException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    String title = "Resource Property Validation Failure";
    ExceptionDetail exceptionDetail = getExceptionDetail(exception, status, title);

    return new ResponseEntity<>(exceptionDetail, null, status);
  }

  private static ExceptionDetail getExceptionDetail(Throwable throwable, HttpStatus status,
                                                    String title) {
    ExceptionDetail exceptionDetail = new ExceptionDetail();
    exceptionDetail.setTimeStamp(new Date().getTime());
    exceptionDetail.setStatus(status.value());
    exceptionDetail.setTitle(title);
    exceptionDetail.setDetail(throwable.getMessage());
    exceptionDetail.setDeveloperMessage(throwable.getClass().getName());
    return exceptionDetail;
  }
}